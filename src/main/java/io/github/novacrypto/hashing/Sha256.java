package io.github.novacrypto.hashing;

import java.security.MessageDigest;

import io.github.novacrypto.toruntime.CheckedExceptionToRuntime;

public final class Sha256 {
    Sha256() {
    }

    public static byte[] sha256(final byte[] bytes) {
        return sha256(bytes, 0, bytes.length);
    }

    public static byte[] sha256(final byte[] bytes, final int offset, final int length) {
        final MessageDigest digest = sha256();
        digest.update(bytes, offset, length);
        return digest.digest();
    }

    public static byte[] sha256Twice(final byte[] bytes) {
        return sha256Twice(bytes, 0, bytes.length);
    }

    public static byte[] sha256Twice(final byte[] bytes, final int offset, final int length) {
        final MessageDigest digest = sha256();
        digest.update(bytes, offset, length);
        digest.update(digest.digest());
        return digest.digest();
    }

    private static MessageDigest sha256() {
        return CheckedExceptionToRuntime.toRuntime(new sha256());
    }

    static final class sha256 implements CheckedExceptionToRuntime.Func<MessageDigest> {
        @Override
        public MessageDigest run() throws Exception {
            return MessageDigest.getInstance("SHA-256");
        }
    }
}