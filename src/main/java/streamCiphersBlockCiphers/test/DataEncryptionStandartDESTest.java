package streamCiphersBlockCiphers.test;

import streamCiphersBlockCiphers.Cipher1;
import streamCiphersBlockCiphers.DataEncryptionStandardDES;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DataEncryptionStandartDESTest {
    private static final String TEST_KEY = "EBEACFDEF9148133";
    private static final String TEST_MESSAGE = "729456AECB172416";
    private static final String TEST_ENC_MESSAGE = "79010F4940798708";

    private final Cipher1 dataEncryptionStandardDES = new DataEncryptionStandardDES();

    @Test
    public void testCryptMessage() {
        final String encryptedMessage = dataEncryptionStandardDES.encrypt123(TEST_MESSAGE, TEST_KEY);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = dataEncryptionStandardDES.decrypt123(TEST_ENC_MESSAGE, TEST_KEY);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
}
