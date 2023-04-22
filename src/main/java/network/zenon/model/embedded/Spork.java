package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JSpork;
import network.zenon.model.primitives.Hash;
import network.zenon.utils.JsonUtils;

public class Spork implements JsonConvertible<JSpork> {
    private final Hash id;
    private final String name;
    private final String description;
    private final boolean activated;
    private final long enforcementHeight;

    public Spork(JSpork json) {
        this.id = Hash.parse(json.id);
        this.name = json.name;
        this.description = json.description;
        this.activated = json.activated;
        this.enforcementHeight = json.enforcementHeight;
    }

    public Hash getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getActivated() {
        return this.activated;
    }

    public long getEnforcementHeight() {
        return this.enforcementHeight;
    }

    @Override
    public JSpork toJson() {
        JSpork json = new JSpork();
        json.id = this.id.toString();
        json.name = this.name;
        json.description = this.description;
        json.activated = this.activated;
        json.enforcementHeight = this.enforcementHeight;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}