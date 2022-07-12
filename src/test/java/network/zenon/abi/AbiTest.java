package network.zenon.abi;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import network.zenon.model.primitives.Address;
import network.zenon.model.primitives.Hash;
import network.zenon.model.primitives.TokenStandard;
import network.zenon.utils.BytesUtils;

public class AbiTest {
    private static Stream<Arguments> abiTypeEncodeValidTestData() throws IOException {
        final BoolType BOOLEAN = new BoolType();
        final IntType INT = new IntType("int256");
        final UnsignedIntType UINT = new UnsignedIntType("uint256");
        final StringType STRING = new StringType();
        final Bytes32Type BYTES32 = new Bytes32Type("bytes32");
        final BytesType BYTES = new BytesType("bytes");
        final FunctionType FUNCTION = new FunctionType();
        final TokenStandardType TOKEN_STANDARD = new TokenStandardType();
        final HashType HASH = new HashType("hash");
        final AddressType ADDRESS = new AddressType();
        final StaticArrayType STATIC_ARRAY = new StaticArrayType("uint8[5]");
        final DynamicArrayType DYNAMIC_ARRAY = new DynamicArrayType("uint8[]");

        final float FLOAT_MIN_VALUE = -3.4028235E38F;
        final double DOUBLE_MIN_VALUE = -4.5231284858326634E74D;
        final double DOUBLE_MAX_VALUE = 4.5231284858326634E74D;
        
        return Stream.of(
                // Test Boolean
                Arguments.of(BOOLEAN, true,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000001")),
                Arguments.of(BOOLEAN, "true",
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000001")),
                Arguments.of(BOOLEAN, "True",
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000001")),
                Arguments.of(BOOLEAN, false,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000000")),
                Arguments.of(BOOLEAN, "false",
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000000")),
                Arguments.of(BOOLEAN, "False",
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000000")),
                // Test Integer
                Arguments.of(INT, Byte.MIN_VALUE,
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF80")),
                Arguments.of(INT, Short.MIN_VALUE,
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF8000")),
                Arguments.of(INT, Integer.MIN_VALUE,
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF80000000")),
                Arguments.of(INT, Long.MIN_VALUE,
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF8000000000000000")),
                Arguments.of(INT, FLOAT_MIN_VALUE,
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000FFFFFFFFF5CADEE0835E800000")),
                Arguments.of(INT, DOUBLE_MIN_VALUE,
                        BytesUtils.fromHexString("FF00000000000007B4D111B9A72294B30CF6BC2DC2A46FE99800000000000000")),
                Arguments.of(INT, BigInteger.valueOf(-1),
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")),
                Arguments.of(INT, "0x05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612",
                        BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
                Arguments.of(INT, "05a0fef85008e63f0680b68d11743ba5caf199994d642590febe570b2a84b612",
                        BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
                Arguments.of(INT, "-123456789",
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF8A432EB")),
                Arguments.of(INT,
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"),
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")),
                // Test UnsignedInteger
                Arguments.of(UINT, 0xFF,
                        BytesUtils.fromHexString("00000000000000000000000000000000000000000000000000000000000000FF")),
                Arguments.of(UINT, Short.MAX_VALUE,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000007FFF")),
                Arguments.of(UINT, Integer.MAX_VALUE,
                        BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000007FFFFFFF")),
                Arguments.of(UINT, Long.MAX_VALUE,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000007FFFFFFFFFFFFFFF")),
                Arguments.of(UINT, Float.MAX_VALUE,
                        BytesUtils.fromHexString("00000000000000000000000000000000FFFFFF000000000A35211F7CA1800000")),
                Arguments.of(UINT, DOUBLE_MAX_VALUE,
                        BytesUtils.fromHexString("00FFFFFFFFFFFFF84B2EEE4658DD6B4CF30943D23D5B90166800000000000000")),
                Arguments.of(UINT,
                        new BigInteger("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16),
                        BytesUtils.fromHexString("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")),
                Arguments.of(UINT, "0x05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612",
                        BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
                Arguments.of(UINT, "05a0fef85008e63f0680b68d11743ba5caf199994d642590febe570b2a84b612",
                        BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
                Arguments.of(UINT, "123456789",
                        BytesUtils.fromHexString("00000000000000000000000000000000000000000000000000000000075BCD15")),
                Arguments.of(UINT,
                        BytesUtils.fromHexString("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"),
                        BytesUtils.fromHexString("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")),
                // Test String
                Arguments.of(STRING, "Hello Zenon!", BytesUtils.fromHexString(
                        "000000000000000000000000000000000000000000000000000000000000000C48656C6C6F205A656E6F6E210000000000000000000000000000000000000000")),
                // Test Bytes32
                Arguments.of(BYTES32, Integer.MAX_VALUE,
                        BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000007FFFFFFF")),
                Arguments.of(BYTES32, Long.MAX_VALUE,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000007FFFFFFFFFFFFFFF")),
                Arguments.of(BYTES32, 0x7FFFFFFF,
                        BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000007FFFFFFF")),
                Arguments.of(BYTES32, 0x7FFFFFFFFFFFFFFFL,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000000007FFFFFFFFFFFFFFF")),
                Arguments.of(BYTES32, Float.MAX_VALUE,
                        BytesUtils.fromHexString("00000000000000000000000000000000FFFFFF000000000A35211F7CA1800000")),
                Arguments.of(BYTES32, DOUBLE_MAX_VALUE,
                        BytesUtils.fromHexString("00FFFFFFFFFFFFF84B2EEE4658DD6B4CF30943D23D5B90166800000000000000")),
                Arguments.of(BYTES32, "05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612",
                        BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
                Arguments.of(BYTES32,
                        BytesUtils.fromHexString("2020202020202020202020202020202020202020202020202020202020202020"),
                        BytesUtils.fromHexString("2020202020202020202020202020202020202020202020202020202020202020")),
                // Test Bytes
                Arguments.of(BYTES, "05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612",
                        BytesUtils.fromHexString(
                                "000000000000000000000000000000000000000000000000000000000000002005A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
                Arguments.of(BYTES,
                        BytesUtils.fromHexString("2020202020202020202020202020202020202020202020202020202020202020"),
                        BytesUtils.fromHexString(
                                "00000000000000000000000000000000000000000000000000000000000000202020202020202020202020202020202020202020202020202020202020202020")),
                // Test TokenStandard
                Arguments.of(TOKEN_STANDARD, TokenStandard.ZNN_ZTS,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000014E66318C6318C6318C6")),
                Arguments.of(TOKEN_STANDARD, TokenStandard.ZNN_TOKEN_STANDARD,
                        BytesUtils.fromHexString("0000000000000000000000000000000000000000000014E66318C6318C6318C6")),
                // Test Hash
                Arguments.of(HASH, Byte.MAX_VALUE,
                        Hash.parse("000000000000000000000000000000000000000000000000000000000000007F").getBytes()),
                Arguments.of(HASH, Short.MAX_VALUE,
                        Hash.parse("0000000000000000000000000000000000000000000000000000000000007FFF").getBytes()),
                Arguments.of(HASH, Integer.MAX_VALUE,
                        Hash.parse("000000000000000000000000000000000000000000000000000000007FFFFFFF").getBytes()),
                Arguments.of(HASH, Long.MAX_VALUE,
                        Hash.parse("0000000000000000000000000000000000000000000000007FFFFFFFFFFFFFFF").getBytes()),
                Arguments.of(HASH, Float.MAX_VALUE,
                        Hash.parse("00000000000000000000000000000000FFFFFF000000000A35211F7CA1800000").getBytes()),
                Arguments.of(HASH, DOUBLE_MAX_VALUE,
                        Hash.parse("00FFFFFFFFFFFFF84B2EEE4658DD6B4CF30943D23D5B90166800000000000000").getBytes()),
                Arguments.of(HASH,
                        new BigInteger("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16),
                        Hash.parse("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF").getBytes()),
                Arguments.of(HASH, "05a0fef85008e63f0680b68d11743ba5caf199994d642590febe570b2a84b612",
                        Hash.parse("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612").getBytes()),
                Arguments.of(HASH,
                        BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"),
                        Hash.parse("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF").getBytes()),
                // Test Address
                Arguments.of(ADDRESS, "z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402",
                        BytesUtils.fromHexString("000000000000000000000000001F74A72493EEBDCC75463481B4E2D812C70903")),
                Arguments.of(ADDRESS, Address.parse("z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402"),
                        BytesUtils.fromHexString("000000000000000000000000001F74A72493EEBDCC75463481B4E2D812C70903")),
                // Test Function
                Arguments.of(FUNCTION,
                        new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4 },
                        BytesUtils.fromHexString("0102030405060708090001020304050607080900010203040000000000000000")),
                // Test Array
                Arguments.of(DYNAMIC_ARRAY, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, BytesUtils.fromHexString(
                        "0000000000000000000000000000000000000000000000000000000000000009000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000050000000000000000000000000000000000000000000000000000000000000006000000000000000000000000000000000000000000000000000000000000000700000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000009")),
                Arguments.of(STATIC_ARRAY, new int[] { 1, 2, 3, 4, 5 }, BytesUtils.fromHexString(
                        "00000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000300000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000005")));
    }

    private static Stream<Arguments> abiTypeEncodeInvalidTestData() {

        final BoolType BOOLEAN = new BoolType();
        final IntType INT = new IntType("int256");
        final UnsignedIntType UINT = new UnsignedIntType("uint256");
        final StringType STRING = new StringType();
        final Bytes32Type BYTES32 = new Bytes32Type("bytes32");
        final BytesType BYTES = new BytesType("bytes");
        final FunctionType FUNCTION = new FunctionType();
        final TokenStandardType TOKEN_STANDARD = new TokenStandardType();
        final HashType HASH = new HashType("hash");
        final AddressType ADDRESS = new AddressType();

        return Stream.of(
                // Test Boolean
                Arguments.of(BOOLEAN, Integer.MAX_VALUE), Arguments.of(INT, "NotANumber"),
                Arguments.of(UINT, BigInteger.valueOf(-1)), Arguments.of(BYTES32, Hash.EMPTY),
                Arguments.of(BYTES, Hash.EMPTY), Arguments.of(STRING, Integer.MAX_VALUE),
                Arguments.of(FUNCTION, "Function"), Arguments.of(TOKEN_STANDARD, TokenStandard.ZNN_ZTS.getBytes()),
                Arguments.of(HASH, Hash.EMPTY),
                Arguments.of(ADDRESS, Address.parse("z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402").getBytes()));
    }

    private static Stream<Arguments> abiTypeDecodeValidTestData() {

        final BoolType BOOLEAN = new BoolType();
        final IntType INT = new IntType("int256");
        final UnsignedIntType UINT = new UnsignedIntType("uint256");
        final StringType STRING = new StringType();
        final Bytes32Type BYTES32 = new Bytes32Type("bytes32");
        final BytesType BYTES = new BytesType("bytes");
        final TokenStandardType TOKEN_STANDARD = new TokenStandardType();
        final HashType HASH = new HashType("hash");
        final AddressType ADDRESS = new AddressType();

        return Stream.of(
                // Test Boolean
                Arguments.of(BOOLEAN,
                BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000001"),
                true),
            Arguments.of(BOOLEAN,
                BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000000000"),
                false),
            // Test Integer
            Arguments.of(INT,
                BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF80"),
                BigInteger.valueOf(Byte.MIN_VALUE)),
            Arguments.of(INT,
                BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF8000"),
                BigInteger.valueOf(Short.MIN_VALUE)),
            Arguments.of(INT,
                BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF80000000"),
                BigInteger.valueOf(Integer.MIN_VALUE)),
            Arguments.of(INT,
                BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF8000000000000000"),
                BigInteger.valueOf(Long.MIN_VALUE)),
            Arguments.of(INT,
                BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"),
                BigInteger.valueOf(-1)),
            Arguments.of(INT,
                BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612"),
                new BigInteger("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612", 16)),
            Arguments.of(INT,
                BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF8A432EB"),
                new BigInteger("-123456789")),
            // Test UnsignedInteger
            Arguments.of(UINT,
                BytesUtils.fromHexString("00000000000000000000000000000000000000000000000000000000000000FF"),
                BigInteger.valueOf(0xFF)),
            Arguments.of(UINT,
                BytesUtils.fromHexString("0000000000000000000000000000000000000000000000000000000000007FFF"),
                BigInteger.valueOf(Short.MAX_VALUE)),
            Arguments.of(UINT,
                BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000007FFFFFFF"),
                BigInteger.valueOf(Integer.MAX_VALUE)),
            Arguments.of(UINT,
                BytesUtils.fromHexString("0000000000000000000000000000000000000000000000007FFFFFFFFFFFFFFF"),
                BigInteger.valueOf(Long.MAX_VALUE)),
            Arguments.of(UINT,
                BytesUtils.fromHexString("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"),
                new BigInteger("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16)),
            Arguments.of(UINT,
                BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612"),
                new BigInteger("05a0fef85008e63f0680b68d11743ba5caf199994d642590febe570b2a84b612", 16)),
            Arguments.of(UINT,
                BytesUtils.fromHexString("00000000000000000000000000000000000000000000000000000000075BCD15"),
                new BigInteger("123456789")),
            Arguments.of(UINT,
                BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"),
                new BigInteger(BytesUtils.fromHexString("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"))),
            // Test String
            Arguments.of(STRING,
                BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000000000000C48656C6C6F205A656E6F6E210000000000000000000000000000000000000000"),
                "Hello Zenon!"),
            // Test Bytes32
            Arguments.of(BYTES32,
                BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000007FFFFFFF"),
                BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000007FFFFFFF")),
            Arguments.of(BYTES32,
                BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612"),
                BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
            // Test Bytes
            Arguments.of(BYTES,
                BytesUtils.fromHexString("000000000000000000000000000000000000000000000000000000000000002005A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612"),
                BytesUtils.fromHexString("05A0FEF85008E63F0680B68D11743BA5CAF199994D642590FEBE570B2A84B612")),
            // Test TokenStandard
            Arguments.of(TOKEN_STANDARD,
                BytesUtils.fromHexString("0000000000000000000000000000000000000000000014E66318C6318C6318C6"),
                TokenStandard.ZNN_ZTS),
            // Test Hash
            Arguments.of(HASH,
                BytesUtils.fromHexString("05a0fef85008e63f0680b68d11743ba5caf199994d642590febe570b2a84b612"),
                Hash.parse("05a0fef85008e63f0680b68d11743ba5caf199994d642590febe570b2a84b612")),
            // Test Address
            Arguments.of(ADDRESS,
                BytesUtils.fromHexString("000000000000000000000000001F74A72493EEBDCC75463481B4E2D812C70903"),
                Address.parse("z1qq0hffeyj0htmnr4gc6grd8zmqfvwzgrydt402")));
    }

    @ParameterizedTest
    @MethodSource(value = "abiTypeEncodeValidTestData")
    public void whenEncodeAbiTypeExpectToEqual(AbiType type, Object value, byte[] expectedResult) {
        // Execute
        byte[] result = type.encode(value);

        // Validate
        assertArrayEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource(value = "abiTypeEncodeInvalidTestData")
    public void whenEncodeAbiTypeExpectToFail(AbiType type, Object value) {
        // Execute
        assertThrows(Exception.class, () -> type.encode(value));
    }

    @ParameterizedTest
    @MethodSource(value = "abiTypeDecodeValidTestData")
    public void whenDecodeAbiTypeExpectToEqual(AbiType type, byte[] value, Object expectedResult) {
        // Execute
        Object result = type.decode(value, 0);

        // Validate
        if (result instanceof byte[])
            assertArrayEquals((byte[])expectedResult, (byte[])result);
        else
            assertEquals(expectedResult, result);
    }
}