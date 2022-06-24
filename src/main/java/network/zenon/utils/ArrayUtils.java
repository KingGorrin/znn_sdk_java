package network.zenon.utils;

import java.util.Arrays;

public class ArrayUtils {
    /**
     * Concatenate all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the element of {@code arrays}. When an array is returned, it is always a new
     * array.
     * </p>
     * @param arrays the arrays whose elements are added to the new array.
     * @return The new byte[] array.
     */
    public static byte[] concat(byte[]... arrays) {
        if (arrays == null || arrays.length == 0)
            return new byte[0];
        
        int finalLength = 0;
        for (byte[] array : arrays) {
            finalLength += array.length;
        }

        byte[] dest = null;
        int destPos = 0;

        for (byte[] array : arrays) {
            if (dest == null) {
                dest = Arrays.copyOf(array, finalLength);
                destPos = array.length;
            } else {
                System.arraycopy(array, 0, dest, destPos, array.length);
                destPos += array.length;
            }
        }
        return dest;
    }
    
    public static byte[] sublist(byte[] array, int startIndex, int endIndex) {
        return Arrays.copyOfRange(array, startIndex, endIndex);
    }

    /**
     * Clones an array returning a typecast result and handling {@code null}.
     * <p>
     * This method returns {@code null} for a {@code null} input array.
     * </p>
     *
     * @param array the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static byte[] clone(final byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
}
