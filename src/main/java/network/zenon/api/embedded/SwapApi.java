package network.zenon.api.embedded;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;

import network.zenon.Constants;
import network.zenon.client.IClient;
import network.zenon.model.embedded.SwapAssetEntry;
import network.zenon.model.embedded.SwapLegacyPillarEntry;
import network.zenon.model.embedded.json.JSwapAssetEntry;
import network.zenon.model.embedded.json.JSwapLegacyPillarEntry;
import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.primitives.Hash;

public class SwapApi {
	private final IClient client;
	
    public SwapApi(IClient client) {
        this.client = client;
    }

    public IClient getClient() {
    	return this.client;
    }

    public SwapAssetEntry getAssetsByKeyIdHash(Hash keyIdHash) {
    	JSwapAssetEntry response = this.client.sendRequest("embedded.swap.getAssetsByKeyIdHash", new Object[] { keyIdHash.toString() }, JSwapAssetEntry.class);
        return new SwapAssetEntry(keyIdHash, response);
    }

    public List<SwapAssetEntry> getAssets() {
    	try {
    		List<SwapAssetEntry> result = new ArrayList<SwapAssetEntry>();
	    	Object response = this.client.sendRequest("embedded.swap.getAssets", null);
	    	JsonIterator iterator = JsonIterator.parse(response.toString());
	    	iterator.readAny().forEach(x -> result.add(new SwapAssetEntry(Hash.parse(x.toString("key")), x.as(JSwapAssetEntry.class))));
	    	return result;
    	} catch (IOException e) {
    		return null;
    	}
    }

    public List<SwapLegacyPillarEntry> getLegacyPillars() {
    	try {
    		Object response = this.client.sendRequest("embedded.swap.getLegacyPillars", null);
    		if (response == null) return null;
    		JsonIterator iterator = JsonIterator.parse(response.toString());
    		List<JSwapLegacyPillarEntry> result = iterator.read(new TypeLiteral<List<JSwapLegacyPillarEntry>>() {});
    		return result.stream().map(x -> new SwapLegacyPillarEntry(x)).toList();
    	} catch (IOException e) {
    		return null;
    	}
    }

    // Contract methods
    public AccountBlockTemplate retrieveAssets(String pubKey, String signature) {
    	throw new UnsupportedOperationException();
    }

    public long getSwapDecayPercentage(int currentTimestamp)
    {
        final int secondsPerDay = 86400;

        int percentageToGive = 100;
        long currentEpoch =
            (currentTimestamp - Constants.GENESIS_TIMESTAMP) / secondsPerDay;

        if (currentTimestamp < Constants.SWAP_ASSET_DECAY_TIMESTAMP_START)
        {
            percentageToGive = 100;
        }
        else
        {
            int numTicks = (int)((currentEpoch - Constants.SWAP_ASSET_DECAY_EPOCHS_OFFSET + 1) / Constants.SWAP_ASSET_DECAY_TICK_EPOCHS);
            int decayFactor = Constants.SWAP_ASSET_DECAY_TICK_VALUE_PERCENTAGE * numTicks;
            if (decayFactor > 100)
            {
                percentageToGive = 0;
            }
            else
            {
                percentageToGive = 100 - decayFactor;
            }
        }
        return 100 - percentageToGive;
    }
}