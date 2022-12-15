package classicCiphers;

public interface Cipher {
    // the size of the alphabet
    int ALPHABET_SIZE = 26;

    // main functions for encryption and decryption
    String encrypt(final String message);
    String decrypt(final String message);

}
