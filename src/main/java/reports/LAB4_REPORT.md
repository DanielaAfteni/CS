# Hash functions and Digital Signatures.

### Course: Cryptography & Security
### Author: Afteni Daniela

----

## Theory

Hashing is a technique used to compute a new representation of an existing value, message or any piece of text. The new representation is also commonly called a digest of the initial text, and it is a one way function meaning that it should be impossible to retrieve the initial content from the digest.

    Such a technique has the following usages:

* Offering confidentiality when storing passwords,
* Checking for integrity for some downloaded files or content,
* Creation of digital signatures, which provides integrity and non-repudiation.
    In order to create digital signatures, the initial message or text needs to be hashed to get the digest. After that, the digest is to be encrypted using a public key encryption cipher. Having this, the obtained digital signature can be decrypted with the public key and the hash can be compared with an additional hash computed from the received message to check the integrity of it.


## Objectives:

1. Get familiar with the hashing techniques/algorithms.

2. Use an appropriate hashing algorithms to store passwords in a local DB.
* * You can use already implemented algortihms from libraries provided for your language.
* * The DB choise is up to you, but it can be something simple, like an in memory one.

3. Use an asymmetric cipher to implement a digital signature process for a user message.
* * Take the user input message.
* * Preprocess the message, if needed.
* * Get a digest of it via hashing.
* * Encrypt it with the chosen cipher.
* * Perform a digital signature check by comparing the hash of the message with the decrypted one.

## SHA256 (Secure Hash Algorithm)

Hashing is the process of scrambling raw information to the extent that it cannot reproduce it back to its original form. 

It takes a piece of information and passes it through a function that performs mathematical operations on the plaintext. This function is called the hash function, and the output is called the hash value/digest. 

Hash function is responsible for converting the plaintext to its respective hash digest. They are designed to be irreversible, which means your digest should not provide you with the original plaintext by any means necessary. Hash functions also provide the same output value if the input remains unchanged, irrespective of the number of iterations.

There are two primary applications of hashing:

* Password Hashes: In most website servers, it converts user passwords into a hash value before being stored on the server. It compares the hash value re-calculated during login to the one stored in the database for validation.

* Integrity Verification: When it uploads a file to a website, it also shared its hash as a bundle. When a user downloads it, it can recalculate the hash and compare it to establish data integrity.

SHA 256 is a part of the SHA 2 family of algorithms, where SHA stands for Secure Hash Algorithm. Published in 2001, it was a joint effort between the NSA and NIST to introduce a successor to the SHA 1 family, which was slowly losing strength against brute force attacks.

The significance of the 256 in the name stands for the final hash digest value, i.e. irrespective of the size of plaintext/cleartext, the hash value will always be 256 bits.

The other algorithms in the SHA family are more or less similar to SHA 256. 

Some of the standout features of the SHA algorithm are as follows:

* Message Length: The length of the cleartext should be less than 264 bits. The size needs to be in the comparison area to keep the digest as random as possible.
* Digest Length: The length of the hash digest should be 256 bits in SHA 256 algorithm, 512 bits in SHA-512, and so on. Bigger digests usually suggest significantly more calculations at the cost of speed and space.
* Irreversible: By design, all hash functions such as the SHA 256 are irreversible. You should neither get a plaintext when you have the digest beforehand nor should the digest provide its original value when you pass it through the hash function again.


### Implementation description


Steps in SHA-256 Algorithm:

1. Padding Bits
2. Padding Length
3. Initialising the Buffers
4. Compression Functions
5. Output

The main structure in which was stored all the information about the user was the storageMemoryDatabase.
   

```
type storageMemoryDatabase struct {
	data map[string]User
}

func NewStorageMemoryDatabase() Database {
	return &storageMemoryDatabase{data: make(map[string]User)}
}

```

Those steps are based on the main user and database. User has e-mail, password and private key. The password is stored as a byte array, because it is the output of the hashing algorithm. The private key is used to decrypt the digital signature.

```
type User struct {
	Email      string
	Password   []byte
	PrivateKey *rsa.PrivateKey
}

type Database interface {
	Get(key string) (User, error)
	Set(key string, value User) error
	Delete(key string) error
}

```
User login register has the methods for logging in and registering a new user. 

The logging in method will check if the user exists in the database and if the password is correct.

```
func (s *userService) Login(e_mail, password string) error {
	user, err := s.db.Get(e_mail)
	if err != nil {
		return err
	}
	return bcrypt.CompareHashAndPassword(user.Password, []byte(password))
}
```

The register method will generate a new private key for the user and store it in the database. 

