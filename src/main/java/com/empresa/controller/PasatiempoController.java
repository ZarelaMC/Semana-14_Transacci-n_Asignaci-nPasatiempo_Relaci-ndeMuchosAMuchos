package com.empresa.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empresa.entidades.Pasatiempo;
import com.empresa.entidades.Usuario;
import com.empresa.entidades.UsuarioHasPasatiempo;
import com.empresa.entidades.UsuarioHasPasatiempoPK;
import com.empresa.service.PasatiempoService;
import com.empresa.service.UsuarioService;

@Controller // PASO 1
public class PasatiempoController {
	/*--------------------------- Semana 14 ------------------------------*/
	// PASO 2
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasatiempoService pasatiempoService;
	
	// PASO 3: mètodo para listar Pasatiempos	
	@ResponseBody()
	@RequestMapping("/listaPasatiempo")
	public List<Pasatiempo> listaPas(){
		return pasatiempoService.listaPasatiempo(); 
	}
	
	// PASO 4: mètodo para listar Usuarios
	@ResponseBody() //retorna JSON
	@RequestMapping("/listaUsuario") //ruta
	public List<Usuario> listaUs(){
		return usuarioService.listaUsuario() ;
	}
	
	// PASO 5: mètodo para listar Pasatiempos por Usuario
	@ResponseBody()
	@RequestMapping("/listaPasatiempoPorUsuario")
	public List<Pasatiempo> listaPasPorUsuario(int idUsuario){
		return usuarioService.traerPasatiempoDeUsuario(idUsuario); 
	}
	
	// PASO 6: mètodo para AGREGAR Pasatiempos al Usuario
	@ResponseBody()
	@PostMapping("/registraPasatiempo")
	public  HashMap<String, Object> registro(int idUsuario, int idPasatiempo){ //HashMap<?, ?> -- ? significa que retornarà cualquier tipo de dato
		HashMap<String, Object> maps = new HashMap<>();
		//------------> recibe todos los datos enviados como "data" desde el formulario del .jsp
		
		//CREA EL OBJETO
		UsuarioHasPasatiempoPK pk = new UsuarioHasPasatiempoPK();
		pk.setIdPasatiempo(idPasatiempo);
		pk.setIdUsuario(idUsuario);
		
		//VALIDAR PARA REGISTRAR
		UsuarioHasPasatiempo usp = new UsuarioHasPasatiempo();
		usp.setUsuarioHasPasatiempoPK(pk);
		
		if (usuarioService.buscaPasatiempo(pk).isPresent()) {
			maps.put("mensaje", "El pasatiempo ya existe");
		}else {
			UsuarioHasPasatiempo objUsp = usuarioService.insertaPasatiempo(usp);
			if (objUsp == null) {
				maps.put("mensaje", "Error en el registro");
			}else {
				maps.put("mensaje", "Registro existoso");
			}
		}
		List<Pasatiempo> lstSalida = usuarioService.traerPasatiempoDeUsuario(idUsuario);
		maps.put("lista", lstSalida);
		maps.put("usuario", idUsuario);
		return maps;
	}
	
	// PASO 7: mètodo para ELIMINAR Pasatiempos al Usuario
	@ResponseBody()
	@PostMapping("/eliminaPasatiempo")
	public HashMap<String, Object> elimina(int idUsuario, int idPasatiempo){
		HashMap<String, Object> maps = new HashMap<>();
		
		//CREA EL OBJETO
		UsuarioHasPasatiempoPK pk = new UsuarioHasPasatiempoPK();
		pk.setIdPasatiempo(idPasatiempo);
		pk.setIdUsuario(idUsuario);
		
		//VALIDAR PARA ELIMINAR
		UsuarioHasPasatiempo usp = new UsuarioHasPasatiempo();
		usp.setUsuarioHasPasatiempoPK(pk);
		//si existe lo elimina
		if (usuarioService.buscaPasatiempo(pk).isPresent()) {
			usuarioService.eliminaPasatiempo(usp); //ELIMINA REGISTRO
			maps.put("mensaje", "Se eliminó el pasatiempo");
		}else {
			maps.put("mensaje", "No existe pasatiempo");
		}
		
		List<Pasatiempo> lstSalida = usuarioService.traerPasatiempoDeUsuario(idUsuario);
		maps.put("lista", lstSalida);
		maps.put("usuario", idUsuario);
		return maps;
	}
}
