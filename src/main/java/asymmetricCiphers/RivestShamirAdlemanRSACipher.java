package asymmetricCiphers;

import java.math.BigInteger;
import java.util.Random;

public class RivestShamirAdlemanRSACipher implements Cipher {

    // convertMessageIntoASCIIvalue - function which returns the converted character of the message into an ASCII value
    public static BigInteger convertMessageIntoASCIIvalue(String message) {
        message = message.toUpperCase();
        String cipherString = "";
        int i = 0;
        while (i < message.length()) {
            int character = (int) message.charAt(i);
            cipherString = cipherString + character;
            i++;
        }
        BigInteger cipherBig = new BigInteger(String.valueOf(cipherString));
        return cipherBig;
    }

    // convertASCIIvalueIntoMessage - function which returns the ASCII value of the message into a character
    public static String convertASCIIvalueIntoMessage(BigInteger message) {
        String cipherString = message.toString();
        String output = "";
        int i = 0;
        while (i < cipherString.length()) {
            int temp = Integer.parseInt(cipherString.substring(i, i + 2));
            char character = (char) temp;
            output = output + character;
            i = i + 2;
        }
        return output;
    }

    // generateRandomPrimeNumber - method responsible for finding the prime numbers
    @Override
    public BigInteger generateRandomPrimeNumber(int bits) {
        Random randomInteger = new Random();
        BigInteger randomLargePrime = BigInteger.probablePrime(bits, randomInteger);
        return randomLargePrime;
    }

    // numberN - function to determine the N number, by multiplying the 2 prime numbers
    @Override
    public BigInteger numberN(BigInteger q, BigInteger p) {
        return q.multiply(p);

    }

    // calculatePhiofN - function to calulate the phi of n, which is equal to the multiple of q and p, without 1 unit
    @Override
    public BigInteger calculatePhiofN(BigInteger q, BigInteger p) {
        BigInteger phiOfN = (q.subtract(BigInteger.ONE)).multiply(p.subtract(BigInteger.ONE));
        return phiOfN;
    }

    // calculateNumberE - function for determination if number E, that should correspond to some rules:
    //                                                                                                  1 < e < Phi(n)
    //                                                                                                  gcd(e,Phi) = 1
    @Override
    public BigInteger calculateNumberE(BigInteger phi) {
        Random rand = new Random();
        BigInteger e = new BigInteger(1024, rand);
        do {
            e = new BigInteger(1024, rand);
            while (e.min(phi).equals(phi)) {
                // while phi is smaller than e, look for a new e
                e = new BigInteger(1024, rand);
            }
        } while (!gcd(e, phi).equals(BigInteger.ONE));
        // if gcd(e,phi) isn't 1 then stay in loop
        return e;
    }

    // gcd - formula for calculation of the greatest common divisor between 2 numbers
    public static BigInteger gcd(BigInteger x, BigInteger y) {
        if (y.equals(BigInteger.ZERO)) {
            return x;
        } else {
            return gcd(y, x.mod(y));
        }
    }

    // extendedEuclidAlgorithm - method implements the extended Euclidean algorithm
    @Override
    public BigInteger[] extendedEuclidAlgorithm(BigInteger x, BigInteger y) {
        if (y.equals(BigInteger.ZERO)) return new BigInteger[] {
                x, BigInteger.ONE, BigInteger.ZERO
        };
        BigInteger[] values = extendedEuclidAlgorithm(y, x.mod(y));
        BigInteger d = values[0];
        BigInteger p = values[2];
        BigInteger q = values[1].subtract(x.divide(y).multiply(values[2]));
        return new BigInteger[] { d, p, q };
    }

    @Override
    public BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    @Override
    public BigInteger decrypt(BigInteger message, BigInteger d, BigInteger n) {
        return message.modPow(d, n);
    }
}
