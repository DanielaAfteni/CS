import drdaniela.Cipher;
import drdaniela.implementations.AffineCipher;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class AffineCipherTest
{
    // we include data as:
    //                      first substitution key number
    //                      second substitution key number
    //                      plaintext
    //                      ciphertext
    private static final int TEST_KEY1 = 1;
    private static final int TEST_KEY2 = 1;
    private static final String TEST_MESSAGE = "DrVasile";
    private static final String TEST_ENC_MESSAGE = "ESWBTJMF";

    private final Cipher cipherInstance = new AffineCipher(TEST_KEY1, TEST_KEY2);

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
