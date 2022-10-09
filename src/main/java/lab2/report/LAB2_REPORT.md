# Intro to Cryptography. Modern ciphers. Stream Ciphers. Block Ciphers.

### Course: Cryptography & Security
### Author: Afteni Daniela

----

## Theory


A stream cipher is a symmetric key cipher where plaintext digits are combined with a pseudorandom cipher digit stream (keystream). In a stream cipher, each plaintext digit is encrypted one at a time with the corresponding digit of the keystream, to give a digit of the ciphertext stream. Since encryption of each digit is dependent on the current state of the cipher, it is also known as state cipher. In practice, a digit is typically a bit and the combining operation is an exclusive-or (XOR).
Cryptography consists a part of the science known as Cryptology. The other part is Cryptanalysis. There are a lot of different algorithms/mechanisms used in Cryptography, but in the scope of these laboratory works the students need to get familiar with some examples of each kind.

A stream cipher is a method of encrypting text (to produce ciphertext) in which a cryptographic key and algorithm are applied to each binary digit in a data stream, one bit at a time. The main alternative method to stream cipher is, in fact, the block cipher, where a key and algorithm are applied to blocks of data rather than individual bits in a stream.

In cryptography, a block cipher is a deterministic algorithm operating on fixed-length groups of bits, called blocks. They are specified elementary components in the design of many cryptographic protocols and are widely used to encrypt large amounts of data, including in data exchange protocols. It uses blocks as an unvarying transformation.

Even a secure block cipher is suitable for the encryption of only a single block of data at a time, using a fixed key. A multitude of modes of operation have been designed to allow their repeated use in a secure way to achieve the security goals of confidentiality and authenticity. However, block ciphers may also feature as building blocks in other cryptographic protocols, such as universal hash functions and pseudorandom number generators.


## Objectives:

* Get familiar with the symmetric cryptography, stream and block ciphers.

* Implement an example of a stream cipher.

* Implement an example of a block cipher.

* The implementation should, ideally follow the abstraction/contract/interface used in the previous laboratory work.

* Use packages/directories to logically split the files that you will have.

* As in the previous task, use a client class or test classes to showcase the execution of your programs.


## Stream Cipher

A stream cipher is an encryption technique that works byte by byte to transform plain text into code that's unreadable to anyone without the proper key.

Stream ciphers are linear, so the same key both encrypts and decrypts messages. And while cracking them can be difficult, hackers have managed to do it.

For that reason, experts feel stream ciphers aren't safe for widespread use. Even so, plenty of people still lean on the technology to pass information through the internet.

Stream ciphers rely on:

* Plaintext: a message to encode.
* Keystreams: A set of random characters replaces those in the plaintext. They could be numbers, letters, or symbols.
* Ciphertext: this is the encoded message.

Generating a key is a complicated mathematical process. Even so, most computers can push through each step in seconds.

Bits of plaintext enter the stream cipher, and the cipher manipulates each bit with the mathematical formula. The resulting text is completely scrambled, and the recipient can't read it without the proper key.

With the right key, a recipient can push the ciphertext back through the stream cipher and transform the garbled data back to plaintext.

The key typically used with a stream cipher is known as a one-time pad. Mathematically, a one-time pad is unbreakable because it's always at least the exact same size as the message it is encrypting.

Stream ciphers:
* The encryption is done one byte at a time.
* Stream ciphers use confusion to hide the plain text.
* Make use of substitution techniques to modify the plain text.
* The implementation is fairly complex.
* The execution is fast.

### Implementation description 

### Rivest Cipher 4 (RC4)

RC4 generates a pseudorandom stream of bits (a keystream). As with any stream cipher, these can be used for encryption by combining it with the plaintext using bitwise exclusive or; decryption is performed the same way (since exclusive or with given data is an involution). This is similar to the one-time pad, except that generated pseudorandom bits, rather than a prepared stream, are used.

To generate the keystream, the cipher makes use of a secret internal state which consists of two parts:

* A permutation of all 256 possible bytes (denoted "S").
* Two 8-bit index-pointers (denoted "i" and "j").

The permutation is initialized with a variable-length key, typically between 40 and 2048 bits, meaning minimum 5 bytes and maximum 256 bytes, using the key-scheduling algorithm (KSA). Once this has been completed, the stream of bits is generated using the pseudo-random generation algorithm (PRGA).

Explanation for key-scheduling algorithm (KSA)

