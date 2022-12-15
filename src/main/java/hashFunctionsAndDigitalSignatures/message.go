package hashfunctionsanddigitalsignatures

import (
	"crypto"
	"crypto/rand"
	"crypto/rsa"
	"crypto/sha256"
)

type MessageService interface {
	SignMessage(privateKey *rsa.PrivateKey, msg []byte) ([]byte, []byte, error)
	VerifyMessage(pubKey *rsa.PublicKey, message, signature []byte) error
}

type messageService struct{}

func NewMessageService() MessageService {
	return &messageService{}
}

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

func (s *messageService) VerifyMessage(publicKey *rsa.PublicKey, signature []byte, msgHashSum []byte) error {
	return rsa.VerifyPSS(publicKey, crypto.SHA256, msgHashSum, signature, nil)
}
