package application.login_register.helper;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

public class MailHelper {

	public static boolean send(String from, String to, String subject, String content) {
		try {
			
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				@Override 
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("nguyenthanhcongvt123@gmail.com", "xlkx kvhe tqvb tdjh");
				}
			});

			Message message = new MimeMessage(session);
			message.setSubject(subject);
			message.setContent(content, "text/html");
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));

			Transport.send(message);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	

}