The key-scheduling algorithm is used to initialize the permutation in the array "S". "keylength" is defined as the number of bytes in the key and can be in the range 1 ≤ keylength ≤ 256, typically between 5 and 16, corresponding to a key length of 40–128 bits. First, the array "S" is initialized to the identity permutation. S is then processed for 256 iterations in a similar way to the main PRGA, but also mixes in bytes of the key at the same time.

```
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
```

Using swap function, which swaps the value from s array at positions i and j:

```
private void swap(int i, int j, int[] s) {
        int temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
```

Explanation for pseudo-random generation algorithm (PRGA)

For as many iterations as are needed, the PRGA modifies the state and outputs a byte of the keystream. In each iteration, the PRGA:

* increments i;
* looks up the ith element of S, S[i], and adds that to j;
* exchanges the values of S[i] and S[j], then uses the sum S[i] + S[j] (modulo 256) as an index to fetch a third element of S (the keystream value K below);
* then bitwise exclusive ORed (XORed) with the next byte of the message to produce the next byte of either ciphertext or plaintext.

Each element of S is swapped with another element at least once every 256 iterations.


```
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
```

Encryption process, using key-scheduling algorithm (KSA) in pseudo-random generation algorithm (PRGA):

```
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
```

Decryption process, same as for encryption, using key-scheduling algorithm (KSA) in pseudo-random generation algorithm (PRGA):

```
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
```

In main:

```
    // Rivest Cipher RC4
        String message = "It is a cipher!";
        String key = "Secret key should be kept secret.";
        final Cipher rivestCipherRC4 = new RivestCipherRC4("This is pretty long key");
        byte[] encryptmessage = rivestCipherRC4.encrypt(message, key);
        String dencryptmessage = rivestCipherRC4.decrypt(encryptmessage, key);
        System.out.println(encryptmessage);
        System.out.println(dencryptmessage);
```

Test:

```
    public class RivestChiperRC4Test {
    private static final String TEST_KEY = "ABC DFE HGK LMN OPS";
    private static final String TEST_MESSAGE = "DrVasile";
    private static final String TEST_ENC_MESSAGE = "[B@17f052a3";

    private final Cipher rivestCipherRC4 = new RivestCipherRC4(TEST_KEY);

    @Test
    public void testCryptAndDecryptMessage() {

        final byte[] encryptedMessage = rivestCipherRC4.encrypt(TEST_MESSAGE, TEST_KEY);
        final String dencryptedMessage = rivestCipherRC4.decrypt(encryptedMessage, TEST_KEY);

        assertEquals(TEST_MESSAGE, dencryptedMessage);
    }
}
```

## Block Cipher

A block cipher takes a block of plaintext bits and generates a block of ciphertext bits, generally of same size. The size of block is fixed in the given scheme. The choice of block size does not directly affect to the strength of encryption scheme. The strength of cipher depends up on the key length.
A block cipher encrypts data in blocks using a deterministic algorithm and a symmetric key.

As in the case of stream ciphers, most encryption methods encrypt bits one by one (stream ciphers). Block ciphers, on the other hand, encrypt 128 bit blocks with a key of predetermined length: 128, 192, or 256 bits.

A 128-bit block cipher brings 128 bits of plaintext and encrypts it into 128 bits of ciphertext. Where the amount of plaintext is less than 128 bits, in this example, the cipher will employ methods to reconcile the difference (padding schemes).

Block ciphers have the advantage of high diffusion and strong tamper resistance without detection. They have the disadvantage of slower encryption speed since the entire block must be captured for encryption/decryption. Block ciphers also breed errors since a mistake in just one symbol could alter the whole block.
Block ciphers:
* The encryption is done one block of plain text at a time.
* Block ciphers use confusion and diffusion to hide the plain text.
* Make use of transposition techniques to modify the plain text.
* The implementation is simpler relative to the stream ciphers.
* The execution is slow compared to the stream ciphers.

### Implementation description

### Data encryption standard (DES)

The Data Encryption Standard is a symmetric-key algorithm for the encryption of digital data. Although its short key length of 56 bits makes it too insecure for modern applications, it has been highly influential in the advancement of cryptography.

Generally, DES is a block cipher and encrypts data in blocks of size of 64 bits each, which means 64 bits of plain text go as the input to DES, which produces 64 bits of ciphertext. The same algorithm and key are used for encryption and decryption, with minor differences. The key length is 56 bits.

