package lab2.test.java;

import lab2.drdaniela.Cipher;
import lab2.drdaniela.implementations.RivestCipherRC4;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RivestChiperRC4Test {
    private static final String TEST_KEY = "ABC DFE HGK LMN OPS";
    private static final String TEST_MESSAGE = "DrVasile";
    private static final String TEST_ENC_MESSAGE = "[B@17f052a3";

    private final Cipher rivestCipherRC4 = new RivestCipherRC4(TEST_KEY);

    @Test
    public void testCryptAndDecryptMessage() {

        final byte[] encryptedMessage = rivestCipherRC4.encrypt(TEST_MESSAGE, TEST_KEY);
        final String dencryptedMessage = rivestCipherRC4.decrypt(encryptedMessage, TEST_KEY);

        assertEquals(TEST_MESSAGE, dencryptedMessage);
    }
}
