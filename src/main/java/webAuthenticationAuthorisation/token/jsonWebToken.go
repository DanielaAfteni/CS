package token

import (
	"fmt"
	"os"
	"strings"
	"time"

	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
)

// function helping inthe process of extracting user e-mail and role
func extract(context *gin.Context) string {
	token := context.Query("token")
	if token != "" {
		return token
	}
	bearerToken := context.Request.Header.Get("Authorization")
	if len(strings.Split(bearerToken, " ")) == 2 {
		return strings.Split(bearerToken, " ")[1]
	}
	return ""
}

// function for generating the JSON Web Token
func Generate(email, role string) (string, error) {
	mapClai := jwt.MapClaims{}
	mapClai["authorized"] = true
	mapClai["email"] = email
	mapClai["role"] = role
	mapClai["exp"] = time.Now().Add(12 * time.Hour).Unix()
	tok := jwt.NewWithClaims(jwt.SigningMethodHS256, mapClai)
	token, err := tok.SignedString([]byte(os.Getenv("SECRET")))
	if err != nil {
		return "", err
	}
	return token, nil
}

// checking if the JSON Web Token is valid and extracting user e-mail and role and saves them in the request context
func ExtractEmailRole(context *gin.Context) (string, string, error) {
	token, err := jwt.Parse(extract(context), func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("an error appeared at the signing method %v", token.Header["alg"])
		}
		return []byte(os.Getenv("SECRET")), nil
	})
	if err != nil {
		return "", "", err
	}
	claims, ok := token.Claims.(jwt.MapClaims)
	if ok && token.Valid {
		return claims["email"].(string), claims["role"].(string), nil
	}
	return "", "", nil
}
