package hashfunctionsanddigitalsignatures

import (
	"crypto/rand"
	"crypto/rsa"
	"errors"

	"golang.org/x/crypto/bcrypt"
)

type UserService interface {
	Register(e_mail, password string) error
	Login(e_mail, password string) error
}

type userService struct {
	db Database
}

func NewUserService(database Database) UserService {
	return &userService{db: database}
}

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

func (s *userService) Login(e_mail, password string) error {
	user, err := s.db.Get(e_mail)
	if err != nil {
		return err
	}
	return bcrypt.CompareHashAndPassword(user.Password, []byte(password))
}
