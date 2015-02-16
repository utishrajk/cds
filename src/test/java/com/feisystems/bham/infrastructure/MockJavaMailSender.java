package com.feisystems.bham.infrastructure;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MockJavaMailSender implements JavaMailSender {

	List<MimeMessage> messages = new ArrayList<MimeMessage>();

	private static Logger log = LoggerFactory.getLogger(MockJavaMailSender.class);

	public MimeMessage createMimeMessage() {
		MimeMessage message = new MimeMessage(Session.getInstance(new Properties()));
		return message;
	}

	public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
		return null;
	}

	public void send(MimeMessage mimeMessage) throws MailException {
		log.debug("Mocked sending an email.");
	}

	public void send(MimeMessage[] mimeMessages) throws MailException {
		log.debug("Mocked sending an email.");
	}

	public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
		try {
			MimeMessage mimeMessage = createMimeMessage();
			mimeMessagePreparator.prepare(mimeMessage);
			messages.add(mimeMessage);
		} catch (Exception e) {
			System.out.println("Exception while preparing Mail Message" + e);
			throw new RuntimeException(e);
		}
		log.debug("Mocked sending an email.");
	}

	public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
		log.debug("Mocked sending an email.");
	}

	public void send(SimpleMailMessage simpleMessage) throws MailException {
		log.debug("Mocked sending an email.");
	}

	public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		log.debug("Mocked sending an email.");
	}

	public List<MimeMessage> getMessages() {
		return messages;
	}
}
