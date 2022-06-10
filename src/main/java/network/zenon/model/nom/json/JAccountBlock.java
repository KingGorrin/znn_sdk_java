package network.zenon.model.nom.json;

import java.util.List;

public class JAccountBlock extends JAccountBlockTemplate {
    public List<JAccountBlock> descendantBlocks;
    public long basePlasma;
    public long usedPlasma;
    public String changesHash;
    public JToken token;
    public JAccountBlockConfirmationDetail confirmationDetail;
    public JAccountBlock pairedAccountBlock;
}