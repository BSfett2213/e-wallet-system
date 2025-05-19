package com.ewallet.services;

import com.ewallet.entities.Admin;
import com.ewallet.entities.CurrentAdminSession;
import com.ewallet.entities.User;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.LoginException;
import com.ewallet.exceptions.LogoutException;
import com.ewallet.exceptions.UserException;

public interface AdminLoginService {

	public CurrentAdminSession loginAdmin(User user) throws LoginException, AdminException;

	public String logoutAdmin(String key) throws LogoutException;

	public User authenticateAdmin(User user, String key) throws UserException, AdminException, LoginException;

	public Admin validateAdmin(String key) throws AdminException, LoginException;

}
