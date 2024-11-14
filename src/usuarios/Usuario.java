package usuarios;

public abstract class Usuario {
	
	protected String login;
	protected String password;
	
	public Usuario(String nLogin, String nPassword) {
		this.login = nLogin;
		this.password = nPassword;
	}	
}
