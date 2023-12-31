package com.empresa.service;

import java.util.List;
import java.util.Optional;

import com.empresa.entidades.Opcion;
import com.empresa.entidades.Pasatiempo;
import com.empresa.entidades.Rol;
import com.empresa.entidades.Usuario;
import com.empresa.entidades.UsuarioHasPasatiempo;
import com.empresa.entidades.UsuarioHasPasatiempoPK;

public interface UsuarioService {

	public abstract Usuario login(Usuario bean);
	public abstract List<Opcion> traerEnlacesDeUsuario(int idUsuario);
	public abstract List<Rol> traerRolesDeUsuario(int idUsuario);
	public abstract Usuario buscaPorLogin(String login);
	public abstract List<Usuario> listaUsuario();
	
	
	public abstract UsuarioHasPasatiempo insertaPasatiempo(UsuarioHasPasatiempo obj);
	public abstract void eliminaPasatiempo(UsuarioHasPasatiempo obj);
	public abstract Optional<UsuarioHasPasatiempo> buscaPasatiempo(UsuarioHasPasatiempoPK obj);
	public abstract List<Pasatiempo> traerPasatiempoDeUsuario(int idUsuario);

}




