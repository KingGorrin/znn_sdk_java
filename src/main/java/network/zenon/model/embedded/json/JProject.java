package network.zenon.model.embedded.json;

import java.util.List;

import com.jsoniter.any.Any;

public class JProject extends JAcceleratorProject {
    public String owner;
    public List<String> phaseIds;
    public List<Any> phases;
    public long lastUpdateTimestamp;
}