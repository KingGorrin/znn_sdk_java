package network.zenon.model.embedded;

import com.jsoniter.output.JsonStream;

import network.zenon.model.IJsonConvertible;
import network.zenon.model.embedded.json.JGetRequiredParam;
import network.zenon.model.nom.BlockTypeEnum;
import network.zenon.model.primitives.Address;
import network.zenon.utils.BytesUtils;

public class GetRequiredParam implements IJsonConvertible<JGetRequiredParam> {
    private final Address address;
    private final BlockTypeEnum blockType;
    private final Address toAddress;
    private final byte[] data;

    public GetRequiredParam(JGetRequiredParam json) {
        this.address = Address.parse(json.address);
        this.blockType = BlockTypeEnum.values()[json.blockType];
        this.toAddress = json.address != null ? Address.parse(json.address) : null;
        this.data = BytesUtils.fromBase64String(json.data);
    }

    public GetRequiredParam(Address address, BlockTypeEnum blockType, Address toAddress, byte[] data) {
        this.address = address;
        this.blockType = blockType;
        this.toAddress = (blockType == BlockTypeEnum.USER_RECEIVE) ? address : toAddress;
        this.data = data;
    }

    public Address getAddress() {
        return this.address;
    }

    public BlockTypeEnum getBlockType() {
        return this.blockType;
    }

    public Address getToAddress() {
        return this.toAddress;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override
    public JGetRequiredParam toJson() {
        JGetRequiredParam json = new JGetRequiredParam();
        json.address = this.address.toString();
        json.blockType = this.blockType.ordinal();
        json.toAddress = this.toAddress != null ? this.toAddress.toString() : null;
        json.data = BytesUtils.toBase64String(this.data);
        return json;
    }

    @Override
    public String toString() {
        return JsonStream.serialize(this.toJson());
    }
}