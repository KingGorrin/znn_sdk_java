package network.zenon.model.primatives;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import network.zenon.model.primitives.Hash;

public class HashTest {
	   
	private static Stream<Arguments> validHashData() {
		return Stream.of(
			Arguments.of(
				"b3a023d751681b6aed507f7f7c4fa8a59dbee7ee11b3d3e39c294fc078d9b7b9"
	        ),
	        Arguments.of(
        		"6a50df02d365fe8034881d0bac17be58d5af4f5efec37c4b965a7ba05a557df0"
	        ),
	        Arguments.of(
        		"9ef6873791c43a3f380671970c58672e9604c617b529fca282b07e36576f8743"
	        ),
	        Arguments.of(
        		"29e36ceb9e12c8dd5c7f42b7fc7e0236fe3df2ac558bd60d1d27e329f75e1514"
		    )
	    );
	}
	
	@Test
    public void whenParseEmptyExpectToEqualEmpty() {
        // Setup
        String emptyHash = "0000000000000000000000000000000000000000000000000000000000000000";

        // Execute
        Hash hash = Hash.parse(emptyHash);

        // Validate
        assertEquals(hash, Hash.EMPTY);
    }
	
	@ParameterizedTest
    @MethodSource(value =  "validHashData")
    public void whenParseExpectToEqual(String hashString) {
        // Execute
		Hash hash = Hash.parse(hashString);

        // Validate
        assertEquals(hashString, hash.toString());
    }
	
	@Test
	public void whenDigestExpectToEqual() 
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// Setup
		byte[] data = "Hello World!".getBytes("UTF-8");
		
		// Execute
		Hash hash = Hash.digest(data);
		
		// Validate
		assertEquals("d0e47486bbf4c16acac26f8b653592973c1362909f90262877089f9c8a4536af", hash.toString());
		assertEquals("d0e474...4536af", hash.toShortString());
	}
}
