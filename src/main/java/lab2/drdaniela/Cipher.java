package lab2.drdaniela;

public interface Cipher {
    // the size of the alphabet
    int ALPHABET_SIZE = 26;

    // main functions for encryption and decryption
    byte[] encrypt(final String message, final String key);
    String decrypt(final byte[] message, final String key);

}
