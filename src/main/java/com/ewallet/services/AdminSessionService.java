package com.ewallet.services;

import com.ewallet.entities.Admin;
import com.ewallet.entities.CurrentAdminSession;
import com.ewallet.exceptions.AdminException;
import com.ewallet.exceptions.AdminSessionException;

public interface AdminSessionService {

	public CurrentAdminSession getCurrentAdminSession(String key) throws AdminSessionException;

	public Admin getAdminDetails(String key) throws AdminException, AdminSessionException;

	public Integer getAdminId(String key) throws AdminSessionException;

}
