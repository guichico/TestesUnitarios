package com.br.guilherme.service;

import com.br.guilherme.entities.User;

public interface EmailService {

	public void notifyDelayedUsers(User user);

}