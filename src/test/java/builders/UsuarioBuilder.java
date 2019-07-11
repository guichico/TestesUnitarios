package builders;

import com.br.guilherme.entities.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder newUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Guilherme");
		
		return builder;
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
