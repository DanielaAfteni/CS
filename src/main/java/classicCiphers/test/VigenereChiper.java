package classicCiphers.test;

import classicCiphers.Cipher;
import classicCiphers.VigenereCipher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class VigenereChiper
{
    // B = +1
    private static final String TEST_KEY = "BBB";
    private static final String TEST_MESSAGE = "DrVasile";
    private static final String TEST_ENC_MESSAGE = "ESWBTJMF";

    private final Cipher cipherInstance = new VigenereCipher(TEST_KEY);

    @Test
    public void testCipherEncryption()
    {
        final String encryptedMessage = cipherInstance.encrypt(TEST_MESSAGE);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = cipherInstance.decrypt(TEST_ENC_MESSAGE);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
}
