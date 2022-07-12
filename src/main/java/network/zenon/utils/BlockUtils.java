package network.zenon.utils;

import java.math.BigInteger;
import java.util.function.Consumer;

import org.slf4j.LoggerFactory;

import network.zenon.Zenon;
import network.zenon.model.embedded.GetRequiredParam;
import network.zenon.model.embedded.GetRequiredResponse;
import network.zenon.model.nom.AccountBlock;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.nom.BlockTypeEnum;
import network.zenon.model.nom.Momentum;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.HashHeight;
import network.zenon.pow.PoW;
import network.zenon.pow.PowStatus;
import network.zenon.wallet.KeyPair;

public class BlockUtils {
    public static boolean isSendBlock(BlockTypeEnum blockType) {
        return blockType == BlockTypeEnum.USER_SEND || blockType == BlockTypeEnum.CONTRACT_SEND;
    }

    public static boolean isReceiveBlock(BlockTypeEnum blockType) {
        return blockType == BlockTypeEnum.USER_RECEIVE || blockType == BlockTypeEnum.GENESIS_RECEIVE
                || blockType == BlockTypeEnum.CONTRACT_RECEIVE;
    }

    public static Hash getTransactionHash(AccountBlockTemplate transaction) {
        byte[] versionBytes = BytesUtils.getBytes((long) transaction.getVersion());
        byte[] chainIdentifierBytes = BytesUtils.getBytes((long) transaction.getChainIdentifier());
        byte[] blockTypeBytes = BytesUtils.getBytes((long) transaction.getBlockType().ordinal());
        byte[] previousHashBytes = transaction.getPreviousHash().getBytes();
        byte[] heightBytes = BytesUtils.getBytes(transaction.getHeight());
        byte[] momentumAcknowledgedBytes = transaction.getMomentumAcknowledged().getBytes();
        byte[] addressBytes = transaction.getAddress().getBytes();
        byte[] toAddressBytes = transaction.getToAddress().getBytes();
        byte[] amountBytes = BytesUtils.bigIntToBytes(BigInteger.valueOf(transaction.getAmount()), 32);
        byte[] tokenStandardBytes = transaction.getTokenStandard().getBytes();
        byte[] fromBlockHashBytes = transaction.getFromBlockHash().getBytes();
        byte[] descendentBlocksBytes = Hash.digest(new byte[0]).getBytes();
        byte[] dataBytes = Hash.digest(transaction.getData()).getBytes();
        byte[] fusedPlasmaBytes = BytesUtils.getBytes(transaction.getFusedPlasma());
        byte[] difficultyBytes = BytesUtils.getBytes(transaction.getDifficulty());
        byte[] nonceBytes = BytesUtils.leftPadBytes(BytesUtils.fromHexString(transaction.getNonce()), 8);

        byte[] source = ArrayUtils.concat(versionBytes, chainIdentifierBytes, blockTypeBytes, previousHashBytes,
                heightBytes, momentumAcknowledgedBytes, addressBytes, toAddressBytes, amountBytes, tokenStandardBytes,
                fromBlockHashBytes, descendentBlocksBytes, dataBytes, fusedPlasmaBytes, difficultyBytes, nonceBytes);

        return Hash.digest(source);
    }

    private static byte[] getTransactionSignature(KeyPair keyPair, AccountBlockTemplate transaction) {
        return keyPair.sign(transaction.getHash().getBytes());
    }

    private static Hash getPoWData(AccountBlockTemplate transaction) {
        return Hash.digest(
                ArrayUtils.concat(transaction.getAddress().getBytes(), transaction.getPreviousHash().getBytes()));
    }

    private static void autofillTransactionParameters(AccountBlockTemplate accountBlockTemplate) {
        AccountBlock frontierAccountBlock = Zenon.getInstance().getLedger()
                .getFrontierAccountBlock(accountBlockTemplate.getAddress());

        long height = 1;
        Hash previousHash = Hash.EMPTY;

        if (frontierAccountBlock != null) {
            height = frontierAccountBlock.getHeight() + 1;
            previousHash = frontierAccountBlock.getHash();
        }

        accountBlockTemplate.setHeight(height);
        accountBlockTemplate.setPreviousHash(previousHash);

        Momentum frontierMomentum = Zenon.getInstance().getLedger().getFrontierMomentum();

        accountBlockTemplate
                .setMomentumAcknowledged(new HashHeight(frontierMomentum.getHash(), frontierMomentum.getHeight()));
    }

