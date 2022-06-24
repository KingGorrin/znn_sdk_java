package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JPillarEpochStats;
import network.zenon.utils.JsonUtils;

public class PillarEpochStats implements JsonConvertible<JPillarEpochStats> {
    private final long producedMomentums;
    private final long expectedMomentums;

    public PillarEpochStats(JPillarEpochStats json) {
        this.producedMomentums = json.producedMomentums;
        this.expectedMomentums = json.expectedMomentums;
    }

    public PillarEpochStats(long producedMomentums, long expectedMomentums) {
        this.producedMomentums = producedMomentums;
        this.expectedMomentums = expectedMomentums;
    }

    public long getProducedMomentums() {
        return this.producedMomentums;
    }

    public long getExpectedMomentums() {
        return this.expectedMomentums;
    }

    @Override
    public JPillarEpochStats toJson() {
        JPillarEpochStats json = new JPillarEpochStats();
        json.producedMomentums = this.producedMomentums;
        json.expectedMomentums = this.expectedMomentums;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}