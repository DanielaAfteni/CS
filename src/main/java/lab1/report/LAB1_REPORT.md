# Intro to Cryptography. Classical ciphers. Caesar cipher.

### Course: Cryptography & Security
### Author: Afteni Daniela

----

## Theory


Cryptography consists a part of the science known as Cryptology. The other part is Cryptanalysis. There are a lot of different algorithms/mechanisms used in Cryptography, but in the scope of these laboratory works the students need to get familiar with some examples of each kind.

First, it is important to understand the basics so for the first task students will need to implement a classical and relatively simple cipher. This would be the Caesar cipher which uses substitution to encrypt a message.

In it's simplest form, the cipher has a key which is used to substitute the characters with the next ones, by the order number in a pre-established alphabet. Mathematically it would be expressed as follows:

em = x + k(mod; n)

dm = x + k(mod; n)

where:

* em: the encrypted message,
* dm: the decrypted message (i.e. the original one),
* x: input,
* k: key,
* n: size of the alphabet.

Judging by the encryption mechanism one can conclude that this cipher is pretty easy to break. In fact, a brute force attack would have O(nm) complexity, where n would be the size of the alphabet and m the size of the message. This is why there were other variations of this cipher, which are supposed to make the cryptanalysis more complex.


## Objectives:


* Get familiar with the basics of cryptography and classical ciphers.

* Implement 4 types of the classical ciphers:

* * Caesar cipher with one key used for substitution (as explained above),
* * Caesar cipher with one key used for substitution, and a permutation of the alphabet,
* * Vigenere cipher,
* * Playfair cipher. 
* * If I want you can implement other.
* Structure the project in methods/classes/packages as neeeded.

## Implementation description

### Caesar Cipher

The Caesar Cipher technique is one of the earliest and simplest methods of encryption technique. It’s simply a type of substitution cipher, i.e., each letter of a given text is replaced by a letter with a fixed number of positions down the alphabet. For example with a shift of 1, A would be replaced by B, B would become C, and so on. The method is apparently named after Julius Caesar, who apparently used it to communicate with his officials.

Thus to cipher a given text we need an integer value, known as a shift which indicates the number of positions each letter of the text has been moved down.
The encryption can be represented using modular arithmetic by first transforming the letters into numbers, according to the scheme, A = 0, B = 1,…, Z = 25. Encryption of a letter by a shift n can be described mathematically as.

Explanation for key setting

```
private final int key;

public CaesarCipher(int key)
{
    this.key = key;
}
```

Explanation for Caesar Cipher logic

Encryption

```
    @Override
    protected Character encryptCharacter(Character currentChar)
    {
        // 1. to the ASCII value of the currentChar will be added the integer value of the inserted key
        // 2. subtract from the calculated value 65 to determine the distance from char A to the currentChar
        // 3. we calculate: distance from char A to the currentChar mod the alphabet size
        // 4. return to the new ASCII value, by adding 65
        return (char) (((currentChar + key - 65) % ALPHABET_SIZE) + 65);
    }
```

Decryption

```
    @Override
    protected Character decryptCharacter(Character currentChar)
    {
        // 1. from the ASCII value of the currentChar we subtract the value of the key
        // 2. as well as we subtract 65 to determine the distance from char A to the currentChar
        // 3. we calculate: distance from char A to the currentChar mod the alphabet size
        // 4. return to the new ASCII value, by adding 65
        return (char) (((currentChar - key - 65) % ALPHABET_SIZE) + 65);
```

In main

```
// Caesar Cipher
        final Cipher cipher0 = new CaesarCipher(5);
        System.out.println(cipher0.encrypt("ABC"));
```

Test

```
    private static final int TEST_KEY = 1;
    private static final String TEST_MESSAGE = "DrVasile";
    private static final String TEST_ENC_MESSAGE = "ESWBTJMF";

    private final Cipher cipherInstance = new CaesarCipher(TEST_KEY);

    @Test
    public void testCipherEncryption()
    {
        final String encryptedMessage = cipherInstance.encrypt(TEST_MESSAGE);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = cipherInstance.decrypt(TEST_ENC_MESSAGE);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
```


### Affine Cipher

The affine cipher is a type of monoalphabetic substitution cipher, where each letter in an alphabet is mapped to its numeric equivalent, encrypted using a simple mathematical function, and converted back to a letter. The formula used means that each letter encrypts to one other letter, and back again, meaning the cipher is essentially a standard substitution cipher with a rule governing which letter goes to which. As such, it has the weaknesses of all substitution ciphers. Each letter is enciphered with the function (ax + b) mod 26, where b is the magnitude of the shift.

Explanation for key setting

```
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
```

Explanation for Affine Cipher logic

Encryption

```
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
```

Decryption

```
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
```

In main

```
// Affine Cipher
        final Cipher cipher1 = new AffineCipher(1, 5);
        System.out.println(cipher1.encrypt("ABC"));
```

Test

