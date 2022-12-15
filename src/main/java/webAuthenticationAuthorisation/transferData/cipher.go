package transferData

//here are represented the structures of ciphers
type DataEncryptionStandardDES struct {
	Key  uint64 `json:"key"`
	Text uint64 `json:"text"`
}

type VigenereCipher struct {
	Key      string `json:"key"`
	Text     string `json:"text"`
	Alphabet string `json:"alphabet"`
}

type RivestCipherRC4 struct {
	Key  []byte `json:"key"`
	Text string `json:"text"`
}

type PlayfairCipher struct {
	Key  string `json:"key"`
	Text string `json:"text"`
}

type CaesarPermutationCipher struct {
	Shift    int    `json:"shift"`
	Text     string `json:"text"`
	Alphabet string `json:"alphabet"`
}

type RivestShamirAdlemanRSAcipher struct {
	Key  uint64   `json:"key"`
	Text []uint64 `json:"text"`
}

// and the creation of the user, having its e-mail, password and One Time Password
type CreateUser struct {
	Email           string `json:"email" binding:"required,email"`
	Password        string `json:"password" binding:"required"`
	OneTimePassword string `json:"oneTimePassword" binding:"required"`
}
