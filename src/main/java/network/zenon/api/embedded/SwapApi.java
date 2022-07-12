package network.zenon.api.embedded;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jsoniter.any.Any;
import com.jsoniter.spi.TypeLiteral;

import network.zenon.Constants;
import network.zenon.client.Client;
import network.zenon.embedded.Definitions;
import network.zenon.model.embedded.SwapAssetEntry;
import network.zenon.model.embedded.SwapLegacyPillarEntry;
import network.zenon.model.embedded.json.JSwapAssetEntry;
import network.zenon.model.embedded.json.JSwapLegacyPillarEntry;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.JsonUtils;

public class SwapApi {
    private final Client client;

    public SwapApi(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public SwapAssetEntry getAssetsByKeyIdHash(Hash keyIdHash) {
        JSwapAssetEntry response = this.client.sendRequest("embedded.swap.getAssetsByKeyIdHash",
                new Object[] { keyIdHash.toString() }, JSwapAssetEntry.class);
        return new SwapAssetEntry(keyIdHash, response);
    }

    public List<SwapAssetEntry> getAssets() {
        Object response = this.client.sendRequest("embedded.swap.getAssets", null);

        List<SwapAssetEntry> result = new ArrayList<>();
        Map<String, Any> map = JsonUtils.deserializeAny(response.toString()).asMap();
        for (Map.Entry<String, Any> entry : map.entrySet()) {
            result.add(new SwapAssetEntry(Hash.parse(entry.getKey()), entry.getValue().as(JSwapAssetEntry.class)));
        }
        return result;
    }

    public List<SwapLegacyPillarEntry> getLegacyPillars() {
        Object response = this.client.sendRequest("embedded.swap.getLegacyPillars", null);
        if (response == null)
            return null;
        List<JSwapLegacyPillarEntry> result = JsonUtils.deserialize(response.toString(),
                new TypeLiteral<List<JSwapLegacyPillarEntry>>() {
                });
        return result.stream().map(x -> new SwapLegacyPillarEntry(x)).collect(Collectors.toList());
    }

    // Contract methods
    public AccountBlockTemplate retrieveAssets(String pubKey, String signature) {
        return AccountBlockTemplate.callContract(Address.SWAP_ADDRESS, TokenStandard.ZNN_ZTS, 0,
                Definitions.SWAP.encodeFunction("RetrieveAssets", pubKey, signature));
    }

    public long getSwapDecayPercentage(int currentTimestamp) {
        final int secondsPerDay = 86400;

        int percentageToGive = 100;
        long currentEpoch = (currentTimestamp - Constants.GENESIS_TIMESTAMP) / secondsPerDay;

        if (currentTimestamp < Constants.SWAP_ASSET_DECAY_TIMESTAMP_START) {
            percentageToGive = 100;
        } else {
            int numTicks = (int) ((currentEpoch - Constants.SWAP_ASSET_DECAY_EPOCHS_OFFSET + 1)
                    / Constants.SWAP_ASSET_DECAY_TICK_EPOCHS);
            int decayFactor = Constants.SWAP_ASSET_DECAY_TICK_VALUE_PERCENTAGE * numTicks;
            if (decayFactor > 100) {
                percentageToGive = 0;
            } else {
                percentageToGive = 100 - decayFactor;
            }
        }
        return 100 - percentageToGive;
    }
}