package com.feisystems.bham.infrastructure;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.feisystems.bham.domain.VerificationToken;
import com.feisystems.bham.util.Constant;

@Service("mailSenderService")
public class MailSenderServiceImpl implements MailSenderService {

	private static Logger LOG = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	private final JavaMailSender mailSender;
	private final VelocityEngine velocityEngine;
	
	
	@Autowired
	private MessageSource messageSource;
	
	Locale locale = LocaleContextHolder.getLocale();

	@Autowired
	public MailSenderServiceImpl(JavaMailSender mailSender, VelocityEngine velocityEngine) {
		this.mailSender = mailSender;
		this.velocityEngine = velocityEngine;
	}
	
	public EmailServiceTokenModel sendEmail(final EmailServiceTokenModel model) {
		VerificationToken.Type tokenType = model.getTokenType();
		if(tokenType.equals(VerificationToken.Type.emailConfirmation)) {
			
			return sendVerificationEmail(model, messageSource.getMessage(Constant.VERIFICATION_SUBJECT, null, locale), "META-INF/velocity/VerifyEmail.vm");
			
		} else if (tokenType.equals(VerificationToken.Type.emailRegistration)) {
			
			return sendVerificationEmail(model, messageSource.getMessage(Constant.REGISTRATION_SUBJECT, null, locale), "META-INF/velocity/RegistrationEmail.vm");
			
		} else if (tokenType.equals(VerificationToken.Type.lostPassword)) {
			
			return sendVerificationEmail(model, messageSource.getMessage(Constant.LOST_PASSWORD_SUBJECT, null, locale), "META-INF/velocity/LostPasswordEmail.vm");
			
		} else if (tokenType.equals(VerificationToken.Type.lostPasswordAndSecurityQuestions)) {
			
			return sendVerificationEmail(model, messageSource.getMessage(Constant.LOST_PASSWORD_SUBJECT, null, locale), "META-INF/velocity/LostPasswordAndSecurityQuestionsEmail.vm");
			
		} else {
			
			return sendVerificationEmail(model, messageSource.getMessage(Constant.FEEDBACK_SUBJECT, null, locale), "META-INF/velocity/FeedbackEmail.vm");
		}
		
	}

	private EmailServiceTokenModel sendVerificationEmail(final EmailServiceTokenModel emailVerificationModel, final String emailSubject, final String velocityModel) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
				messageHelper.setTo(emailVerificationModel.getEmailAddress());
				messageHelper.setFrom(messageSource.getMessage(Constant.FROM_ADDRESS, null, locale));
				messageHelper.setReplyTo(messageSource.getMessage(Constant.REPLYTO_ADDRESS, null, locale));
				messageHelper.setSubject(emailSubject);
				Map model = new HashMap();
				model.put("model", emailVerificationModel);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityModel, model);
				messageHelper.setText(new String(text.getBytes(), "UTF-8"), true);
			}
		};
		LOG.debug("Sending {} token to : {}", emailVerificationModel.getTokenType().toString(), emailVerificationModel.getEmailAddress());

		try {
			this.mailSender.send(preparator);
		} catch (MailException e) {
			LOG.debug("Sending {} token to : {} failed. ", emailVerificationModel.getTokenType().toString(), emailVerificationModel.getEmailAddress());
			throw e;
		}

		return emailVerificationModel;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	

}
