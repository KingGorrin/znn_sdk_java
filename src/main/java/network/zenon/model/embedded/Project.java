package network.zenon.model.embedded;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JPhase;
import network.zenon.model.embedded.json.JProject;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;

public class Project extends AcceleratorProject implements IJsonConvertible<JProject> {
    private final Address owner;
    private final List<Hash> phaseIds;
    private final List<Phase> phases;
    private final long lastUpdateTimestamp;

    public Project(JProject json) {
        super(json);

        this.owner = Address.parse(json.owner);
        this.phaseIds = Collections.unmodifiableList(json.phaseIds.stream().map(x -> Hash.parse(x)).toList());
        this.phases = Collections
                .unmodifiableList(json.phases.stream().map(x -> new Phase(JPhase.fromJObject(x))).toList());
        this.lastUpdateTimestamp = json.lastUpdateTimestamp;
    }

    public Project(Hash id, String name, Address owner, String description, String url, long znnFundsNeeded,
            long qsrFundsNeeded, long creationTimestamp, long lastUpdateTimestamp, AcceleratorProjectStatus status,
            List<Hash> phaseIds, VoteBreakdown voteBreakdown, List<Phase> phases) {
        super(id, name, description, url, znnFundsNeeded, qsrFundsNeeded, creationTimestamp, status, voteBreakdown);

        this.owner = owner;
        this.phaseIds = Collections.unmodifiableList(phaseIds);
        this.phases = Collections.unmodifiableList(phases);
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public Address getOwner() {
        return this.owner;
    }

    public List<Hash> getPhaseIds() {
        return this.phaseIds;
    }

    public List<Phase> getPhases() {
        return this.phases;
    }

    public long getLastUpdateTimestamp() {
        return this.lastUpdateTimestamp;
    }

    @Override
    public JProject toJson() {
        JProject json = new JProject();
        super.toJson(json);
        json.owner = this.owner.toString();
        json.lastUpdateTimestamp = this.lastUpdateTimestamp;
        json.phases = this.phases.stream().map(x -> JPhase.toAny(x.toJson())).toList();
        json.phaseIds = this.phaseIds.stream().map(Hash::toString).toList();
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }

    public long getPaidZnnFunds() {
        return this.phases.stream().filter(x -> x.getStatus() == AcceleratorProjectStatus.PAID)
                .collect(Collectors.summingLong(Phase::getZnnFundsNeeded));
    }

    public long getPendingZnnFunds() {
        if (this.phases.isEmpty())
            return 0;

        Phase lastPhase = this.getLastPhase();
        if (lastPhase != null && lastPhase.getStatus() == AcceleratorProjectStatus.ACTIVE) {
            return lastPhase.getZnnFundsNeeded();
        }
        return 0;
    }

    public long getRemainingZnnFunds() {
        if (this.phases.isEmpty())
            return 0;

        return this.getZnnFundsNeeded() - this.getPaidZnnFunds();
    }

    public long getTotalZnnFunds() {
        return this.getZnnFundsNeeded();
    }

    public long getPaidQsrFunds() {
        return this.phases.stream().filter(x -> x.getStatus() == AcceleratorProjectStatus.PAID)
                .collect(Collectors.summingLong(Phase::getQsrFundsNeeded));
    }

    public long GetPendingQsrFunds() {
        if (this.phases.isEmpty())
            return 0;

        Phase lastPhase = this.getLastPhase();
        if (lastPhase != null && lastPhase.getStatus() == AcceleratorProjectStatus.ACTIVE) {
            return lastPhase.getQsrFundsNeeded();
        }
        return 0;
    }

    public long GetRemainingQsrFunds() {
        if (this.phases.isEmpty())
            return 0;

        return this.getQsrFundsNeeded() - this.getPaidQsrFunds();
    }

    public long GetTotalQsrFunds() {
        return this.getQsrFundsNeeded();
    }

    public Phase findPhaseById(Hash id) {
        for (int i = 0; i < this.phaseIds.size(); i++) {
            if (this.phaseIds.get(i).equals(id)) {
                return this.phases.get(i);
            }
        }
        return null;
    }

    public Phase getLastPhase() {
        if (this.phases.isEmpty())
            return null;

        return this.phases.get(this.phases.size() - 1);
    }
}
