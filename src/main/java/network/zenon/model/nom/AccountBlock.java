package network.zenon.model.nom;

import java.util.Collections;
import java.util.List;

import network.zenon.model.nom.json.JAccountBlock;
import network.zenon.model.primitives.Hash;

public class AccountBlock extends AccountBlockTemplate {
    private final List<AccountBlock> descendantBlocks;
    private final long basePlasma;
    private final long usedPlasma;
    private final Hash changesHash;
    private final Token token;
    // Available if account-block is confirmed, null otherwise
    private final AccountBlockConfirmationDetail confirmationDetail;
    private final AccountBlock pairedAccountBlock;

    public AccountBlock(JAccountBlock json) {
        super(json);

        this.descendantBlocks = Collections
                .unmodifiableList(json.descendantBlocks.stream().map(x -> new AccountBlock(x)).toList());
        this.basePlasma = json.basePlasma;
        this.usedPlasma = json.usedPlasma;
        this.changesHash = Hash.parse(json.changesHash);
        this.token = json.token != null ? new Token(json.token) : null;
        this.confirmationDetail = json.confirmationDetail != null
                ? new AccountBlockConfirmationDetail(json.confirmationDetail)
                : null;
        this.pairedAccountBlock = json.pairedAccountBlock != null ? new AccountBlock(json.pairedAccountBlock) : null;
    }

    public List<AccountBlock> getDescendantBlocks() {
        return this.descendantBlocks;
    }

    public long getBasePlasma() {
        return this.basePlasma;
    }

    public long getUsedPlasma() {
        return this.usedPlasma;
    }

    public Hash getChangesHash() {
        return this.changesHash;
    }

    public Token getToken() {
        return this.token;
    }

    // Available if account-block is confirmed, null otherwise
    public AccountBlockConfirmationDetail getConfirmationDetail() {
        return this.confirmationDetail;
    }

    public AccountBlock getPairedAccountBlock() {
        return this.pairedAccountBlock;
    }

    public boolean getIsCompleted() {
        return this.confirmationDetail != null;
    }

    @Override
    public JAccountBlock toJson() {
        JAccountBlock json = new JAccountBlock();
        this.toJson(json);
        return json;
    }

    protected void toJson(JAccountBlock json) {
        super.toJson(json);

        json.descendantBlocks = this.descendantBlocks.stream().map(AccountBlock::toJson).toList();
        json.usedPlasma = this.usedPlasma;
        json.basePlasma = this.basePlasma;
        json.changesHash = this.changesHash.toString();
        json.token = this.token != null ? this.token.toJson() : null;
        json.confirmationDetail = this.confirmationDetail != null ? this.confirmationDetail.toJson() : null;
        json.pairedAccountBlock = this.pairedAccountBlock != null ? this.pairedAccountBlock.toJson() : null;
    }
}