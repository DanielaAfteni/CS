package classicCiphers;

import javafx.util.Pair;

public class AffineCipher extends SubstitutionCipher
{
    // Affine Cipher with one key used for substitution
    // the key consists of 2 integer numbers as: a anb b
    private final Pair<Integer, Integer> keyPair;

    // create constructor
    public AffineCipher(final Integer a, final Integer b)
    {
        this.keyPair = new Pair<>(a, b);
    }

    // we override the encryption method considering the ASCII table for characters
    @Override
    protected Character encryptCharacter(Character currentChar)
    {
        // E(x) = (a * x + b) mod m
        // a, b - number of the key
        // m - alphabet size

        // 1. we subtract from the ASCII value of the currentChar 65 to determine the distance from char A to the currentChar
        // 2. multiply the calculated value by the first key number
        // 3. add to it the second key number
        // 4. we calculate: distance from char A to the currentChar mod the alphabet size
        // 5. return to the new ASCII value, by adding 65
        return (char) ((((currentChar - 65) * keyPair.getKey() + keyPair.getValue()) % ALPHABET_SIZE) + 65);
    }

    // we override the decryption method considering the ASCII table for characters
    @Override
    protected Character decryptCharacter(Character currentChar)
    {
        // D(x) = a^-1 * (x - b) mod m
        // a, b - number of the key
        // m - alphabet size

        // 1. we subtract from the ASCII value of the currentChar 65 to determine the distance from char A to the currentChar
        // 2. subtract from it the second key number
        // 3. multiply the calculated value by the first key number
        // 4. we calculate: distance from char A to the currentChar mod the alphabet size
        // 5. return to the new ASCII value, by adding 65
        return (char) (((((currentChar - 65) - keyPair.getValue()) * keyPair.getKey()) % ALPHABET_SIZE) + 65);
    }
}