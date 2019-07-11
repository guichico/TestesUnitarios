package com.br.guilherme.service;

import com.br.guilherme.entities.Usuario;

public interface EmailService {

	public void notificarUsuarioComAtraso(Usuario usuario);

}