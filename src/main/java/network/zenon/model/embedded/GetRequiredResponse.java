package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JGetRequiredResponse;
import network.zenon.utils.JsonUtils;

public class GetRequiredResponse implements JsonConvertible<JGetRequiredResponse> {
    private final long availablePlasma;
    private final long basePlasma;
    private final long requiredDifficulty;

    public GetRequiredResponse(JGetRequiredResponse json) {
        this.availablePlasma = json.availablePlasma;
        this.basePlasma = json.basePlasma;
        this.requiredDifficulty = json.requiredDifficulty;
    }

    public GetRequiredResponse(long availablePlasma, long basePlasma, long requiredDifficulty) {
        this.availablePlasma = availablePlasma;
        this.basePlasma = basePlasma;
        this.requiredDifficulty = requiredDifficulty;
    }

    public long getAvailablePlasma() {
        return this.availablePlasma;
    }

    public long getBasePlasma() {
        return this.basePlasma;
    }

    public long getRequiredDifficulty() {
        return this.requiredDifficulty;
    }

    @Override
    public JGetRequiredResponse toJson() {
        JGetRequiredResponse json = new JGetRequiredResponse();
        json.availablePlasma = this.availablePlasma;
        json.basePlasma = this.basePlasma;
        json.requiredDifficulty = this.requiredDifficulty;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}