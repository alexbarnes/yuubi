package com.yubi.application.user;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.login.PasswordResetRequest;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private final JavaMailSender sender;
	private final SessionFactory sessionFactory;
	private final PasswordEncoder encoder;
	private final UserAccess userAccess;
	
	@Inject
	public UserServiceImpl(JavaMailSender sender,
			SessionFactory sessionFactory, PasswordEncoder encoder,
			UserAccess userAccess) {
		super();
		this.sender = sender;
		this.sessionFactory = sessionFactory;
		this.encoder = encoder;
		this.userAccess = userAccess;
	}

	
	@Transactional
	@SuppressWarnings("unchecked")
	public void createPasswordReset(String email, String userName) {
		// -- First invalidate any outstanding requests for that user
		Query query = sessionFactory.getCurrentSession().createQuery("from PasswordResetRequest where userName = :userName and used = false");
		query.setString("userName", userName);
		
		List<PasswordResetRequest> list = query.list();
		for (PasswordResetRequest reset : list) {
			reset.setUsed(true);
		}
		
		sessionFactory.getCurrentSession().flush();
		
		// -- Now create a new request
		PasswordResetRequest request = new PasswordResetRequest();
		request.setToken(RandomStringUtils.randomAlphanumeric(20));
		request.setIssued(new Date());
		request.setUsed(false);
		request.setUserName(userName);
		
		sessionFactory.getCurrentSession().save(request);
		
		// -- Now send an e-mail to the user with the token in.
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("Yuubi Password Reset Request");
		message.setText("Please visit https://www.yuubi-jewellery.com/admin/password/change/" + request.getToken() + " to reset your password.");
		message.setTo(email);
		message.setFrom("info@yuubi-jewellery.co.uk");
		sender.send(message);
		
		// -- Disable the users account
		User user = userAccess.loadByUserName(userName);
		user.setEnabled(false);
	}

	
	@Transactional
	public void applyPasswordReset(String token) {
		PasswordResetRequest request = (PasswordResetRequest) sessionFactory.getCurrentSession().get(PasswordResetRequest.class, token);
		request.setUsed(true);
	}

	@Transactional
	public String changePassword(PasswordChange password) {
		log.info("Updating password for user [" + password.getUsername() + "].");
		
		// -- Update the user
		User user = userAccess.loadByUserName(password.getUsername());
		String encodedPassword = encoder.encode(password.getPassword1());
		user.setPassword(encodedPassword);
		userAccess.save(user);
		user.setEnabled(true);
		
		return encodedPassword;
	}

	
	@Transactional
	public String getUserNameFromResetToken(String token) {
		PasswordResetRequest request = (PasswordResetRequest) sessionFactory.getCurrentSession().get(PasswordResetRequest.class, token);
		if(request == null) {
			return null;
		}
		
		return request.getUserName();
	}

	
	@SuppressWarnings("unchecked")
	@Transactional
	public PasswordResetRequest loadValidRequest(String token) {
		Query query = sessionFactory.getCurrentSession().createQuery("from PasswordResetRequest where token = :token and used = false");
		query.setString("token", token);
		List<PasswordResetRequest> list = query.list();
		
		if(list.isEmpty()) {
			return null;
		}
		
		// -- Should only be one
		return list.get(0);
	}
}
