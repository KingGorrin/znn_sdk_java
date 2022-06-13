package network.zenon.utils;

public class AmountUtils {
    public static long extractDecimals(double num, long decimals) {
        return (long) (num * Math.pow(10, decimals));
    }

    public static double addDecimals(long num, long decimals) {
        double numberWithDecimals = num / Math.pow(10, decimals);
        if (numberWithDecimals == (long) numberWithDecimals) {
            return (long) numberWithDecimals;
        }
        return numberWithDecimals;
    }
}