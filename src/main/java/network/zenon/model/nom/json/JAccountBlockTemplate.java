package network.zenon.model.nom.json;

import network.zenon.model.primitives.json.JHashHeight;

public class JAccountBlockTemplate {
    public int version;
    public int chainIdentifier;
    public int blockType;
    public String hash;
    public String previousHash;
    public long height;
    public JHashHeight momentumAcknowledged;
    public String address;
    public String toAddress;
    public long amount;
    public String tokenStandard;
    public String fromBlockHash;
    public String data;
    public long fusedPlasma;
    public long difficulty;
    public String nonce;
    public String publicKey;
    public String signature;
}