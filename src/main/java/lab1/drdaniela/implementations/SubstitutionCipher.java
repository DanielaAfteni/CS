package lab1.drdaniela.implementations;

import lab1.drdaniela.Cipher;

import java.util.Locale;

public abstract class SubstitutionCipher implements Cipher
{
    // we implement the encryption method
    @Override
    public String encrypt(String message)
    {
        final String upperCaseMessage = message.toUpperCase(Locale.ROOT);
        final StringBuilder encryptedMessage = new StringBuilder(upperCaseMessage.length());

        for (char character : upperCaseMessage.toCharArray())
        {
            encryptedMessage.append(encryptCharacter(character));
        }

        return encryptedMessage.toString();
    }

    // we implement the decryption method
    @Override
    public String decrypt(String message)
    {
        final String upperCaseMessage = message.toUpperCase(Locale.ROOT);
        final StringBuilder decryptedMessage = new StringBuilder(upperCaseMessage.length());

        for (char character : upperCaseMessage.toCharArray())
        {
            decryptedMessage.append(decryptCharacter(character));
        }

        return decryptedMessage.toString();
    }

    protected abstract Character encryptCharacter(final Character currentChar);

    protected abstract Character decryptCharacter(final Character currentChar);
}
