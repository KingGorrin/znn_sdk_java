package network.zenon.embedded;

import java.util.Arrays;
import java.util.Collections;

import network.zenon.abi.Abi;
import network.zenon.abi.json.JEntry;
import network.zenon.abi.json.JParam;

public class Definitions {
    private static JEntry createEntry(String name, JParam... inputs) {
        JEntry entry = new JEntry();
        entry.type = "function";
        entry.name = name;
        entry.inputs = Collections.unmodifiableList(Arrays.asList(inputs));
        return entry;
    }

    private static JParam createParam(String name, String type) {
        JParam param = new JParam();
        param.name = name;
        param.type = type;
        return param;
    }

    private static final JEntry[] PLASMA_DEFINITION = new JEntry[] {
            createEntry("Fuse", createParam("address", "address")),
            createEntry("CancelFuse", createParam("id", "hash")) };

    private static final JEntry[] PILLAR_DEFINITION = new JEntry[] {
            createEntry("Register", createParam("name", "string"), createParam("producerAddress", "address"),
                    createParam("rewardAddress", "address"), createParam("giveBlockRewardPercentage", "uint8"),
                    createParam("giveDelegateRewardPercentage", "uint8")),
            createEntry("RegisterLegacy", createParam("name", "string"), createParam("producerAddress", "address"),
                    createParam("rewardAddress", "address"), createParam("giveBlockRewardPercentage", "uint8"),
                    createParam("giveDelegateRewardPercentage", "uint8"), createParam("publicKey", "string"),
                    createParam("signature", "string")),
            createEntry("Revoke", createParam("name", "string")),
            createEntry("UpdatePillar", createParam("name", "string"), createParam("producerAddress", "address"),
                    createParam("rewardAddress", "address"), createParam("giveBlockRewardPercentage", "uint8"),
                    createParam("giveDelegateRewardPercentage", "uint8")),
            createEntry("Delegate", createParam("name", "string")), createEntry("Undelegate") };

    private static final JEntry[] TOKEN_DEFINITION = new JEntry[] {
            createEntry("IssueToken", createParam("tokenName", "string"), createParam("tokenSymbol", "string"),
                    createParam("tokenDomain", "string"), createParam("totalSupply", "uint256"),
                    createParam("maxSupply", "uint256"), createParam("decimals", "uint8"),
                    createParam("isMintable", "bool"), createParam("isBurnable", "bool"),
                    createParam("isUtility", "bool")),
            createEntry("Mint", createParam("tokenStandard", "tokenStandard"), createParam("amount", "uint256"),
                    createParam("receiveAddress", "address")),
            createEntry("Burn"),
            createEntry("UpdateToken", createParam("tokenStandard", "tokenStandard"), createParam("owner", "address"),
                    createParam("isMintable", "bool"), createParam("isBurnable", "bool")) };

    private static final JEntry[] SENTINEL_DEFINITION = new JEntry[] { createEntry("Register"), createEntry("Revoke") };

    private static final JEntry[] SWAP_DEFINITION = new JEntry[] {
            createEntry("RetrieveAssets", createParam("publicKey", "string"), createParam("signature", "string")) };

    private static final JEntry[] STAKE_DEFINITION = new JEntry[] {
            createEntry("Stake", createParam("durationInSec", "int64")),
            createEntry("Cancel", createParam("id", "hash")) };

    private static final JEntry[] ACCELERATOR_DEFINITION = new JEntry[] {
            createEntry("CreateProject", createParam("name", "string"), createParam("description", "string"),
                    createParam("url", "string"), createParam("znnFundsNeeded", "uint256"),
                    createParam("qsrFundsNeeded", "uint256")),
            createEntry("AddPhase", createParam("id", "hash"), createParam("name", "string"),
                    createParam("description", "string"), createParam("url", "string"),
                    createParam("znnFundsNeeded", "uint256"), createParam("qsrFundsNeeded", "uint256")),
            createEntry("UpdatePhase", createParam("id", "hash"), createParam("name", "string"),
                    createParam("description", "string"), createParam("url", "string"),
                    createParam("znnFundsNeeded", "uint256"), createParam("qsrFundsNeeded", "uint256")),
            createEntry("Donate"),
            createEntry("VoteByName", createParam("id", "hash"), createParam("name", "string"),
                    createParam("vote", "uint8")),
            createEntry("VoteByProdAddress", createParam("id", "hash"), createParam("vote", "uint8")) };

    private static final JEntry[] HTLC_DEFINITION = new JEntry[] {
            createEntry("CreateHtlc", createParam("hashLocked", "address"), createParam("expirationTime", "int64"),
                    createParam("hashType", "uint8"), createParam("keyMaxSize", "uint8"),
                    createParam("hashLock", "bytes")),
            createEntry("ReclaimHtlc", createParam("id", "hash")),
            createEntry("UnlockHtlc", createParam("id", "hash"), createParam("preimage", "bytes")) };

    // Common _DEFINITIONs of embedded methods
    private static final JEntry[] COMMON_DEFINITION = new JEntry[] { createEntry("DepositQsr"),
            createEntry("WithdrawQsr"), createEntry("CollectReward") };

    // ABI _DEFINITIONs of embedded contracts
    public static final Abi PLASMA = Abi.parse(PLASMA_DEFINITION);
    public static final Abi PILLAR = Abi.parse(PILLAR_DEFINITION);
    public static final Abi TOKEN = Abi.parse(TOKEN_DEFINITION);
    public static final Abi SENTINEL = Abi.parse(SENTINEL_DEFINITION);
    public static final Abi SWAP = Abi.parse(SWAP_DEFINITION);
    public static final Abi STAKE = Abi.parse(STAKE_DEFINITION);
    public static final Abi ACCELERATOR = Abi.parse(ACCELERATOR_DEFINITION);
    public static final Abi HTLC = Abi.parse(HTLC_DEFINITION);
    public static final Abi COMMON = Abi.parse(COMMON_DEFINITION);
}
