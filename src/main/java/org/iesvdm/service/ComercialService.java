package org.iesvdm.service;

import java.util.List;
import java.util.Optional;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.springframework.stereotype.Service;

@Service
public class ComercialService {
	
	private ComercialDAO comercialDAO;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ComercialService(ComercialDAO comercialDAO) {
		this.comercialDAO = comercialDAO;
	}
	
	public List<Comercial> listAll() {
		
		return comercialDAO.getAll();
		
	}
	

	public Comercial one(Integer id) {
		Optional<Comercial> optCom = comercialDAO.find(id);
		if (optCom.isPresent())
			
			//request.setAttribute("listaDepartamentos", depDao.getAll());
			
			return optCom.get();
		else 
			return null;
	}
	
	public void newComercial(Comercial Comercial) {
		
		comercialDAO.create(Comercial);
		
	}
	
	public void replaceComercial(Comercial Comercial) {
		
		comercialDAO.update(Comercial);
		
	}
	
	public void deleteComercial(int id) {
		
		comercialDAO.delete(id);
		
	}

}