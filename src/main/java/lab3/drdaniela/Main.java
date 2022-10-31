package lab3.drdaniela;

import lab3.drdaniela.implementations.RivestShamirAdlemanRSACipher;

import java.math.BigInteger;

public class Main
{
    public static void main(String[] args)
    {
        // Rivest-Shamir-Adleman (RSA) Cipher
        final Cipher cipherRSA = new RivestShamirAdlemanRSACipher();
        String plaintext = "LABORATORY WORK ABOUT ASYMMETRIC CIPHERS";
        BigInteger p = cipherRSA.generateRandomPrimeNumber(512);
        BigInteger q = cipherRSA.generateRandomPrimeNumber(512);
        BigInteger n = cipherRSA.numberN(q, p);
        BigInteger phi = cipherRSA.calculatePhiofN(q, p);
        BigInteger e = cipherRSA.calculateNumberE(phi);
        BigInteger d = cipherRSA.extendedEuclidAlgorithm(e, phi)[1];
        BigInteger cipherMessage = ((RivestShamirAdlemanRSACipher) cipherRSA).convertMessageIntoASCIIvalue(plaintext);
        BigInteger encrypted = cipherRSA.encrypt(cipherMessage, e, n);
        BigInteger decrypted = cipherRSA.decrypt(encrypted, d, n);
        String restoredMessageAfterDecryption = ((RivestShamirAdlemanRSACipher) cipherRSA).convertASCIIvalueIntoMessage(decrypted);
        System.out.println(plaintext);
        System.out.println(encrypted);
        System.out.println(restoredMessageAfterDecryption);
    }
}