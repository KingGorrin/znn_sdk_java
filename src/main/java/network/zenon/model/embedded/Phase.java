package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JPhase;
import network.zenon.model.primitives.Hash;

public class Phase extends AcceleratorProject implements IJsonConvertible<JPhase> {
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
        return JsonStream.serialize(this.toJson());
    }
}