It is mentioned that DES uses a 56-bit key. Actually, the initial key consists of 64 bits. However, before the DES process even starts, every 8th bit of the key is discarded to produce a 56-bit key. That is bit positions 8, 16, 24, 32, 40, 48, 56, and 64 are discarded. Thus, the discarding of every 8th bit of the key produces a 56-bit key from the original 64-bit key.

DES is based on the two fundamental attributes of cryptography: substitution (also called confusion) and transposition (also called diffusion). DES consists of 16 steps, each of which is called a round. Each round performs the steps of substitution and transposition. Let's continue with the broad-level steps in DES. 
* Firstly, the 64-bit plain text block is handed over to an initial Permutation (IP) function.
* The initial permutation is performed on plain text.
* Next, the initial permutation (IP) produces two halves of the permuted block; saying Left Plain Text (LPT) and Right Plain Text (RPT).
* Now each LPT and RPT go through 16 rounds of the encryption process.
* In the end, LPT and RPT are rejoined and a Final Permutation (FP) is performed on the combined block

The result of this process produces 64-bit ciphertext.


The initial permutation (IP) happens only once and it happens before the first round. It suggests how the transposition in IP should proceed. For example, it says that the IP replaces the first bit of the original plain text block with the 58th bit of the original plain text, the second bit with the 50th bit of the original plain text block, and so on.This is nothing but jugglery of bit positions of the original plain text block. the same rule applies to all the other bit positions shown in the figure.

After IP is done, the resulting 64-bit permuted text block is divided into two half blocks. Each half-block consists of 32 bits, and each of the 16 rounds, in turn, consists of the broad-level steps: key transformation, expansion permutation, Sbox permutation,  Pbox permutation, xor and swap. 


Encryption:

```
    public String encrypt1(String plainText, String key)
    {
        int i;
        String keys[] = getKeys(key);
        plainText = permutation(IP, plainText);
        for (i = 0; i < 16; i++) plainText = round(plainText, keys[i], i);
        plainText = plainText.substring(8, 16) + plainText.substring(0, 8);
        plainText = permutation(IP1, plainText);
        return plainText;
    }
```

Which firstly, prepares 16 keys for 16 rounds, by function getKeys:

```
    String[] getKeys(String key)
    {
        String keys[] = new String[16];
        key = permutation(PC1, key);
        for (int i = 0; i < 16; i++) {
            key = leftCircularShift(key.substring(0, 7), shiftBits[i]) + leftCircularShift( key.substring(7, 14), shiftBits[i]);
            keys[i] = permutation(PC2, key);
        }
        return keys;
    }
```

It uses first and second key permutation, by function permutation, which permutates input hexadecimal according to specified sequence:

```
    String permutation(int[] sequence, String input)
    {
        String output = "";
        input = hextoBin(input);
        for (int i = 0; i < sequence.length; i++) output += input.charAt(sequence[i] - 1);
        output = binToHex(output);
        return output;
    }
```

Which in order uses binary to hexadecimal conversion and hexadecimal to binary conversion:

```
    String hextoBin(String input)
    {
        int n = input.length() * 4;
        input = Long.toBinaryString(Long.parseUnsignedLong(input, 16));
        while (input.length() < n) input = "0" + input;
        return input;
    }
```

```
    String binToHex(String input)
    {
        int n = (int)input.length() / 4;
        input = Long.toHexString(Long.parseUnsignedLong(input, 2));
        while (input.length() < n) input = "0" + input;
        return input;
    }
```

Considering as well and the left Circular Shifting bits process:

```
    String leftCircularShift(String input, int numBits)
    {
        int n = input.length() * 4;
        int perm[] = new int[n];
        for (int i = 0; i < n - 1; i++) perm[i] = (i + 2);
        perm[n - 1] = 1;
        while (numBits-- > 0) input = permutation(perm, input);
        return input;
    }
```

Returning to the encryption process, we will go through the initial permutation process for the plaintext. And then start the 16 rounds, by function rouund:

```
    String round(String input, String key, int num)
    {
        String left = input.substring(0, 8);
        String temp = input.substring(8, 16);
        String right = temp;
        temp = permutation(EP, temp);
        temp = xor(temp, key);
        temp = sBox(temp);
        temp = permutation(P, temp);
        left = xor(left, temp);
        return right + left;
    }
```

