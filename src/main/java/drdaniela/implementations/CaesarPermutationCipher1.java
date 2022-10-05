package drdaniela.implementations;

import drdaniela.Cipher;

// rail fence cipher
public class CaesarPermutationCipher1 implements Cipher
{

    // Caesar Cipher with one key used for substitution and a permutation of the alphabet
    public final int keyAlph;
    private final int shiftKey;
    public String data = "ABCDEFGHIKLMNOPQRSTUVWXYZ";

    public CaesarPermutationCipher1(int shiftKey, int keyAlph) {
        this.shiftKey = shiftKey;
        this.keyAlph = keyAlph;
        
    }

    public int getKeyAlph(){
        return keyAlph;
    }
    
    
    public int getShiftKey(){
        return shiftKey;
    }


    public static String getDecryptedData(int keyAlph) {
        String data = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        char[] decrypted = new char[data.length()];
        int n = 0;
        for(int k = 0 ; k < keyAlph; k ++) {
            int index = k;
            boolean down = true;
            while(index < data.length() ) {
                decrypted[index] = data.charAt(n++);

                if(k == 0 || k == keyAlph - 1) {
                    index = index + 2 * (keyAlph - 1);
                }
                else if(down) {
                    index = index +  2 * (keyAlph - k - 1);
                    down = !down;
                }
                else {
                    index = index + 2 * k;
                    down = !down;
                }
            }
        }
        return new String(decrypted);
    }


    public static String getEncryptedData(int keyAlph) {
        String data = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        char[] encrypted = new char[data.length()];
        int n = 0;


        for(int k = 0 ; k < keyAlph; k ++) {
            int index = k;
            boolean down = true;
            while(index < data.length() ) {
                //System.out.println(k + " " + index+ " "+ n );
                encrypted[n++] = data.charAt(index);

                if(k == 0 || k == keyAlph - 1) {
                    index = index + 2 * (keyAlph - 1);
                }
                else if(down) {
                    index = index +  2 * (keyAlph - k - 1);
                    down = !down;
                }
                else {
                    index = index + 2 * k;
                    down = !down;
                }
            }
        }
        return new String(encrypted);
    }


    @Override
    public String encrypt(String message) {
        String ALPHABET = getEncryptedData(keyAlph);
        message = message.toUpperCase();

        // encryptStr to store encrypted data
        String encryptStr = "";

        // use for loop for traversing each character of the input string
        for (int i = 0; i < message.length(); i++)
        {
            // get position of each character of message in ALPHABET
            int pos = ALPHABET.indexOf(message.charAt(i));

            // get encrypted char for each char of message
            int encryptPos = (shiftKey + pos) % 26;
            char encryptChar = ALPHABET.charAt(encryptPos);

            // add encrypted char to encrypted string
            encryptStr += encryptChar;
        }

        // return encrypted string
        return encryptStr;
    }

    @Override
    public String decrypt(String message) {
        String ALPHABET = getEncryptedData(keyAlph);

        message = message.toUpperCase();
        // decryptStr to store decrypted data
        String decryptStr = "";

        // use for loop for traversing each character of the input string
        for (int i = 0; i < message.length(); i++)
        {

            // get position of each character of message in ALPHABET
            int pos = ALPHABET.indexOf(message.charAt(i));

            // get decrypted char for each char of message
            int decryptPos = (pos - shiftKey) % 26;

            // if decryptPos is negative
            if (decryptPos < 0){
                decryptPos = ALPHABET.length() + decryptPos;
            }
            char decryptChar = ALPHABET.charAt(decryptPos);

            // add decrypted char to decrypted string
            decryptStr += decryptChar;
        }
        // return decrypted string
        return decryptStr;
    }
}