```
func (s *userService) Register(e_mail, password string) error {
	_, err := s.db.Get(e_mail)
	if err == nil {
		return errors.New("This user already exists, it is present in the database.")
	}
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(password), bcrypt.DefaultCost)
	if err != nil {
		return err
	}
	privKey, err := rsa.GenerateKey(rand.Reader, 1028)
	if err != nil {
		return err
	}
	user := User{
		Email:      e_mail,
		Password:   hashedPassword,
		PrivateKey: privKey,
	}
	return s.db.Set(e_mail, user)
}

```

The next part of the laboratory work was the methods for signing a message and verifying the signature by the SHA 256. 

The signMessage method will hash the message, encrypt it with the user private key and return the signature, where before signing, we need to hash our message (the hash is what we actually sign) In order to generate the signature, we provide a random number generator, our private key, the hashing algorithm that we used, and the hash sum of our message.

```
func (s *messageService) SignMessage(privateKey *rsa.PrivateKey, msg []byte) ([]byte, []byte, error) {
	msgHash := sha256.New()
	_, err := msgHash.Write(msg)
	if err != nil {
		return nil, nil, err
	}
	msgHashSum := msgHash.Sum(nil)
	signature, err := rsa.SignPSS(rand.Reader, privateKey, crypto.SHA256, msgHashSum, nil)
	if err != nil {
		return nil, nil, err
	}
	return signature, msgHashSum, nil
}

```

The verifyMessage method will decrypt the signature with the user public key, hash the message and compare the two hashes. To verify the signature, we provide the public key, the hashing algorithm, the hash sum of our message and the signature we generated previously.

```
func (s *messageService) VerifyMessage(publicKey *rsa.PublicKey, signature []byte, msgHashSum []byte) error {
	return rsa.VerifyPSS(publicKey, crypto.SHA256, msgHashSum, signature, nil)
}
```

Test:

```
    func TestLoginRegister(t *testing.T) {
	nsmd := NewStorageMemoryDatabase()
	nus := NewUserService(nsmd)

	const (
		e_mail   = "new.userfordatabase@mail.com"
		password = "917685"
	)

	err := nus.Register(e_mail, password)
	if err != nil {
		t.Fatalf("There was expected Register to return nil but received the return %v", err)
	}
	userLogin, err := nsmd.Get(e_mail)
	if err != nil {
		t.Fatalf("There was expected user to be registered but received the error %v", err)
	}
	if string(userLogin.Password) == password {
		t.Fatalf("There was expected password to be hashed but received the return %s", userLogin.Password)
	}
	err = nus.Login(e_mail, password)
	if err != nil {
		t.Fatalf("There was expected Login to return nil but received the return %v", err)
	}
}
```

```
func TestMessage(t *testing.T) {
	nsmd := NewStorageMemoryDatabase()
	nus := NewUserService(nsmd)
	ms := NewMessageService()

	const (
		e_mail   = "new.userfordatabase@mail.com"
		password = "917685"
	)

	err := nus.Register(e_mail, password)
	if err != nil {
		t.Fatalf("There was expected register to return NIL but received the return %v", err)
	}
	userLogin, err := nsmd.Get(e_mail)
	if err != nil {
		t.Fatalf("There was expected user to be registered but received the error %v", err)
	}
	message := []byte("I am the user.")
	signature, msgHashSum, err := ms.SignMessage(userLogin.PrivateKey, message)
	if err != nil {
		t.Fatalf("There was expected signMessage to return nil but received the return %v", err)
	}
	err = ms.VerifyMessage(&userLogin.PrivateKey.PublicKey, signature, msgHashSum)
	if err != nil {
		t.Fatalf("There was expected verifyMessage to return nil but received the return %v", err)
	}
}

```

## Conclusions 

SHA algorithm is being used in a lot of places, some of which are digital signature verification, password hashing, SSL handshake and integrity checks.

Digital signatures follow asymmetric encryption methodology to verify the authenticity of a document/file. Hash algorithms like SHA 256 go a long way in ensuring the verification of the signature.

Websites store user passwords in a hashed format for two benefits. It helps foster a sense of privacy, and it lessens the load on the central database since all the digests are of similar size.

The SSL handshake is a crucial segment of the web browsing sessions, and it’s done using SHA functions. It consists of your web browsers and the web servers agreeing on encryption keys and hashing authentication to prepare a secure connection.

Verifying file integrity has been using variants like SHA 256 algorithm and the MD5 algorithm. It helps maintain the full value functionality of files and makes sure they were not altered in transit.