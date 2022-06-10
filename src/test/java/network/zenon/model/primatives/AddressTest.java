package network.zenon.model.primatives;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import network.zenon.model.primitives.Address;
import network.zenon.utils.BytesUtils;

public class AddressTest {
	
	private static Stream<Arguments> validAddressData() {
		return Stream.of(
			Arguments.of(
	            "z1qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqsggv2f",
	            "z1qqqqq...sggv2f",
        		"z",
	            BytesUtils.fromHexString("0000000000000000000000000000000000000000"),
	            false
	        ),
	        Arguments.of(
        		"z1qxemdeddedxplasmaxxxxxxxxxxxxxxxxsctrp",
				"z1qxemd...xsctrp",
				"z",
	            BytesUtils.fromHexString("01b3b6e5adcb4c1ff61be98c6318c6318c6318c6"),
	            true
	        ),
	        Arguments.of(
        		"z1qxemdeddedxpyllarxxxxxxxxxxxxxxxsy3fmg",
				"z1qxemd...sy3fmg",
				"z",
	            BytesUtils.fromHexString("01b3b6e5adcb4c127ffd198c6318c6318c6318c6"),
	            true
	        ),
	        Arguments.of(
        		"z1qxemdeddedxt0kenxxxxxxxxxxxxxxxxh9amk0",
				"z1qxemd...h9amk0",
				"z",
	            BytesUtils.fromHexString("01b3b6e5adcb4cb7db33318c6318c6318c6318c6"),
	            true
		    ),
	        Arguments.of(
        		"z1qxemdeddedxsentynelxxxxxxxxxxxxxwy0r2r",
				"z1qxemd...wy0r2r",
				"z",
	            BytesUtils.fromHexString("01b3b6e5adcb4d0ccd649e7e6318c6318c6318c6"),
	            true
	        ),
	        Arguments.of(
        		"z1qxemdeddedxswapxxxxxxxxxxxxxxxxxxl4yww",
				"z1qxemd...xl4yww",
				"z",
	            BytesUtils.fromHexString("01b3b6e5adcb4d077426318c6318c6318c6318c6"),
	            true
	        ),
	        Arguments.of(
        		"z1qxemdeddedxstakexxxxxxxxxxxxxxxxjv8v62",
				"z1qxemd...jv8v62",
				"z",
	            BytesUtils.fromHexString("01b3b6e5adcb4d05f6d9318c6318c6318c6318c6"),
	            true
	        ),
	        Arguments.of(
        		"z1qxemdeddedxsp0rkxxxxxxxxxxxxxxxx956u48",
				"z1qxemd...956u48",
				"z",
	            BytesUtils.fromHexString("01b3b6e5adcb4d00bc76318c6318c6318c6318c6"),
	            false
	        ),
	        Arguments.of(
        		"z1qxemdeddedxaccelerat0rxxxxxxxxxxp4tk22",
				"z1qxemd...p4tk22",
				"z",
				BytesUtils.fromHexString("01b3b6e5adcb4ddc633fc8fab78cc6318c6318c6"),
	            true
	        ),
	        Arguments.of(
        		"z1qzlytaqdahg5t02nz5096frflfv7dm3y7yxmg7",
				"z1qzlyt...7yxmg7",
				"z",
	            BytesUtils.fromHexString("00be45f40dedd145bd53151e5d2469fa59e6ee24"),
	            false
	        ),
	        Arguments.of(
        		"z1qrs43le3a5pdrn8jk0jwl8xu6ltxjy2g8mz2ls",
				"z1qrs43...8mz2ls",
				"z",
	            BytesUtils.fromHexString("00e158ff31ed02d1ccf2b3e4ef9cdcd7d6691148"),
	            false
	        ),
	        Arguments.of(
        		"z1qzzs6aju4vz4d8ndqch8kw8v8v97tcs9vhujvx",
				"z1qzzs6...vhujvx",
				"z",
	            BytesUtils.fromHexString("00850d765cab05569e6d062e7b38ec3b0be5e205"),
	            false
	        ),
		    Arguments.of(
        		"z1qr6f4pmyycu44emt9t8cshkvlvqvdj7s22l6tw",
				"z1qr6f4...22l6tw",
				"z",
	            BytesUtils.fromHexString("00f49a876426395ae76b2acf885eccfb00c6cbd0"),
	            false
	        )
	    );
	}
	
	@ParameterizedTest
    @MethodSource(value =  "validAddressData")
    public void whenParseExpectToEqual(String addressString, String shortString, String hrp, byte[] bytes, boolean embedded)
    {
        // Execute
        Address address = Address.parse(addressString);

        // Validate
        assertEquals(addressString, address.toString());
        assertEquals(shortString, address.toShortString());
        assertEquals(hrp, address.getHrp());
        assertArrayEquals(bytes, address.getBytes());
        assertEquals(embedded, address.isEmbedded());
    }
	
	@ParameterizedTest
	@CsvSource({"'OLuRUkw86l8uCdnm0iG0J2KpALiQUjhQEflvut53HSU=', 'z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y'"})
	public void whenParseFromPublicKeyExpectToEqual(String publicKey, String expectedAddress) 
			throws NoSuchAlgorithmException {
		// Execute
        Address address = Address.fromPublicKey(BytesUtils.fromBase64String(publicKey));
        
        // Validate
        assertEquals(expectedAddress, address.toString());
	}
	
	@Test
	public void validAddress() {
		assertTrue(Address.isValid("z1qqwttth8sj5fchuqyr0ctum63hax2rqfyswk8y"));
	}
	
	@Test
	public void invalidAddress() {
		assertFalse(Address.isValid("qwttth8sj5fchuq0ctum63hax2rqfyswk8y"));
	}
	
	@Test
	public void notEqualsNull() {
		assertNotEquals(Address.PLASMA_ADDRESS, null);
	}
	
	@Test
	public void equalsReference() {
		assertEquals(Address.PLASMA_ADDRESS, Address.PLASMA_ADDRESS);
	}
	
	@Test
	public void notEqualsOther() {
		assertNotEquals(Address.PLASMA_ADDRESS, Address.STAKE_ADDRESS);
	}
}
