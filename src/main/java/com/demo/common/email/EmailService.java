package com.demo.common.email;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.demo.common.dto.EmailDto;

import lombok.extern.slf4j.Slf4j;


//@Singleton
//@Component

public class EmailService {

	/** The host name. */
	private String hostName;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The email from. */
	private String emailFrom;

	/** The sender name. */
	private String senderName;

	/** The smtp port. */
	private int smtpPort;

	/** The is ssl on. */
	private boolean isSslOn;

	private String EmailFrom;

	private String Password;

	private String UserName;

	/** The environment. */
	@Inject
	private Environment environment;
	@Inject
	ThreadPoolTaskExecutor threadPool;

	/**
	 * Instantiates a new email service.
	 *
	 * @return
	 */
	@PostConstruct
	public void intit() {
		hostName = environment.getRequiredProperty("email.host");
		username = environment.getRequiredProperty("email.username");
		password = "password" ;   //SecuirtyUtils.decrypt(environment.getRequiredProperty("email.userpassword"));
		emailFrom = environment.getRequiredProperty("email.from");
		senderName = environment.getRequiredProperty("email.sender.name");
		smtpPort = Integer.parseInt(environment.getRequiredProperty("email.port"));
		isSslOn = Boolean.parseBoolean(environment.getRequiredProperty("email.sslenable"));

		UserName = environment.getRequiredProperty("Email.username");
		EmailFrom = environment.getRequiredProperty("Email.from");
		Password = environment.getRequiredProperty("Email.userpassword");
	}

	
	public void sendEmail(final EmailDto emailDto) {
		threadPool.execute(() -> {
			final HtmlEmail email = new HtmlEmail();
			email.setHostName(hostName);
			email.setSmtpPort(smtpPort);
			email.setAuthenticator(new DefaultAuthenticator(username, password));

			email.setSSLOnConnect(isSslOn);
			try {
				email.addTo(emailDto.getEmailTo(), emailDto.getName());
				email.setFrom(emailFrom, senderName);
				email.setSubject(emailDto.getEmailSubject());
				email.setHtmlMsg(emailDto.getEmailMessage());
				if (emailDto.getFile() != null) {
					final EmailAttachment attachment = new EmailAttachment();
					//attachment.setPath(emailDto.getFile().getAbsolutePath());
					attachment.setDisposition(EmailAttachment.ATTACHMENT);
					email.attach(attachment);
				}
				// email.setTextMsg("Your email client does not support HTML
				// messages");
				email.send();
				//log.info("Sending email to,{}" + emailDto.getEmailTo());
			} catch (final Exception e) {
				//log.error("Error occred during sending email to " + emailDto.getEmailTo() + " :" + e);
			}
		});
	}


	public void sendEmailList(List<String> adminEmails, String subject, String message, String firstName, final boolean isClientApi) {
		threadPool.execute(() -> {
			final HtmlEmail email = new HtmlEmail();
			email.setHostName(hostName);
			email.setSmtpPort(smtpPort);
			if(!isClientApi) {
				email.setAuthenticator(new DefaultAuthenticator(username, password));
			} else {
				email.setAuthenticator(new DefaultAuthenticator(UserName, Password));
			}

			email.setSSLOnConnect(isSslOn);
			String[] emailData = adminEmails.stream().toArray(String[]::new);
			try {
				if(!isClientApi) {
					email.setFrom(emailFrom, senderName);
				} else {
					email.setFrom(EmailFrom, senderName);
				}

				email.setSubject(subject);
				email.setHtmlMsg(message);
				email.addTo(emailData);
				email.send();
				//log.info("Sending email to,{}" + adminEmails);
			} catch (final Exception e) {
				//log.error("Error occured during sending email:" + e);
			}
		});
	}

}
