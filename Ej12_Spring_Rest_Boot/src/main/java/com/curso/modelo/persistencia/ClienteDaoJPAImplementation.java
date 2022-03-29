package com.curso.modelo.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.curso.modelo.entidad.Cliente;

@Repository
public class ClienteDaoJPAImplementation implements ClienteDao{
	//JPA tiene una anotacion especifica de JEE para inyectar el EntityManager
	//Spring tambien lo usa
	//@Autowired
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public void insertar(Cliente obj) {
		em.persist(obj);
	}

	@Override
	public void modificar(Cliente obj) {
		em.merge(obj);
	}

	@Override
	public void borrar(Cliente obj) {
		em.remove(em.find(Cliente.class, obj.getId()));
	}

	@Override
	public Cliente buscar(Integer id) {
		return em.find(Cliente.class, id);
	}

	@Override
	public List<Cliente> listar() {
		TypedQuery<Cliente> q = em.createQuery("select c from Cliente c",Cliente.class);
		return q.getResultList();
	}

}
