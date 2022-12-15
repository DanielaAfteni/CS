package classicCiphers;

public class VigenereCipher implements Cipher {

    // Vigenere Cipher with one key used for substitution
    private static String key = null;

    // create constructor
    public VigenereCipher(String key)
    {
        this.key = key;
    }

    public String getKey () {
        return key;
    }

    // we override the encryption method considering the ASCII table for characters
    @Override
    public String encrypt(String message) {
        // we initialise the sting for encrypted Message for the ciphertext
        String encryptedMessage = "";
        // set the plaintext to the upper case letters
        message = message.toUpperCase();
        // we iterate through the plaintext
        for (int i = 0, j = 0; i < message.length(); i++)
        {
            // select each character of the plaintext
            char currentChar = message.charAt(i);
            // if the currentChar is smaller than A or bigger than Z, then we skip the current iteration of the loop
            if (currentChar < 65 || currentChar > 90)
                continue;

            // 1. to the ASCII value of the currentChar will be added the ASCII value of a char of the inserted key
            // 2. subtract twice 65, because the minimal result would be: A + A => 65 + 65
            // 3. we calculate: distance from char A to the currentChar mod the alphabet size
            // 4. return to the new ASCII value, by adding 65

            encryptedMessage += (char) ((currentChar + key.charAt(j) - 2 * 65) % ALPHABET_SIZE + 65);
            // we calculate which char should be taken from the key
            // is needed in case the length of the key is smaller than the length of the plaintext
            j = ++j % key.length();
        }
        return encryptedMessage;
    }

    // we override the decryption method considering the ASCII table for characters
    @Override
    public String decrypt(String message) {
        // we initialise the sting for decrypted Message for the plaintext
        String decryptedMessage = "";
        // set the ciphertext to the upper case letters
        message = message.toUpperCase();
        // we iterate through the ciphertext
        for (int i = 0, j = 0; i < message.length(); i++)
        {
            // select each character of the ciphertext
            char currentChar = message.charAt(i);
            // if the currentChar is smaller than A or bigger than Z, then we skip the current iteration of the loop
            if (currentChar < 65 || currentChar > 90)
                continue;

            // 1. from the ASCII value of the currentChar will be subtracted the ASCII value of a char of the inserted key
            // 2. add 65, because the maximal result would be: Z - Z or A - A  => 0
            // 3. we calculate: distance from char A to the currentChar mod the alphabet size
            // 4. return to the new ASCII value, by adding 65

            decryptedMessage += (char) ((currentChar - key.charAt(j) + ALPHABET_SIZE) % ALPHABET_SIZE + 65);
            // we calculate which char should be taken from the key
            // is needed in case the length of the key is smaller than the length of the plaintext
            j = ++j % key.length();
        }
        return decryptedMessage;
    }
}
