package model;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	private String recipientName;
	private String recipientUsername;
	private String recipientPassword;
    private String sender = Credentials.email;
    private String password = Credentials.password;
    private String recipientEmail;
    private String verificationCode;

    private Session session = null;
    private MimeMessage message = null;
    private Transport transport = null;

    public Email(String recipientName, String recipient) {
    	this.recipientName = recipientName;
    	this.recipientEmail = recipient;
    }

	public void setRecipientUsername(String recipientUsername) {
		this.recipientUsername = recipientUsername;
	}

	public void setRecipientPassword(String recipientPassword) {
		this.recipientPassword = recipientPassword;
	}

	//Generate and set the verification code
    public String generateVerificationCode() {
    	Random random = new Random();
        int code = random.nextInt(999999);
        verificationCode = String.format("%06d", code);
        return verificationCode;
    }

    //Send email for registration
    public void sendEmailForRegistration() throws MessagingException {
		//Set properties
		Properties properties = new Properties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		session = Session.getInstance(properties);

		//Set message
		message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		message.setSubject("Email Verification");
		message.setText("Dear " + recipientName +", please verify your email using this code: " + verificationCode + "\nYour code will expire in 2 minutes");

		//Connect to mail server
		transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", sender, password);

		//Send message
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
    }

    public void sendEmailForForget() throws MessagingException {
		//Set properties
		Properties properties = new Properties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		session = Session.getInstance(properties);

		//Set message
		message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		message.setSubject("Email Verification");
		message.setText("Dear " + recipientName +", you have requested for the following information:\n\nUsername: " + recipientUsername + "\nPassword: " + recipientPassword);

		//Connect to mail server
		transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", sender, password);

		//Send message
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
    }

    //Send email for change password
    public void sendEmailForChangePassword() throws MessagingException {
		//Set properties
		Properties properties = new Properties();
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		session = Session.getInstance(properties);

		//Set message
		message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		message.setSubject("Email Verification");
		message.setText("Dear " + recipientName + ", <a href='http://localhost:8089/BankSystem/ChangePassword.html'>click here</a> to change your password", "UTF-8", "html");

		//Connect to mail server
		transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", sender, password);

		//Send message
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
    }
}