It uses expansion permutation, xor temp and round key, lookup in s-box table, Straight P-box, and again xor, and swapper.

```
    String xor(String a, String b)
    {
        long t_a = Long.parseUnsignedLong(a, 16);
        long t_b = Long.parseUnsignedLong(b, 16);
        t_a = t_a ^ t_b;
        a = Long.toHexString(t_a);
        while (a.length() < b.length()) a = "0" + a;
        return a;
    }

    String sBox(String input)
    {
        String output = "";
        input = hextoBin(input);
        for (int i = 0; i < 48; i += 6) {
            String temp = input.substring(i, i + 6);
            int num = i / 6;
            int row = Integer.parseInt(temp.charAt(0) + "" + temp.charAt(5), 2);
            int col = Integer.parseInt(temp.substring(1, 5), 2);
            output += Integer.toHexString(sbox[num][row][col]);
        }
        return output;
    }
```

After this we will have 32-bit swap and final permutation (by permutation function).

Decryption: 

```
public String decrypt1(String plainText, String key)
    {
        int i;
        String keys[] = getKeys(key);
        plainText = permutation(IP, plainText);
        for (i = 15; i > -1; i--) plainText = round(plainText, keys[i], 15 - i);
        plainText = plainText.substring(8, 16) + plainText.substring(0, 8);
        plainText = permutation(IP1, plainText);
        return plainText;
    }
```

Decryption is done in the same way as encryption, but in the opposite direction, considering that we have to go through the: getting round keys, initial permutation, 16-rounds, 32-bit swap and final permutation.

In main:

```
        // Data encryption standard (DES)
        String text1 = "729451AECB872416";
        String key121 = "EBEACFDEF9148230";
        final Cipher1 dataEncryptionStandardDES1 = new DataEncryptionStandardDES();
        String encryptmessage1 = dataEncryptionStandardDES1.encrypt123(text1, key121);
        System.out.println(encryptmessage1);
        String dencryptmessage1 = dataEncryptionStandardDES1.decrypt123(encryptmessage1, key121);
        System.out.println(dencryptmessage1);
```

Test:

```
    public class DataEncryptionStandartDESTest {
    private static final String TEST_KEY = "EBEACFDEF9148133";
    private static final String TEST_MESSAGE = "729456AECB172416";
    private static final String TEST_ENC_MESSAGE = "79010F4940798708";

    private final Cipher1 dataEncryptionStandardDES = new DataEncryptionStandardDES();

    @Test
    public void testCryptMessage() {
        final String encryptedMessage = dataEncryptionStandardDES.encrypt123(TEST_MESSAGE, TEST_KEY);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = dataEncryptionStandardDES.decrypt123(TEST_ENC_MESSAGE, TEST_KEY);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
}
```


## Conclusions 

Both block and stream ciphers are symmetric key ciphers (like DES, RCx). Block ciphers convert plaintext to ciphertext block by block, while stream ciphers convert one byte at a time.

Most modern symmetric algorithms are block ciphers, though the block sizes vary (such as DES (64 bits), AES (128, 192, and 256 bits), and so on).

The advantage of a stream cipher is that stream encryption is faster (linear in time) and constant in space. It is unlikely to propagate errors, as an error in one byte's translation won't impact the next byte.

However, there's little diffusion as one plaintext symbol is directly translated to one ciphertext symbol. Also, the ciphertext is susceptible to insertions or modifications. If an attacker is able to break the algorithm, they may be able to insert text which looks authentic.

A stream cipher is used when the amount of plaintext is unknown (like audio or video streaming), or when extreme performance is important (like with very high speed connections, or for devices which need to be very efficient and compact, like smart cards).

A stream cipher works by generating a series of pseudorandom bytes which depend on the key (for any given key, the series of bytes is the same for encryption and decryption). Different keys will produce different strings of bytes.

In order to encrypt data the plaintext bytes are xored with the string of pseudorandom bytes. To decrypt, the ciphertext is xored with the same string in order to see the plaintext.

The advantage of a block cipher is that a block cipher has high diffusion (information from one plaintext symbol is spread into several cipher-text symbols). It is also fairly difficult for an attacker to insert symbols without detection, because they can't easily insert them into the middle of a block.

However, it is slower than a stream cipher (an entire block needs to be transmitted before encryption/decryption can happen) and if an error does occur, it can propagate throughout the block, corrupting the entire section.

Block ciphers are a better choice when we know the transmission size, such as in file transfer.

