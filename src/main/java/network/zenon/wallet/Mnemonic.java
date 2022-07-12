package network.zenon.wallet;

import java.security.SecureRandom;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.MnemonicValidator;
import io.github.novacrypto.bip39.WordList;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;

public class Mnemonic {
    public static String generateMnemonic(Words words) {
        return generateMnemonic(words, English.INSTANCE);
    }

    public static String generateMnemonic(Words words, WordList wordList) {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[words.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(wordList).createMnemonic(entropy, sb::append);
        return sb.toString();
    }

    public static boolean validateMnemonic(CharSequence words) {
        return validateMnemonic(words, English.INSTANCE);
    }

    public static boolean validateMnemonic(CharSequence words, WordList wordList) {
        try {
            MnemonicValidator.ofWordList(wordList).validate(words);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isValidWord(String word) {
        throw new UnsupportedOperationException();
    }
}