package lab3.test.java;

import lab3.drdaniela.Cipher;
import lab3.drdaniela.implementations.RivestShamirAdlemanRSACipher;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;

public class RivestShamirAdlemanRSACipherTest {

    private static final String TEST_MESSAGE = "HELLO WORLD";

    private final Cipher cipherRSA = new RivestShamirAdlemanRSACipher();

    @Test
    public void testCryptMessage() {
        BigInteger p = cipherRSA.generateRandomPrimeNumber(512);
        BigInteger q = cipherRSA.generateRandomPrimeNumber(512);
        BigInteger n = cipherRSA.numberN(p, q);
        BigInteger phi = cipherRSA.calculatePhiofN(p, q);
        BigInteger e = cipherRSA.calculateNumberE(phi);
        BigInteger d = cipherRSA.extendedEuclidAlgorithm(e, phi)[1];
        BigInteger cipherMessage = ((RivestShamirAdlemanRSACipher) cipherRSA).convertMessageIntoASCIIvalue(TEST_MESSAGE);
        BigInteger encrypted = cipherRSA.encrypt(cipherMessage, e, n);
        BigInteger decrypted = cipherRSA.decrypt(encrypted, d, n);
        String restoredMessageAfterDecryption = ((RivestShamirAdlemanRSACipher) cipherRSA).convertASCIIvalueIntoMessage(decrypted);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(restoredMessageAfterDecryption);

        assertTrue(areEqual);
    }
}
