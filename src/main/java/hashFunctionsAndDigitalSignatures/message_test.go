package hashfunctionsanddigitalsignatures

import (
	"testing"
)

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
