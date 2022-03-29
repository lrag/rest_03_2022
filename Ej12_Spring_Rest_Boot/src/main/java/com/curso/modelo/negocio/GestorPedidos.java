package com.curso.modelo.negocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.curso.modelo.entidad.DetallePedido;
import com.curso.modelo.entidad.Pedido;
import com.curso.modelo.persistencia.PedidoDao;

@Service
@Transactional(propagation=Propagation.REQUIRED) 
public class GestorPedidos {

	@Autowired private PedidoDao pedidoDao;
	@Autowired private GestorBancos gestorBancos;
	@Autowired private GestorAlmacen gestorAlmacen;
	@Autowired private GestorTransportes gestorTransportes;
	@Autowired private GestorOfertas gestorOfertas;
	
	public void insertar(Pedido pedido){
		pedidoDao.insertar(pedido);
	}

	
	public void modificar(Pedido pedido){
		pedidoDao.modificar(pedido);
	}
	
	public void borrar(Integer idPedido){
		Pedido pedido = pedidoDao.buscar(idPedido);
		//if pedido==null...
		pedidoDao.borrar(pedido);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor= { Exception.class } )
	public void aceptar(Pedido pedido) throws Exception{
	
		pedido = pedidoDao.buscar(pedido.getId());
	
		Integer numeroTC = pedido.getCliente().getNumeroTC();
		gestorBancos.comprobarTC(numeroTC);
		
		for(DetallePedido dp : pedido.getDetalles()) {
			gestorAlmacen.comprobarExistencias(dp.getProducto(), dp.getCantidad());
			gestorAlmacen.reducirExistencias(dp.getProducto(), dp.getCantidad());
		}
		
		String camion = gestorTransportes.obtenerCamion(true);
		//pedido.setCamion(camion);
		
		
		try {
			String perritoPiloto = gestorOfertas.obtenerPerritoPiloto(false);
		} catch (Exception e) {
			System.out.println("GESTOR_PEDIDOS: "+e.getMessage());
			//Procesar el drama de que no haya perrito pilotos
		}
		
		//EMITIR UNA FACTURA
		//gestorFacturas.bla bla blá...
		
		pedido.setEstado("ACEPTADO");
		pedidoDao.modificar(pedido);
	
		System.out.println(TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
		
		System.out.println("FIN");
		
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public Pedido buscar(Integer id){
		return pedidoDao.buscar(id);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public Pedido buscarConDetalles(Integer id){
		return pedidoDao.buscarConDetalles(id);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Pedido> listar(){
		return pedidoDao.listar();
	}
	
	////////////
	//DETALLES//
	////////////
	public void addDetalle(Pedido pedido, DetallePedido detalle) {
		
		//Si invocamos 'buscarConDetalles' en vez de dos selects se hace uno
		pedido = pedidoDao.buscarConDetalles(pedido.getId());
		//Modelo an�mico:
		//pedido.getDetalles().add(e)
		//Domain Driven Design:
		pedido.addDetalle(detalle);
		pedidoDao.modificar(pedido);
		
	}
	
	
}










