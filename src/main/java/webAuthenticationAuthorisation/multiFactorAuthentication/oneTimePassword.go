package multiFactorAuthentication

import (
	"errors"
	"math/rand"
	"strconv"
)

// there is represented the interface for the generation and verification of the One Time Password, having an e-mail
type IOneTimePasswordService interface {
	Generate(email string) (string, error)
	Verify(email string, oneTimePassword string) error
}

// structure which determines the One Time Password  service
type OneTimePasswordService struct {
	oneTimePasswords map[string]string
}

// function which returns One Time Password service, by making a map of them
func NewOneTimePasswordService() IOneTimePasswordService {
	return &OneTimePasswordService{
		oneTimePasswords: make(map[string]string),
	}
}

// method to generate the One Time Password, having given an e-mail
// corresponding to a specific e-mail we will set a One Time Password
func (oneTimePasswordService *OneTimePasswordService) Generate(email string) (string, error) {
	oneTimePassword := strconv.Itoa(200000 + rand.Intn(900000))
	oneTimePasswordService.oneTimePasswords[email] = oneTimePassword
	return oneTimePassword, nil
}

// method to verify if the specific e-mail has a One Time Password
// if it is present, then we delete it, otherwise the sent error message
func (oneTimePasswordService *OneTimePasswordService) Verify(email string, oneTimePassword string) error {
	if oneTimePasswordService.oneTimePasswords[email] != oneTimePassword {
		return errors.New("the one time password is not valid at all")
	}
	delete(oneTimePasswordService.oneTimePasswords, email)
	return nil
}
