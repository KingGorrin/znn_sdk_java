package network.zenon.model.nom;

import com.jsoniter.output.JsonStream;

import network.zenon.Constants;
import network.zenon.model.IJsonConvertible;
import network.zenon.model.nom.json.JAccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.HashHeight;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.BytesUtils;

public class AccountBlockTemplate implements IJsonConvertible<JAccountBlockTemplate> {
    public static AccountBlockTemplate Receive(Hash fromBlockHash) {
    	return new AccountBlockTemplate(BlockTypeEnum.USER_RECEIVE, 
    			null, null, null, 
    			fromBlockHash, null);
    }

    public static AccountBlockTemplate Send(Address toAddress, 
    		TokenStandard tokenStandard, long amount) {
    	return new AccountBlockTemplate(BlockTypeEnum.USER_SEND, 
    			toAddress, 
    			Long.valueOf(amount), 
    			tokenStandard, 
    			null, null);
    }
    
    public static AccountBlockTemplate Send(Address toAddress, 
    		TokenStandard tokenStandard, long amount, byte[] data) {
    	return new AccountBlockTemplate(BlockTypeEnum.USER_SEND, 
    			toAddress, 
    			Long.valueOf(amount),
    			tokenStandard, 
    			null, data);
    }

    public static AccountBlockTemplate CallContract(Address toAddress, 
    		TokenStandard tokenStandard, long amount, byte[] data) {
    	return new AccountBlockTemplate(BlockTypeEnum.USER_SEND, 
    			toAddress, 
    			Long.valueOf(amount),
    			tokenStandard, 
    			null, data);
    }
    
    private final int version;
    private final int chainIdentifier;
    private final BlockTypeEnum blockType;

    private Hash hash;
    private Hash previousHash;
    private long height;
    private HashHeight momentumAcknowledged;

    public Address address;

    // Send information
    private final Address toAddress;

    private final long amount;
    private final TokenStandard tokenStandard;

    // Receive information
    private final Hash fromBlockHash;

    private final byte[] data;

    // PoW
    private long fusedPlasma;
    private long difficulty;

    // Hex representation of 8 byte nonce
    private String nonce;

    // Verification
    private byte[] publicKey;
    private byte[] signature;

    public AccountBlockTemplate(JAccountBlockTemplate json) {
        this.version = json.version;
        this.chainIdentifier = json.chainIdentifier;
        this.blockType = BlockTypeEnum.values()[json.blockType];
        this.hash = Hash.parse(json.hash);
        this.previousHash = Hash.parse(json.previousHash);
        this.height = json.height;
        this.momentumAcknowledged = new HashHeight(json.momentumAcknowledged);
        this.address = Address.parse(json.address);
        this.toAddress = Address.parse(json.toAddress);
        this.amount = json.amount;
        this.tokenStandard = TokenStandard.parse(json.tokenStandard);
        this.fromBlockHash = Hash.parse(json.fromBlockHash);
        this.data = (json.data == null || json.data.isEmpty()) 
        		? new byte[0] 
        		: BytesUtils.fromBase64String(json.data);
        this.fusedPlasma = json.fusedPlasma;
        this.difficulty = json.difficulty;
        this.nonce = json.nonce;
        this.publicKey = (json.publicKey == null || json.publicKey.isEmpty()) 
        		? new byte[0] 
        		: BytesUtils.fromBase64String(json.publicKey);
        this.signature = (json.signature == null || json.signature.isEmpty()) 
        		? new byte[0] 
        		: BytesUtils.fromBase64String(json.signature);
    }

