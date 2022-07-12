package network.zenon.model.primitives;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import network.zenon.model.primitives.Bech32.Encoding;

public class Address implements Comparable<Address> {
    public static final String PREFIX = "z";
    public static final int ADDRESS_LENGTH = 40;
    public static final int USER_BYTE = 0;
    public static final int CONTRACT_BYTE = 1;
    public static final int CORE_SIZE = 20;

    public static final Address EMPTY_ADDRESS = parse("z1qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqsggv2f");
    public static final Address PLASMA_ADDRESS = parse("z1qxemdeddedxplasmaxxxxxxxxxxxxxxxxsctrp");
    public static final Address PILLAR_ADDRESS = parse("z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg");
    public static final Address TOKEN_ADDRESS = parse("z1qxemdeddedxt0kenxxxxxxxxxxxxxxxxh9amk0");
    public static final Address SENTINEL_ADDRESS = parse("z1qxemdeddedxsentynelxxxxxxxxxxxxxwy0r2r");
    public static final Address SWAP_ADDRESS = parse("z1qxemdeddedxswapxxxxxxxxxxxxxxxxxxl4yww");
    public static final Address STAKE_ADDRESS = parse("z1qxemdeddedxstakexxxxxxxxxxxxxxxxjv8v62");
    public static final Address SPROK_ADDRESS = parse("z1qxemdeddedxsp0rkxxxxxxxxxxxxxxxx956u48");
    public static final Address ACCELERATOR_ADDRESS = parse("z1qxemdeddedxaccelerat0rxxxxxxxxxxp4tk22");
    public static final Address BRIDGE_ADDRESS = parse("z1qzlytaqdahg5t02nz5096frflfv7dm3y7yxmg7");

    public static final Address[] EMBEDDED_CONTRACT_ADDRESSES = new Address[] { PLASMA_ADDRESS, PILLAR_ADDRESS,
            TOKEN_ADDRESS, SENTINEL_ADDRESS, SWAP_ADDRESS, STAKE_ADDRESS, ACCELERATOR_ADDRESS };

    public static Address parse(final String address) {
        Bech32.Bech32Data bech32 = Bech32.decode(address);
        byte[] core = Bech32.convertBits(bech32.data, 5, 8, false);
        return new Address(bech32.hrp, core);
    }

    public static boolean isValid(final String address) {
        try {
            Address a = parse(address);
            return a.toString().equals(address);
        } catch (Exception e) {
            return false;
        }
    }

    public static Address fromPublicKey(final byte[] publicKey) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] encodedhash = digest.digest(publicKey);
            byte[] core = new byte[CORE_SIZE];
            core[0] = USER_BYTE;
            System.arraycopy(encodedhash, 0, core, 1, 19);
            return new Address(PREFIX, core);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private final String hrp;
    private final byte[] core;

    public Address(String hrp, byte[] core) {
        this.hrp = hrp;
        this.core = core;
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

    public String toShortString() {
        String longString = this.toString();
        return longString.substring(0, 7) + "..." + longString.substring(longString.length() - 6);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(Address other) {
        return this.toString().compareTo(other.toString());
    }

    public boolean isEmbedded() {
        for (Address element : EMBEDDED_CONTRACT_ADDRESSES) {
            if (element.equals(this))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return this.equals((Address) other);
    }

    public boolean equals(Address other) {
        if (other == null)
            return false;

        if (this == other)
            return true;

        return this.hrp.equals(other.hrp) && Arrays.equals(this.core, other.core);
    }
}
