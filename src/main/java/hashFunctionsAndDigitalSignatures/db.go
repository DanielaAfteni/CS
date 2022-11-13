package hashfunctionsanddigitalsignatures

import (
	"crypto/rsa"
	"errors"
)

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

type storageMemoryDatabase struct {
	data map[string]User
}

func NewStorageMemoryDatabase() Database {
	return &storageMemoryDatabase{data: make(map[string]User)}
}

func (s *storageMemoryDatabase) Get(key string) (User, error) {
	if value, ok := s.data[key]; ok {
		return value, nil
	}
	return User{}, errors.New("The key was not found.")
}

func (s *storageMemoryDatabase) Set(key string, value User) error {
	s.data[key] = value
	return nil
}

func (s *storageMemoryDatabase) Delete(key string) error {
	if _, ok := s.data[key]; ok {
		delete(s.data, key)
		return nil
	}
	return errors.New("The key was not found.")
}
