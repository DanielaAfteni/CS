package java

import (
	"fmt"

	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/authorAuthen"
	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/caesarPermutationCipher12"
	multiFactorAuthentication "github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/multiFactorAuthentication"
	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/playfairCipher12"
	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/transferData"
	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/vigenereCipher1"
	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
)

func ListenAndServe() error {
	godotenv.Load()
	newUserService := NewUserService()
	oneTimePassword := multiFactorAuthentication.NewOneTimePasswordService()
	newMailService := multiFactorAuthentication.NewMailService()
	routerGroup := gin.Default().Group("/webAuthenticationAuthorisation")
	// process of getting all users
	routerGroup.GET("/users", func(context *gin.Context) {
		users, err := newUserService.GetAll()
		if err != nil {
			context.JSON(500, gin.H{"error": err.Error()})
			return
		}
		context.JSON(200, users)
	})
	// process of getting the e-mail
	routerGroup.GET("/users/:email", func(context *gin.Context) {
		email := context.Param("email")
		user, err := newUserService.Get(email)
		if err != nil {
			context.JSON(404, gin.H{"error": err.Error()})
			return
		}
		context.JSON(200, user)
	})
	// process of getting the e-mail
	routerGroup.POST("/oneTimePassword/:email", func(context *gin.Context) {
		email := context.Param("email")
		pass, err := oneTimePassword.Generate(email)
		if err != nil {
			context.JSON(500, gin.H{"error": err.Error()})
			return
		}
		mail := multiFactorAuthentication.Mail{
			To:      []string{email},
			Subject: "Code validation",
			Body:    fmt.Sprintf("the validation code is %s", pass),
		}
		go newMailService.Send(mail)
		context.JSON(200, gin.H{"message": "validation code was sent to the corresponding email"})
	})
	// process of creation of a user by registration
	routerGroup.POST("/users/register", func(context *gin.Context) {
		var transferData transferData.CreateUser
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		if err := oneTimePassword.Verify(transferData.Email, transferData.OneTimePassword); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		if err := newUserService.Register(transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		context.JSON(201, nil)
	})
	// process of creation of a user by login
	routerGroup.POST("/users/login", func(context *gin.Context) {
		var transferData transferData.CreateUser
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		if err := oneTimePassword.Verify(transferData.Email, transferData.OneTimePassword); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		jwt, err := newUserService.Login(transferData)
		if err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		context.JSON(200, gin.H{"token": jwt})
	})
	// Authentication
	authen := routerGroup.Use(authorAuthen.AuthenticationJwt())
	// after authentication - what can a user do: encypt the caesar permutation Cipher
	authen.POST("/caesarPermutationCipher12/encrypt", func(context *gin.Context) {
		var transferData transferData.CaesarPermutationCipher
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		cypher := caesarPermutationCipher12.Encrypt(transferData.Alphabet, transferData.Shift, transferData.Text)
		transferData.Text = cypher
		context.JSON(200, transferData)
	})
	// after authentication - what can a user do: decrypt the caesar permutation Cipher
	authen.POST("/caesarPermutationCipher12/decrypt", func(context *gin.Context) {
		var transferData transferData.CaesarPermutationCipher
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		plainText := caesarPermutationCipher12.Decrypt(transferData.Alphabet, transferData.Shift, transferData.Text)
		transferData.Text = plainText
		context.JSON(200, transferData)
	})
	// Authorization
	author := authen.Use(authorAuthen.UserHasaSpecificRole(RoleAdmin))
	// after Authorization - what can a user do: encypt the vigenere Cipher
	author.POST("/vigenereCipher1/encrypt", func(context *gin.Context) {
		var transferData transferData.VigenereCipher
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		cypherText := vigenereCipher1.Encrypt(transferData.Alphabet, transferData.Key, transferData.Text)
		transferData.Text = cypherText
		context.JSON(200, transferData)
	})
	// after Authorization - what can a user do: decrypt the vigenere Cipher
	author.POST("/vigenereCipher1/decrypt", func(context *gin.Context) {
		var transferData transferData.VigenereCipher
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		plainText := vigenereCipher1.Decrypt(transferData.Alphabet, transferData.Key, transferData.Text)
		transferData.Text = plainText
		context.JSON(200, transferData)
	})
	// after Authorization - what can a user do: encypt the playfair Cipher
	author.POST("/playfairCipher12/encrypt", func(context *gin.Context) {
		var transferData transferData.PlayfairCipher
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		cypherText := playfairCipher12.Encrypt(transferData.Key, transferData.Text)
		transferData.Text = cypherText
		context.JSON(200, transferData)
	})
	// after Authorization - what can a user do: decrypt the playfair Cipher
	author.POST("/playfairCipher12/decrypt", func(context *gin.Context) {
		var transferData transferData.PlayfairCipher
		if err := context.ShouldBindJSON(&transferData); err != nil {
			context.JSON(400, gin.H{"appeared error": err.Error()})
			return
		}
		plainText := playfairCipher12.Decrypt(transferData.Key, transferData.Text)
		transferData.Text = plainText
		context.JSON(200, transferData)
	})

	return gin.Default().Run(":8080")
}
