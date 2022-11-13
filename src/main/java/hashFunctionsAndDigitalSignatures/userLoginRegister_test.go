package hashfunctionsanddigitalsignatures

import (
	"testing"
)

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
