package lab2.drdaniela.implementations;

import lab1.drdaniela.implementations.SubstitutionCipher;

public class CaesarCipher extends SubstitutionCipher
{
    // Caesar Cipher with one key used for substitution
    private final int key;

    // create constructor
    public CaesarCipher(int key)
    {
        this.key = key;
    }

    // we override the encryption method considering the ASCII table for characters
    @Override
    protected Character encryptCharacter(Character currentChar)
    {
        // 1. to the ASCII value of the currentChar will be added the integer value of the inserted key
        // 2. subtract from the calculated value 65 to determine the distance from char A to the currentChar
        // 3. we calculate: distance from char A to the currentChar mod the alphabet size
        // 4. return to the new ASCII value, by adding 65
        return (char) (((currentChar + key - 65) % ALPHABET_SIZE) + 65);
    }

    // we override the decryption method considering the ASCII table for characters
    @Override
    protected Character decryptCharacter(Character currentChar)
    {
        // 1. from the ASCII value of the currentChar we subtract the value of the key
        // 2. as well as we subtract 65 to determine the distance from char A to the currentChar
        // 3. we calculate: distance from char A to the currentChar mod the alphabet size
        // 4. return to the new ASCII value, by adding 65
        return (char) (((currentChar - key - 65) % ALPHABET_SIZE) + 65);
    }
}
