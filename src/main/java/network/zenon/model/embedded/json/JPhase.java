package network.zenon.model.embedded.json;

import com.jsoniter.any.Any;

public class JPhase extends JAcceleratorProject {
    public static JPhase fromJObject(Any json) {
        JPhase phase = new JPhase();
        phase.projectId = json.get("phase").toString("projectID");
        phase.acceptedTimestamp = json.get("phase").toLong("acceptedTimestamp");
        phase.id = json.get("phase").toString("id");
        phase.name = json.get("phase").toString("name");
        phase.description = json.get("phase").toString("description");
        phase.url = json.get("phase").toString("url");
        phase.znnFundsNeeded = json.get("phase").toLong("znnFundsNeeded");
        phase.qsrFundsNeeded = json.get("phase").toLong("qsrFundsNeeded");
        phase.creationTimestamp = json.get("phase").toLong("creationTimestamp");
        phase.status = json.get("phase").toInt("status");
        phase.votes = json.get("votes").as(JVoteBreakdown.class);
        return phase;
    }

    public static Any toAny(JPhase phase) {
        return Any.wrap(phase);
    }

    public String projectId;
    public long acceptedTimestamp;
}