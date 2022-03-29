package com.curso.modelo.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.curso.modelo.entidad.DetallePedido;
import com.curso.modelo.persistencia.DetallePedidoDao;

@Service
@Transactional(propagation=Propagation.REQUIRED) 
public class GestorDetallesPedido {

	@Autowired private DetallePedidoDao detallePedidoDao;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public DetallePedido buscar(Integer id){
		return detallePedidoDao.buscar(id);
	}
	
}