    public AccountBlockTemplate(BlockTypeEnum blockType,
        Address toAddress,
        Long amount,
        TokenStandard tokenStandard,
        Hash fromBlockHash,
        byte[] data) {
        this.version = 1;
        this.chainIdentifier = Constants.NET_ID;
        this.blockType = blockType;
        this.hash = Hash.EMPTY;
        this.previousHash = Hash.EMPTY;
        this.height = 0;
        this.momentumAcknowledged = HashHeight.EMPTY;
        this.address = Address.EMPTY_ADDRESS;
        this.toAddress = toAddress != null ? address : Address.EMPTY_ADDRESS;
        this.amount = amount != null ? amount : 0;
        this.tokenStandard = tokenStandard != null ? tokenStandard : TokenStandard.EMPTY_ZTS;
        this.fromBlockHash = fromBlockHash != null ? fromBlockHash : Hash.EMPTY;
        this.data = data != null ? data : new byte[0];
        this.fusedPlasma = 0;
        this.difficulty = 0;
        this.nonce = "";
        this.publicKey = new byte[0];
        this.signature = new byte[0];
    }

    public int getVersion() {
    	return this.version;
    }
    
    public int getChainIdentifier() {
    	return this.chainIdentifier;
    }
    
    public BlockTypeEnum getBlockType() {
    	return this.blockType;
    }

    public Hash getHash() {
    	return this.hash;
    }
    
    public void setHash(Hash value) {
    	this.hash = value;
    }
    
    public Hash getPreviousHash() {
    	return this.previousHash;
    }
    
    public void setPreviousHash(Hash value) {
    	this.previousHash = value;
    }
    
    public long getHeight() {
    	return this.height;
    }
    
    public void setHeight(long value) {
    	this.height = value;
    }
    
    public HashHeight getMomentumAcknowledged() {
    	return this.momentumAcknowledged;
    }
    
    public void setMomentumAcknowledged(HashHeight value) {
    	this.momentumAcknowledged = value;
    }

    public Address getAddress() {
    	return this.address;
    }
    
    public void setAddress(Address value) {
    	this.address = value;
    }

    // Send information
    public Address getToAddress() {
    	return this.toAddress;
    }

    public long getAmount() {
    	return this.amount;
    }
    
    public TokenStandard getTokenStandard() {
    	return this.tokenStandard;
    }

    // Receive information
    public Hash getFromBlockHash() {
    	return this.fromBlockHash;
    }

    public byte[] getData() {
    	return this.data;
    }

    // PoW
    public long getFusedPlasma() {
    	return this.fusedPlasma;
    }
    
    public void setFusedPlasma(long value) {
    	this.fusedPlasma = value;
    }
    
    public long getDifficulty() {
    	return this.difficulty;
    }
    
    public void setDifficulty(long value) {
    	this.difficulty = value;
    }

    // Hex representation of 8 byte nonce
    public String getNonce() {
    	return this.nonce;
    }
    
    public void setNonce(String value) {
    	this.nonce = value;
    }

    // Verification
    public byte[] getPublicKey() {
    	return this.publicKey;
    }
    
    public void getPublicKey(byte[] value) {
    	this.publicKey = value;
    }
    
    public byte[] getSignature() {
    	return this.signature;
    }
    
    public void getSignature(byte[] value) {
    	this.signature = value;
    }

    @Override
    public JAccountBlockTemplate toJson() {
    	JAccountBlockTemplate json = new JAccountBlockTemplate();
        this.toJson(json);
        return json;
    }

    protected void toJson(JAccountBlockTemplate json) {
        json.version = this.version;
        json.chainIdentifier = this.chainIdentifier;
        json.blockType = this.blockType.ordinal();
        json.hash = this.hash.toString();
        json.previousHash = this.previousHash.toString();
        json.height = this.height;
        json.momentumAcknowledged = this.momentumAcknowledged.toJson();
        json.address = this.address.toString();
        json.toAddress = this.toAddress.toString();
        json.amount = this.amount;
        json.tokenStandard = this.tokenStandard.toString();
        json.fromBlockHash = this.fromBlockHash.toString();
        json.data = BytesUtils.toBase64String(this.data);
        json.fusedPlasma = this.fusedPlasma;
        json.difficulty = this.difficulty;
        json.nonce = this.nonce;
        json.publicKey = BytesUtils.toBase64String(this.publicKey);
        json.signature = BytesUtils.toBase64String(this.signature);
    }

    @Override
    public String toString() {
    	return JsonStream.serialize(this.toJson());
    }
}