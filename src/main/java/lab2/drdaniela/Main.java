package lab2.drdaniela;

import lab2.drdaniela.implementations.DataEncryptionStandardDES;
import lab2.drdaniela.implementations.RivestCipherRC4;

public class Main
{
    public static void main(String[] args)
    {
        // Rivest Cipher RC4
        String message = "It is a cipher!";
        String key = "Secret key should be kept secret.";
        final Cipher rivestCipherRC4 = new RivestCipherRC4("This is pretty long key");
        byte[] encryptmessage = rivestCipherRC4.encrypt(message, key);
        String dencryptmessage = rivestCipherRC4.decrypt(encryptmessage, key);
        System.out.println(encryptmessage);
        System.out.println(dencryptmessage);

        // Data encryption standard (DES)
        String text1 = "729451AECB872416";
        String key121 = "EBEACFDEF9148230";
        final Cipher1 dataEncryptionStandardDES1 = new DataEncryptionStandardDES();
        String encryptmessage1 = dataEncryptionStandardDES1.encrypt123(text1, key121);
        System.out.println(encryptmessage1);
        String dencryptmessage1 = dataEncryptionStandardDES1.decrypt123(encryptmessage1, key121);
        System.out.println(dencryptmessage1);

    }
}