    private static boolean checkAndSetFields(AccountBlockTemplate transaction, KeyPair currentKeyPair)
            throws Exception {
        transaction.setAddress(currentKeyPair.getAddress());
        transaction.setPublicKey(currentKeyPair.getPublicKey());

        autofillTransactionParameters(transaction);

        if (BlockUtils.isSendBlock(transaction.getBlockType())) {

        } else {
            if (transaction.getFromBlockHash().equals(Hash.EMPTY)) {
                throw new Exception();
            }

            AccountBlock sendBlock = Zenon.getInstance().getLedger()
                    .getAccountBlockByHash(transaction.getFromBlockHash());

            if ((sendBlock == null) || !(sendBlock.getToAddress().equals(transaction.getAddress())) || transaction.getData() == null || transaction.getData().length != 0) {
                throw new Exception();
            }
        }

        if (transaction.getDifficulty() > 0 && transaction.getNonce().equals("")) {
            throw new Exception();
        }

        return true;
    }

    private static boolean setDifficulty(AccountBlockTemplate transaction, Consumer<PowStatus> generatingPowCallback) {
        return setDifficulty(transaction, generatingPowCallback, false);
    }

    private static boolean setDifficulty(AccountBlockTemplate transaction, Consumer<PowStatus> generatingPowCallback,
            boolean waitForRequiredPlasma) {
        GetRequiredParam powParam = new GetRequiredParam(transaction.getAddress(), transaction.getBlockType(),
                transaction.getToAddress(), transaction.getData());

        GetRequiredResponse response = Zenon.getInstance().getEmbedded().getPlasma()
                .getRequiredPoWForAccountBlock(powParam);

        if (response.getRequiredDifficulty() != 0) {
            transaction.setFusedPlasma(response.getAvailablePlasma());
            transaction.setDifficulty(response.getRequiredDifficulty());

            LoggerFactory.getLogger(BlockUtils.class)
                    .info(String.format("Generating Plasma for block: hash=%s", BlockUtils.getPoWData(transaction)));

            generatingPowCallback.accept(PowStatus.GENERATING);

            String nonce = PoW.generate(getPoWData(transaction), transaction.getDifficulty());

            transaction.setNonce(nonce);

            generatingPowCallback.accept(PowStatus.DONE);
        } else {
            transaction.setFusedPlasma(response.getBasePlasma());
            transaction.setDifficulty(0);
            transaction.setNonce("0000000000000000");
        }
        return true;
    }

    private static boolean setHashAndSignature(AccountBlockTemplate transaction, KeyPair currentKeyPair) {
        transaction.setHash(getTransactionHash(transaction));
        transaction.setSignature(getTransactionSignature(currentKeyPair, transaction));

        return true;
    }

    public static AccountBlockTemplate send(AccountBlockTemplate transaction, KeyPair currentKeyPair,
            Consumer<PowStatus> generatingPowCallback) throws Exception {
        return send(transaction, currentKeyPair, generatingPowCallback, false);
    }

    public static AccountBlockTemplate send(AccountBlockTemplate transaction, KeyPair currentKeyPair,
            Consumer<PowStatus> generatingPowCallback, boolean waitForRequiredPlasma) throws Exception {
        checkAndSetFields(transaction, currentKeyPair);
        setDifficulty(transaction, generatingPowCallback, waitForRequiredPlasma);

        setHashAndSignature(transaction, currentKeyPair);

        Zenon.getInstance().getLedger().publishRawTransaction(transaction);

        LoggerFactory.getLogger(BlockUtils.class).info("Published account-block");

        return transaction;
    }

    public static boolean requiresPoW(AccountBlockTemplate transaction, KeyPair blockSigningKey) {
        transaction.setAddress(blockSigningKey.getAddress());

        GetRequiredParam powParam = new GetRequiredParam(transaction.getAddress(), transaction.getBlockType(),
                transaction.getToAddress(), transaction.getData());

        GetRequiredResponse response = Zenon.getInstance().getEmbedded().getPlasma()
                .getRequiredPoWForAccountBlock(powParam);

        if (response.getRequiredDifficulty() == 0) {
            return false;
        }
        return true;
    }
}
