package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JPillarEpochStats;

public class PillarEpochStats implements IJsonConvertible<JPillarEpochStats> {
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
        return JsonStream.serialize(this.toJson());
    }
}