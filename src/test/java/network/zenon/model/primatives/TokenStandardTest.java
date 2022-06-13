package network.zenon.model.primatives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.BytesUtils;

public class TokenStandardTest {
    @ParameterizedTest
    @CsvSource({ "'znn','zts1znnxxxxxxxxxxxxx9z4ulx'", "'qsr','zts1qsrxxxxxxxxxxxxxmrhjll'" })
    public void whenBySymbolExpectSuccess(String symbol, String expectedZts) {
        // Execute
        TokenStandard zts = TokenStandard.bySymbol(symbol);

        // Validate
        assertEquals(expectedZts, zts.toString());
    }

    @ParameterizedTest
    @CsvSource({ "'btc'", "'eth'" })
    public void whenBySymbolExpectFailure(String symbol) {
        // Execute
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TokenStandard.bySymbol(symbol);
        });

        String expectedMessage = "TokenStandard.bySymbol supports only znn/qsr";
        String actualMessage = exception.getMessage();

        // Validate
        assertEquals(actualMessage, expectedMessage);
    }

    @ParameterizedTest
    @CsvSource({ "'000259a359013c81203d', 'zts1qqp9ng6eqy7gzgpak0wya9'" })
    public void whenFromBytesExpectToEqual(String hexString, String expectedZts) {
        // Execute
        TokenStandard zts = TokenStandard.fromBytes(BytesUtils.fromHexString(hexString));

        // Validate
        assertEquals(expectedZts, zts.toString());
    }

    @ParameterizedTest
    @CsvSource({ "'03030303030303030303030303030303', 'Invalid ZTS size. Expected 10 but got 16'" })
    public void whenFromBytesExpectFailure(String hexString, String expectedMessage) {
        // Execute
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TokenStandard.fromBytes(BytesUtils.fromHexString(hexString));
        });

        String actualMessage = exception.getMessage();

        // Validate
        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @CsvSource({ "'zts1q803c0nd5cfj2sm4fv0yga','zts183cs3vh0txu6sqc2m03rec','zts1s5l6z3aseq6gce8xf8nsv9'" })
    public void whenParseExpectSuccess(String value) {
        // Execute
        TokenStandard zts = TokenStandard.parse(value);

        // Validate
        assertEquals(value, zts.toString());
    }

    @ParameterizedTest
    @CsvSource({ "'bc1rrrrrrrrrrrrrrrrz9dfqq', Invalid ZTS prefix. Expected 'zts' but got 'bc'" })
    public void whenParseExpectFailure(String value, String expectedMessage) {
        // Execute
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TokenStandard.parse(value);
        });

        String actualMessage = exception.getMessage();

        // Validate
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void equalsReference() {
        assertEquals(TokenStandard.ZNN_ZTS, TokenStandard.ZNN_ZTS);
    }

    @Test
    public void equalsOther() {
        assertEquals(TokenStandard.ZNN_ZTS, TokenStandard.parse(TokenStandard.ZNN_TOKEN_STANDARD));
    }

    @Test
    public void notEqualsNull() {
        assertNotEquals(TokenStandard.ZNN_ZTS, null);
    }

    @Test
    public void notEqualsOther() {
        assertNotEquals(TokenStandard.ZNN_ZTS, TokenStandard.QST_ZTS);
    }
}
