package com.yubi.application.user;

import com.yubi.application.login.PasswordResetRequest;

public interface UserService {
	
	public void createPasswordReset(String email, String userName);
	
	public void applyPasswordReset(String token);
	
	public PasswordResetRequest loadValidRequest(String token);
	
	public String changePassword(PasswordChange change);
	
	public String getUserNameFromResetToken(String token);

}
