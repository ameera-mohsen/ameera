package com.dxc.automation.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	//@Bean
	public boolean buildSimpleEmail(String to, String cc, String subject, String body) {
		System.err.println("0------------------- ");

		boolean sentFlag;
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(to);
			msg.setCc(cc);
			msg.setSubject(subject);
			msg.setText(body);
			mailSender.send(msg);
			sentFlag = true;
		} catch (MailException e) {
			sentFlag = false;
		}
		return sentFlag;
	}

	public boolean buildEmailWithAttachment(String to, String cc, String subject, String body, String filePath) {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper msgHelper;
		boolean sentFlag;
		System.err.println("0------------------- ");

		try {
			msgHelper = new MimeMessageHelper(msg, true);
			msgHelper.setTo(to);
			msgHelper.setCc(cc);
			msgHelper.setSubject(subject);
			msgHelper.setText(body);
			FileSystemResource file = new FileSystemResource(new File(filePath));
			msgHelper.addAttachment("Attachments", file);
			mailSender.send(msg);
			sentFlag = true;
		} catch (MessagingException e) {
			sentFlag = false;
		} catch (MailException e) {
			sentFlag = false;
		}
		return sentFlag;
	}

	@Override
	public void sendEmail(String to, String cc, String subject, String body) {
		System.err.println("0------------------- ");

		this.buildSimpleEmail(to, cc, subject, body);
	}
	
	@Override
	public void sendEmailToList(String[] toList, String cc, String subject, String body) {
		System.err.println("New operation to sent mail to List");
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper msgHelper;
		boolean sentFlag;
		try {
			msgHelper = new MimeMessageHelper(msg, true);
			msgHelper.setTo(toList);
			msgHelper.setCc(cc);
			msgHelper.setSubject(subject);
			msgHelper.setText(body);
			mailSender.send(msg);
			sentFlag = true;
		} catch (MessagingException e) {
			sentFlag = false;
		} catch (MailException e) {
			sentFlag = false;
		}
		System.err.println("-------sentFlag ------------ " + sentFlag);
	}

	@Override
	public void sendEmailWithAttachment(String to, String cc, String subject, String body, String filePath) {
		System.err.println("0------------------- ");

		this.buildEmailWithAttachment(to, cc, subject, body, filePath);

	}

	@Override
	public boolean sendEmailAcknowledge(String to, String cc, String subject, String body) {
		System.err.println("0------------------- ");

		boolean sentSuccessfully;
		if (this.buildSimpleEmail(to, cc, subject, body)) {
			sentSuccessfully = true;
		} else {
			sentSuccessfully = false;
		}
		return sentSuccessfully;
	}

	@Override
	public boolean sendEmailWithAttachmentAcknowledge(String to, String cc, String subject, String body,
			String filePath) {
		System.err.println("0------------------- ");

		// TODO Auto-generated method stub
		boolean sentSuccessfully;
		if (this.buildEmailWithAttachment(to, cc, subject, body, filePath)) {
			sentSuccessfully = true;
		} else {
			sentSuccessfully = false;
		}

		return sentSuccessfully;
	}

}
