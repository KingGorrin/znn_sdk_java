package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JPhase;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.JsonUtils;

public class Phase extends AcceleratorProject implements JsonConvertible<JPhase> {
    private final Hash projectId;
    private final long acceptedTimestamp;

    public Phase(JPhase json) {
        super(json);

        this.projectId = Hash.parse(json.projectId);
        this.acceptedTimestamp = json.acceptedTimestamp;
    }

    public Phase(Hash id, Hash projectId, String name, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded, long creationTimestamp, long acceptedTimestamp, AcceleratorProjectStatus status,
            VoteBreakdown voteBreakdown) {
        super(id, name, description, url, znnFundsNeeded, qsrFundsNeeded, creationTimestamp, status, voteBreakdown);

        this.projectId = projectId;
        this.acceptedTimestamp = acceptedTimestamp;
    }

    public Hash getProjectId() {
        return this.projectId;
    }

    public long getAcceptedTimestamp() {
        return this.acceptedTimestamp;
    }

    @Override
    public JPhase toJson() {
        JPhase json = new JPhase();
        super.toJson(json);
        json.projectId = this.projectId.toString();
        json.acceptedTimestamp = this.acceptedTimestamp;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}