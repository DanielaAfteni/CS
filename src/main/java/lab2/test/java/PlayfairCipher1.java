package lab2.test.java;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PlayfairCipher1
{
    // we include data as:
    //                      key
    //                      plaintext
    //                      ciphertext
    private static final String TEST_KEY = "KEYWORD";
    private static final String TEST_MESSAGE = "SECRET";
    private static final String TEST_ENC_MESSAGE = "NORDKU";

    private final lab1.drdaniela.implementations.PlayfairCipher1 cipherInstance = new lab1.drdaniela.implementations.PlayfairCipher1(TEST_MESSAGE, TEST_KEY);

    // we test if the encryption is done in a right way
    // it consists of the comparison between the introduced plaintext and the ciphertext, after the encryption process
    @Test
    public void testCipherEncryption()
    {
        final String encryptedMessage = cipherInstance.encrypt(TEST_MESSAGE);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    // we test if the encryption is done in a right way
    // it consists of the comparison between the introduced plaintext and the ciphertext, after the encryption process
    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = cipherInstance.decrypt(TEST_ENC_MESSAGE);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
}