```
private static final int TEST_KEY1 = 1;
    private static final int TEST_KEY2 = 1;
    private static final String TEST_MESSAGE = "DrVasile";
    private static final String TEST_ENC_MESSAGE = "ESWBTJMF";

    private final Cipher cipherInstance = new AffineCipher(TEST_KEY1, TEST_KEY2);

    @Test
    public void testCipherEncryption()
    {
        final String encryptedMessage = cipherInstance.encrypt(TEST_MESSAGE);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = cipherInstance.decrypt(TEST_ENC_MESSAGE);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
```

### Vigenere Cipher

Vigenere Cipher is a method of encrypting alphabetic text. It uses a simple form of polyalphabetic substitution. A polyalphabetic cipher is any cipher based on substitution, using multiple substitution alphabets. The encryption of the original text is done using the Vigenère square or Vigenère table.

* The table consists of the alphabets written out 26 times in different rows, each alphabet shifted cyclically to the left compared to the previous alphabet, corresponding to the 26 possible Caesar Ciphers.
* At different points in the encryption process, the cipher uses a different alphabet from one of the rows.
* The alphabet used at each point depends on a repeating keyword.


Explanation for key setting

```
     // Vigenere Cipher with one key used for substitution
    private static String key = null;

    // create constructor
    public VigenereCipher(String key)
    {
        this.key = key;
    }
```

Explanation for Vigenere Cipher logic

Encryption

```
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
```

Decryption

```
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
```

In main

```
// Vigenere Cipher
        // F = A + 5
        final Cipher cipher2 = new VigenereCipher("FFF");
        System.out.println(cipher2.encrypt("ABC"));
        System.out.println(cipher1.decrypt("FGH"));
```

Test

```
    // B = +1
    private static final String TEST_KEY = "BBB";
    private static final String TEST_MESSAGE = "DrVasile";
    private static final String TEST_ENC_MESSAGE = "ESWBTJMF";

    private final Cipher cipherInstance = new VigenereCipher(TEST_KEY);

    @Test
    public void testCipherEncryption()
    {
        final String encryptedMessage = cipherInstance.encrypt(TEST_MESSAGE);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = cipherInstance.decrypt(TEST_ENC_MESSAGE);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
```

### Playfair Cipher

The Playfair cipher was the first practical digraph substitution cipher. The scheme was invented in 1854 by Charles Wheatstone but was named after Lord Playfair who promoted the use of the cipher. In playfair cipher unlike traditional cipher we encrypt a pair of alphabets(digraphs) instead of a single alphabet.

It was used for tactical purposes by British forces in the Second Boer War and in World War I and for the same purpose by the Australians during World War II. This was because Playfair is reasonably fast to use and requires no special equipment.

For the encryption process let us consider the Playfair Cipher Encryption Algorithm.
The Algorithm consists of 2 steps:

1. Generate the key Square(5×5):
* The key square is a 5×5 grid of alphabets that acts as the key for encrypting the plaintext. Each of the 25 alphabets must be unique and one letter of the alphabet (usually J) is omitted from the table (as the table can hold only 25 alphabets). If the plaintext contains J, then it is replaced by I.

* The initial alphabets in the key square are the unique alphabets of the key in the order in which they appear followed by the remaining letters of the alphabet in order.

2. Algorithm to encrypt the plain text: The plaintext is split into pairs of two letters (digraphs). If there is an odd number of letters, a Z is added to the last letter.

Rules to respect:
1. Pair cannot be made with same letter. Break the letter in single and add a bogus letter to the previous letter.
2. If the letter is standing alone in the process of pairing, then add an extra bogus letter with the alone letter.
3. If both the letters are in the same column: Take the letter below each one (going back to the top if at the bottom).
4. If both the letters are in the same row: Take the letter to the right of each one (going back to the leftmost if at the rightmost position).
5. If neither of the above rules is true: Form a rectangle with the two letters and take the letters on the horizontal opposite corner of the rectangle.


Explanation for key setting

```
    private int length = 0;
    private static String key = null;
    //creates a matrix for Playfair cipher
    private String [][] table;

    public PlayfairCipher1(String text, String key2)
    {
        String key = parseString(key2);
        while(key.equals(""))
            key = parseString(key2);
        table = this.cipherTable(key);

        String input = parseString(text);
        while(input.equals(""))
            input = parseString(text);
    }
```

Explanation for Playfair Cipher logic

Encryption

```
    @Override
    public String encrypt(String message) {
        length = (int) message.length() / 2 + message.length() % 2;
        //we insert x between double-letter digraphs & redefines "length"
        for(int i = 0; i < (length - 1); i++)
        {
            // if 2 letters stay near each other
            if(message.charAt(2 * i) == message.charAt(2 * i + 1))
            {
                message = new StringBuffer(message).insert(2 * i + 1, 'X').toString();
                length = (int) message.length() / 2 + message.length() % 2;
            }
        }
        // we need to make plaintext of even length
        // create an array of digraphs
        String[] digraph = new String[length];
        //loop iterates over the plaintext
        for(int j = 0; j < length ; j++)
        {
            //checks the plaintext is of even length or not
            if(j == (length - 1) && message.length() / 2 == (length - 1))
                //if not addends X at the end of the plaintext
                message = message + "X";
            digraph[j] = message.charAt(2 * j) + "" + message.charAt(2 * j + 1);
        }
        //encodes the digraphs and returns the output
        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for(int k = 0; k < length; k++)
            out = out + encDigraphs[k];
        return out;
    }
```

