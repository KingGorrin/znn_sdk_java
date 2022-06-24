package network.zenon.model.embedded;

import network.zenon.model.JsonConvertible;
import network.zenon.model.embedded.json.JPlasmaInfo;
import network.zenon.utils.JsonUtils;

public class PlasmaInfo implements JsonConvertible<JPlasmaInfo> {
    private final long currentPlasma;
    private final long maxPlasma;
    private final long qsrAmount;

    public PlasmaInfo(JPlasmaInfo json) {
        this.currentPlasma = json.currentPlasma;
        this.maxPlasma = json.maxPlasma;
        this.qsrAmount = json.qsrAmount;
    }

    public PlasmaInfo(long currentPlasma, long maxPlasma, long qsrAmount) {
        this.currentPlasma = currentPlasma;
        this.maxPlasma = maxPlasma;
        this.qsrAmount = qsrAmount;
    }

    public long getCurrentPlasma() {
        return this.currentPlasma;
    }

    public long getMaxPlasma() {
        return this.maxPlasma;
    }

    public long getQsrAmount() {
        return this.qsrAmount;
    }

    @Override
    public JPlasmaInfo toJson() {
        JPlasmaInfo json = new JPlasmaInfo();
        json.currentPlasma = this.currentPlasma;
        json.maxPlasma = this.maxPlasma;
        json.qsrAmount = this.qsrAmount;
        return json;
    }

    @Override
    public String toString() {
        return JsonUtils.serialize(this.toJson());
    }
}