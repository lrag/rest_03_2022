package com.curso.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.curso.modelo.entidad.Cliente;
import com.curso.modelo.negocio.GestorClientes;
import com.curso.rest.dto.ClienteDto;
import com.curso.rest.dto.Datos;
import com.curso.rest.dto.Error;
import com.curso.rest.dto.Respuesta;

@RestController
//@RequestMapping("clientes")
public class ClientesRest {

	@Autowired
	private GestorClientes gestorClientes;

	//GET /clientes
	@GetMapping(path="/clientes", produces=MimeTypeUtils.APPLICATION_JSON_VALUE)
	public Respuesta listar() {
		List<Cliente> clientes = gestorClientes.listar();
		List<ClienteDto> clientesDto = clientes
			.stream()
			.map( p -> new ClienteDto(p))
			.collect(Collectors.toList());
		return new Respuesta("200",null, new Datos("clientes", clientesDto));		
	}
	
	@GetMapping(path="/clientes/{id}",
				produces=MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta> buscar(@PathVariable("id") Integer id) {
		Cliente cliente = gestorClientes.buscar(id);
		Respuesta respuesta = new Respuesta();
		if(cliente == null) {
			respuesta.setStatus("404");
			respuesta.setError(new Error("404","No existe el cliente"));
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}
		respuesta.setStatus("200");
		respuesta.setDatos(new Datos("cliente", new ClienteDto(cliente)));
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}
	
	//POST CLIENTES
	@PostMapping(path = "/clientes",
			     consumes=MimeTypeUtils.APPLICATION_JSON_VALUE,
			     produces=MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta> insertar(@Valid() @RequestBody() ClienteDto clienteDto) {
		//ignoramos cualquier id que pretendan colarnos 
		//clienteDto.setId(null);
		Cliente cliente = clienteDto.asCliente();
		gestorClientes.insertar(cliente);
		Respuesta respuesta = new Respuesta();
		respuesta.setStatus("201");
		respuesta.setDatos(new Datos("cliente", new ClienteDto(cliente)));
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

	@PutMapping(path="/clientes/{id}",
			    consumes=MimeTypeUtils.APPLICATION_JSON_VALUE,
			    produces=MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta> modificar(@PathVariable("id") Integer idCliente,
										       @Valid() @RequestBody() ClienteDto clienteDto) {
		Cliente cliente = clienteDto.asCliente();
		//Nos fiamos solo del id que viene en la url, no en el cliente del body
		cliente.setId(idCliente);
		gestorClientes.modificar(cliente);
		Respuesta respuesta = new Respuesta();
		respuesta.setStatus("200");
		respuesta.setDatos(new Datos("cliente", new ClienteDto(cliente)));
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

	@DeleteMapping(path="/clientes/{id}",
			       produces=MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<Respuesta> borrar(@PathVariable("id") Integer idCliente){
		//
		//Aqui falta comprobar el 404
		//
		gestorClientes.borrar(new Cliente(idCliente));
		Respuesta respuesta = new Respuesta();
		respuesta.setStatus("200");
		respuesta.setDatos(new Datos("mensaje","El cliente se ha eliminado"));
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

}