Decryption

```
    @Override
    public String decrypt(String message) {
        String decoded = "";
        for(int i = 0; i < message.length() / 2; i++)
        {
            char a = message.charAt(2 * i);
            char b = message.charAt(2 * i + 1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if(r1 == r2)
            {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            }
            else if(c1 == c2)
            {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            }
            else
            {
            //swapping logic
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded = decoded + table[r1][c1] + table[r2][c2];
        }
        //returns the decoded message
        return decoded;
    }
```

In main

```
// Playfair Cipher1
        final PlayfairCipher1 cipher3 = new PlayfairCipher1("SECRET", "KEYWORD");
        System.out.println(cipher3.encrypt("SECRET"));
        System.out.println(cipher3.decrypt("NORDKU"));
```

Test

```
    private static final String TEST_KEY = "KEYWORD";
    private static final String TEST_MESSAGE = "SECRET";
    private static final String TEST_ENC_MESSAGE = "NORDKU";

    private final lab1.drdaniela.implementations.PlayfairCipher1 cipherInstance = new lab1.drdaniela.implementations.PlayfairCipher1(TEST_MESSAGE, TEST_KEY);

    @Test
    public void testCipherEncryption()
    {
        final String encryptedMessage = cipherInstance.encrypt(TEST_MESSAGE);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = cipherInstance.decrypt(TEST_ENC_MESSAGE);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
```

### Caesar Permutation Cipher

In this cipher, beside the substitution key (from the Caesar Cipher), it will combine as well the key permutation of the alphabet. This would represent that before the encryption of the plaintext by substitution key, it will be necessary to permutate the used alphabet.  
The Caesar Cipher technique is one of the earliest and simplest methods of encryption technique. It’s simply a type of substitution cipher, i.e., each letter of a given text is replaced by a letter with a fixed number of positions down the alphabet. For example with a shift of 1, A would be replaced by B, B would become C, and so on. The method is apparently named after Julius Caesar, who apparently used it to communicate with his officials.

Thus to cipher a given text we need an integer value, known as a shift which indicates the number of positions each letter of the text has been moved down.
The encryption can be represented using modular arithmetic by first transforming the letters into numbers, according to the scheme, A = 0, B = 1,…, Z = 25. Encryption of a letter by a shift n can be described mathematically as.


Explanation for key setting

```
    // Caesar Cipher with one key used for substitution and a permutation of the alphabet
    public final int keyAlph;
    private final int shiftKey;
    public String data = "ABCDEFGHIKLMNOPQRSTUVWXYZ";

    public CaesarPermutationCipher1(int shiftKey, int keyAlph) {
        this.shiftKey = shiftKey;
        this.keyAlph = keyAlph;
        
    }
```

Explanation for Caesar Permutation Cipher logic



Encryption

```
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
```

Decryption

```
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
```

In main

```
// Caesar Permutation Cipher1
        final CaesarPermutationCipher1 cipher4 = new CaesarPermutationCipher1(2, 2);
        System.out.println(cipher4.getEncryptedData(2));
        System.out.println(cipher4.encrypt("ACE"));
        System.out.println(cipher4.decrypt("EGI"));
```

Test

```
    private static final int TEST_KEY = 2;
    private static final int TEST_KEY_ALPH = 2;
    private static final String TEST_MESSAGE = "ACE";
    private static final String TEST_ENC_MESSAGE = "EGI";
    lab1.drdaniela.implementations.CaesarPermutationCipher1 cipherInstance = new lab1.drdaniela.implementations.CaesarPermutationCipher1(TEST_KEY, TEST_KEY_ALPH);

    @Test
    public void testCipherEncryption()
    {
        final String encryptedMessage = cipherInstance.encrypt(TEST_MESSAGE);
        final boolean areEqual = TEST_ENC_MESSAGE.equalsIgnoreCase(encryptedMessage);

        assertTrue(areEqual);
    }

    @Test
    public void testCipherDecryption()
    {
        final String decryptedMessage = cipherInstance.decrypt(TEST_ENC_MESSAGE);
        final boolean areEqual = TEST_MESSAGE.equalsIgnoreCase(decryptedMessage);

        assertTrue(areEqual);
    }
```

## Conclusions 

In cryptography, a classical cipher is a type of cipher that was used historically but for the most part, has fallen into disuse. In contrast to modern cryptographic algorithms, most classical ciphers can be practically computed and solved by hand. However, they are also usually very simple to break with modern technology. The term includes the simple systems used since Greek and Roman times, the elaborate Renaissance ciphers, World War II cryptography such as the Enigma machine and beyond. Those implemented ciphers are Caesar Cipher, Affine Cipher, Vigenere Cipher, Playfair Cipher and Caesar Permutation Cipher.

In contrast, modern strong cryptography relies on new algorithms and computers developed since the 1970s. Nowadays, the only reason to use a classical cipher is for entertainment. Even an old, slow computer can break any of these with relative ease. If you need real security, we’ll want to use a modern encryption method. 