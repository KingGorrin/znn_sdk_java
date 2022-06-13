package network.zenon.model.primatives;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import com.jsoniter.JsonIterator;

import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.HashHeight;
import network.zenon.model.primitives.json.JHashHeight;
import network.zenon.utils.BytesUtils;
import network.zenon.utils.NullableConverter;

public class HashHeightTest {
    @ParameterizedTest
    @CsvSource({
            "'b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9', 259, 'b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b90000000000000103'" })
    public void whenGetBytesExpectToEqual(String hashString, @ConvertWith(NullableConverter.class) Long height,
            String byteString) {
        // Setup
        HashHeight hh = new HashHeight(Hash.parse(hashString), height);

        // Execute
        byte[] bytes = hh.getBytes();

        // Validate
        assertArrayEquals(BytesUtils.fromHexString(byteString), bytes);
    }

    @ParameterizedTest
    @CsvSource({
            "'b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9', 259, '{\"hash\":\"b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9\",\"height\":259}'",
            "'b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9', null, '{\"hash\":\"b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9\",\"height\":null}'" })
    public void whenSerializeExpectToEqual(String hashString, @ConvertWith(NullableConverter.class) Long height,
            String expectedJson) {
        // Setup
        HashHeight hh = new HashHeight(Hash.parse(hashString), height);

        // Execute
        String json = hh.toString();

        // Validate
        assertEquals(expectedJson, json);
    }

    @ParameterizedTest
    @CsvSource({
            "'b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9', 259, '{\"hash\":\"b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9\",\"height\":259}'",
            "'b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9', null, '{\"hash\":\"b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9\",\"height\":null}'" })
    public void whenDeserializeExpectToEqual(String hashString, @ConvertWith(NullableConverter.class) Long height,
            String json) {
        // Setup
        HashHeight exprectedHh = new HashHeight(Hash.parse(hashString), height);

        // Execute
        HashHeight hh = new HashHeight(JsonIterator.deserialize(json, JHashHeight.class));

        // Validate
        assertEquals(exprectedHh.getHash(), hh.getHash());
        assertEquals(exprectedHh.getHeight(), hh.getHeight());
    }
}
