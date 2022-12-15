package multiFactorAuthentication

import (
	"fmt"
	"net/smtp"
	"os"
)

// the structure of the e-mail
type Mail struct {
	To      []string
	Subject string
	Body    string
}

// the structure of the e-mail service
type MailService struct {
	from    string
	address string
	auth    smtp.Auth
}

// the interface for the method of sending e-mails
type IMailService interface {
	Send(mail Mail)
}

// method for the establishment of a new e-mail service, which will be defined by some fields
// those being by the main keys: e-mail, password, host and post
// By these elements, it will be possible to return new e-mail service, which has e-mail, adress, and authentication
func NewMailService() IMailService {
	mailService := &MailService{
		from:    os.Getenv("EMAIL"),
		address: os.Getenv("HOST") + ":" + os.Getenv("PORT"),
		auth:    smtp.PlainAuth("", os.Getenv("EMAIL"), os.Getenv("PASSWORD"), os.Getenv("HOST")),
	}
	return mailService
}

// function for the creation of the e-mail, consisting of sections like: from, to, subject and body
func (mailSe *MailService) buildMail(mail Mail) []byte {
	message := "MIME-version: 1.0;\nContent-Type: text/html; charset=\"UTF-8\";\r\n"
	message += fmt.Sprintf("From: %s\r\n", senderName)
	message += fmt.Sprintf("To: %s\r\n", mail.To)
	message += fmt.Sprintf("Subject: %s\r\n", mail.Subject)
	message += fmt.Sprintf("\r\n%s\r\n", mail.Body)
	return []byte(message)
}

// method for the process of sending the e-mail which was already created
func (mailS *MailService) Send(mail Mail) {
	smtp.SendMail(mailS.address, mailS.auth, mailS.from, mail.To, mailS.buildMail(mail))
}

const (
	senderName = "Sender"
)
