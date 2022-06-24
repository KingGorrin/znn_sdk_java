package network.zenon.model.primitives;

import java.util.Arrays;

import network.zenon.model.primitives.Bech32.Encoding;

public class TokenStandard implements Comparable<TokenStandard> {
    public static final String ZNN_TOKEN_STANDARD = "zts1znnxxxxxxxxxxxxx9z4ulx";
    public static final String QSR_TOKEN_STANDARD = "zts1qsrxxxxxxxxxxxxxmrhjll";
    public static final String EMPTY_TOKEN_STANDARD = "zts1qqqqqqqqqqqqqqqqtq587y";

    public static final TokenStandard ZNN_ZTS = parse(ZNN_TOKEN_STANDARD);
    public static final TokenStandard QSR_ZTS = parse(QSR_TOKEN_STANDARD);
    public static final TokenStandard EMPTY_ZTS = parse(EMPTY_TOKEN_STANDARD);

    public static final String PREFIX = "zts";
    public static final int CORE_SIZE = 10;

    public static TokenStandard parse(String tokenStandard) {
        Bech32.Bech32Data bech32 = Bech32.decode(tokenStandard);
        String hrp = bech32.hrp;
        byte[] core = Bech32.convertBits(bech32.data, 5, 8, false);
        return new TokenStandard(hrp, core);
    }

    public static TokenStandard fromBytes(byte[] bytes) {
        return new TokenStandard(PREFIX, bytes);
    }

    public static TokenStandard bySymbol(String symbol) {
        if (symbol.equalsIgnoreCase("znn")) {
            return ZNN_ZTS;
        } else if (symbol.equalsIgnoreCase("qsr")) {
            return QSR_ZTS;
        } else {
            throw new IllegalArgumentException("TokenStandard.bySymbol supports only znn/qsr");
        }
    }

    private final String hrp;
    private final byte[] core;

    private TokenStandard(String hrp, byte[] bytes) {
        if (!hrp.equals(PREFIX))
            throw new IllegalArgumentException("Invalid ZTS prefix. Expected '" + PREFIX + "' but got '" + hrp + "'");
        if (bytes.length != CORE_SIZE)
            throw new IllegalArgumentException("Invalid ZTS size. Expected " + CORE_SIZE + " but got " + bytes.length);
        this.hrp = hrp;
        this.core = bytes;
    }

    public String getHrp() {
        return this.hrp;
    }

    public byte[] getBytes() {
        return this.core;
    }

    @Override
    public String toString() {
        return Bech32.encode(Encoding.BECH32, this.hrp, Bech32.convertBits(this.core, 8, 5, true));
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(TokenStandard other) {
        return this.toString().compareTo(other.toString());
    }

    @Override
    public boolean equals(Object other) {
        return this.equals((TokenStandard) other);
    }

    public boolean equals(TokenStandard other) {
        if (other == null)
            return false;

        if (this == other)
            return true;

        return this.hrp.equals(other.hrp) && Arrays.equals(this.core, other.core);
    }
}
