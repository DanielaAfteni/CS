package authorAuthen

import (
	"github.com/DanielaAfteni/CS/src/main/java/webAuthenticationAuthorisation/src/main/java/webAuthenticationAuthorisation/token"
	"github.com/gin-gonic/gin"
)

// the process of authorization is implemented here
// it is done by checking if the user has the required role
// for the usage of the authorization in the authorAuthen, it will be required to add it to the route
func UserHasaSpecificRole(role string) gin.HandlerFunc {
	return func(context *gin.Context) {
		roleFromToken := context.GetString("role")
		if roleFromToken != role {
			context.Abort()
			return
		}
		context.Next()
	}
}

// the process of authentication is implemented here
// it is done by checking if the JSON Web Token is valid and extracting user e-mail and role and saves them in the request context
// for the usage of authentication in the authorAuthen, it will be required to add it to the route
func AuthenticationJwt() gin.HandlerFunc {
	return func(context *gin.Context) {
		email, role, err := token.ExtractEmailRole(context)
		if err != nil {
			context.Abort()
			return
		}
		context.Set("email", email)
		context.Set("role", role)
		context.Next()
	}
}
