package lab3.drdaniela;

import java.math.BigInteger;

public interface Cipher {
    // the size of the alphabet
    int ALPHABET_SIZE = 26;

    BigInteger generateRandomPrimeNumber(int bits);
    BigInteger numberN(BigInteger p, BigInteger q);
    BigInteger calculatePhiofN(BigInteger p, BigInteger q);
    BigInteger calculateNumberE(BigInteger phi);
    BigInteger[] extendedEuclidAlgorithm(BigInteger a, BigInteger b);
    // main functions for encryption and decryption
    BigInteger encrypt(final BigInteger message, final BigInteger e, final BigInteger n);
    BigInteger decrypt(final BigInteger message, final BigInteger d, final BigInteger n);
}
