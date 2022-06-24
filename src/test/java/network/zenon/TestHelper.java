package network.zenon;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import network.zenon.model.nom.AccountBlockTemplate;
import network.zenon.model.nom.json.JAccountBlockTemplate;
import network.zenon.model.primitives.json.JHashHeight;

public class TestHelper {
    public static String getRosourceText(String resourceName) throws IOException {
        ClassLoader classLoader = TestHelper.class.getClassLoader();
        InputStream in = classLoader.getResourceAsStream(resourceName);
        return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(in.readAllBytes())).toString();
    }
    
    public static AccountBlockTemplate createAccountBlockTemplate(long amount, String data)
    {
        return createAccountBlockTemplate("z1qxemdeddedxaccelerat0rxxxxxxxxxxp4tk22", "zts1znnxxxxxxxxxxxxx9z4ulx", amount, data);
    }

    public static AccountBlockTemplate createAccountBlockTemplate(String toAddres, String tokenStandard, long amount, String data)
    {
        JAccountBlockTemplate json = new JAccountBlockTemplate();
        json.version = 1;
        json.chainIdentifier = 1;
        json.blockType = 2;
        json.hash = "0000000000000000000000000000000000000000000000000000000000000000";
        json.previousHash = "0000000000000000000000000000000000000000000000000000000000000000";
        json.height = 0;
        json.momentumAcknowledged = new JHashHeight();
        json.momentumAcknowledged.hash = "0000000000000000000000000000000000000000000000000000000000000000";
        json.momentumAcknowledged.height = 0L;
        json.address = "z1qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqsggv2f";
        json.toAddress = toAddres;
        json.amount = amount;
        json.tokenStandard = tokenStandard;
        json.fromBlockHash = "0000000000000000000000000000000000000000000000000000000000000000";
        json.data = data;
        json.fusedPlasma = 0;
        json.difficulty = 0;
        json.nonce = "";
        json.publicKey = "";
        json.signature = "";
        
        return new AccountBlockTemplate(json);
    }
}
