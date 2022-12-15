package streamCiphersBlockCiphers;

public interface Cipher1 {
    // the size of the alphabet
    int ALPHABET_SIZE = 26;

    // main functions for encryption and decryption
    String encrypt123(final String message, final String key);
    String decrypt123(final String message, final String key);

}
