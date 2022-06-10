package network.zenon.model.nom.json;

import java.util.List;

public class JMomentum {
    public int version;
    public int chainIdentifier;
    public String hash;
    public String previousHash;
    public long height;
    public long timestamp;
    public String data;
    public List<JAccountHeader> content;
    public String changesHash;
    public String publicKey;
    public String signature;
    public String producer;
}