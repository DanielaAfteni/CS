package java

import (
	"errors"

	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/token"

	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/transferData"
	"golang.org/x/crypto/bcrypt"
)

// the structure of each user, containing the most important elements, those being: e-mail, password and its role
type User struct {
	Email    string `json:"email"`
	Password []byte `json:"_"`
	Role     string `json:"role"`
}

// the interface for the user service, having some processes:
// finding a user
// getting all the users
// registration of a user
// loging in of a user
type UserService interface {
	Get(email string) (User, error)
	GetAll() ([]User, error)
	Register(transferData transferData.CreateUser) error
	Login(transferData transferData.CreateUser) (string, error)
}

// the structure of the user service will contain a multiple users
type userService struct {
	users map[string]User
}

// the main roles of the current user
// there are 2 opptions: being a simple user or being an admin
const (
	RoleUser  = "user"
	RoleAdmin = "admin"
)

// function for the new user services
func NewUserService() UserService {
	users := seedUsers()
	return &userService{users: users}
}

// method for the process of seeding the users
func seedUsers() map[string]User {
	users := make(map[string]User)
	hashedPassword, _ := bcrypt.GenerateFromPassword([]byte("thenewpassword"), bcrypt.DefaultCost)
	users["newuseradmin@mail.com"] = User{Email: "newuseradmin@mail.com", Password: hashedPassword, Role: RoleAdmin}
	return users
}

// method for the process of registration of the user
func (s *userService) Register(transferData transferData.CreateUser) error {
	if _, ok := s.users[transferData.Email]; ok {
		return errors.New("sorry but this user already exists")
	}
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(transferData.Password), bcrypt.DefaultCost)
	if err != nil {
		return err
	}
	s.users[transferData.Email] = User{Email: transferData.Email, Password: hashedPassword, Role: RoleUser}
	return nil
}

// method for the process of loging in of the user
func (s *userService) Login(transferData transferData.CreateUser) (string, error) {
	user, err := s.Get(transferData.Email)
	if err != nil {
		return "something is wrong, try again", err
	}
	err = bcrypt.CompareHashAndPassword(user.Password, []byte(transferData.Password))
	if err != nil {
		return "something is wrong, try again", err
	}
	jwt, err := token.Generate(user.Email, user.Role)
	if err != nil {
		return "something is wrong, try again", err
	}
	return jwt, nil
}

// function for the process of getting a user
func (s *userService) Get(email string) (User, error) {
	if value, ok := s.users[email]; ok {
		return value, nil
	}
	return User{}, errors.New("user was not found, maybe try again")
}

// function for the process of getting the users
func (s *userService) GetAll() ([]User, error) {
	var users []User
	for _, value := range s.users {
		users = append(users, value)
	}
	return users, nil
}
