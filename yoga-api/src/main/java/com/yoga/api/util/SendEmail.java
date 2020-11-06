package com.yoga.api.util;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.yoga.api.model.StatusMessageResponse;
import com.yoga.api.model.CourseRegistrationRequest;

public class SendEmail {

	// @Value("${course.registration.email.to}")
	// private String toEmailId;

	@Autowired
	public JavaMailSender javaMailSender;

	UtilMethods utilMethods = new UtilMethods();

	public StatusMessageResponse sendEmail(String fromEmailId, String toEmailId, Object emailBody,
			String messageBodyKey, String subJect, String successResponseMessage, String failureResponseMessage)
			throws MessagingException {

		SimpleMailMessage message = new SimpleMailMessage();

		try {
			message.setTo(toEmailId);
			message.setFrom(fromEmailId);
			message.setText(messageBodyKey + emailBody);
			message.setSubject(subJect);
			javaMailSender.send(message);
			return utilMethods.successResponse(successResponseMessage);
		} catch (MailException e) {

			return utilMethods.errorResponse(failureResponseMessage);

		}
	}

}
