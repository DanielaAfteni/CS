package lab2.test.java;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class CaesarPermutationCipher1
{

    private static final int TEST_KEY = 2;
    private static final int TEST_KEY_ALPH = 2;
    private static final String TEST_MESSAGE = "ACE";
    private static final String TEST_ENC_MESSAGE = "EGI";
    lab1.drdaniela.implementations.CaesarPermutationCipher1 cipherInstance = new lab1.drdaniela.implementations.CaesarPermutationCipher1(TEST_KEY, TEST_KEY_ALPH);

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
