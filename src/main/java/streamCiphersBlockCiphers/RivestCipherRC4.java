package streamCiphersBlockCiphers;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RivestCipherRC4 implements Cipher {
    private static final int MAX_NUMBER_OF_BYTES = 256;
    private static final int MIN_LENGTH_OF_KEY = 5;
    private byte[] key = new byte[MAX_NUMBER_OF_BYTES - 1];
    private int[] s = new int[MAX_NUMBER_OF_BYTES];

    public RivestCipherRC4() {
        reset();
    }

    public RivestCipherRC4(String key) throws InvalidKeyException {
        this();
        if (!(key.length() >= MIN_LENGTH_OF_KEY && key.length() < MAX_NUMBER_OF_BYTES)) {
            throw new InvalidKeyException("Key length has to be between "
                    + MIN_LENGTH_OF_KEY + " and " + (MAX_NUMBER_OF_BYTES - 1));
        }
        this.key = key.getBytes();
    }

    private void reset() {
        Arrays.fill(key, (byte) 0);
        Arrays.fill(s, 0);
    }

    @Override
    public byte[] encrypt(String message, String key) {
        return encryptMessage(message, StandardCharsets.UTF_8, key);
    }

    @Override
    public String decrypt(byte[] message, String key) {
        return decryptMessage(message, StandardCharsets.UTF_8, key);
    }

    // main process for message encryption, using a charset and a given key
    // it returns encrypted message
    public byte[] encryptMessage(String message, Charset charset, String key) throws InvalidKeyException {
        reset();
        if (!(key.length() >= MIN_LENGTH_OF_KEY && key.length() < MAX_NUMBER_OF_BYTES)) {
            throw new InvalidKeyException("Key length has to be between "
                    + MIN_LENGTH_OF_KEY + " and " + (MAX_NUMBER_OF_BYTES - 1));
        }
        this.key = key.getBytes();
        byte[] crypt = crypt(message.getBytes());
        reset();
        return crypt;
    }

    public String decryptMessage(byte[] message, Charset charset, String key) throws InvalidKeyException {
        reset();
        if (!(key.length() >= MIN_LENGTH_OF_KEY && key.length() < MAX_NUMBER_OF_BYTES)) {
            throw new InvalidKeyException("Key length has to be between "
                    + MIN_LENGTH_OF_KEY + " and " + (MAX_NUMBER_OF_BYTES - 1));
        }
        this.key = key.getBytes();
        byte[] msg = crypt(message);
        reset();
        return new String(msg);
    }

    // Key-scheduling algorithm (KSA)
    // initialize the permutation in the array S
    // A permutation of all 256 possible bytes
    private int[] initS(byte[] key) {
        int[] s = new int[MAX_NUMBER_OF_BYTES];
        int j = 0;
        // First, the array "S" is initialized to the identity permutation.
        // The array is then processed for 256 iterations in a similar way to the main PRGA, but also mixes in bytes of the key at the same time.
        for (int i = 0; i < MAX_NUMBER_OF_BYTES; i++) { s[i] = i; }
        for (int i = 0; i < MAX_NUMBER_OF_BYTES; i++) {
            j = (j + s[i] + (key[i % key.length]) & 0xFF) % MAX_NUMBER_OF_BYTES;
            swap(i, j, s);
        }
        return s;
    }

    // Pseudo-random generation algorithm (PRGA)
    public byte[] crypt(final byte[] msg) {
        s = initS(key);
        byte[] code = new byte[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % MAX_NUMBER_OF_BYTES;
            j = (j + s[i]) % MAX_NUMBER_OF_BYTES;
            swap(i, j, s);
            int rand = s[(s[i] + s[j]) % MAX_NUMBER_OF_BYTES];
            code[n] = (byte) (rand ^ msg[n]);
        }
        return code;
    }

    private void swap(int i, int j, int[] s) {
        int temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}

