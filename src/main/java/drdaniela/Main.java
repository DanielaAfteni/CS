package drdaniela;

import drdaniela.implementations.*;

public class Main
{
    public static void main(String[] args)
    {
        // Caesar Cipher
        final Cipher cipher0 = new CaesarCipher(5);
        System.out.println(cipher0.encrypt("ABC"));

        // Affine Cipher
        final Cipher cipher1 = new AffineCipher(1, 5);
        System.out.println(cipher1.encrypt("ABC"));

        // Vigenere Cipher
        // F = A + 5
        final Cipher cipher2 = new VigenereCipher("FFF");
        System.out.println(cipher2.encrypt("ABC"));
        System.out.println(cipher1.decrypt("FGH"));

        // Playfair Cipher1
        final PlayfairCipher1 cipher3 = new PlayfairCipher1("SECRET", "KEYWORD");
        System.out.println(cipher3.encrypt("SECRET"));
        System.out.println(cipher3.decrypt("NORDKU"));

        // Caesar Permutation Cipher1
        final CaesarPermutationCipher1 cipher4 = new CaesarPermutationCipher1(2, 2);
        System.out.println(cipher4.getEncryptedData(2));
        System.out.println(cipher4.encrypt("ACE"));
        System.out.println(cipher4.decrypt("EGI"));
    }
}