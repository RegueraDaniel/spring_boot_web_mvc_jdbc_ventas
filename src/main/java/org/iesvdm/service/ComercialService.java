package org.iesvdm.service;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.dto.ComercialDTO2;
import org.iesvdm.dto.PedidoDTO;
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
	
	public DoubleSummaryStatistics sacarEstadisticas(List<PedidoDTO> listaPedidos) {
		DoubleSummaryStatistics datos;
		datos = listaPedidos.stream().collect(Collectors.summarizingDouble(p -> p.getPedidoComercial().getTotal()));
		return datos;

	}
	
	public List<Date>  fechasLimites(List<Integer> mesesLimites){
		
		return ComercialDAO.limitesEstadisticos(mesesLimites);
	}
	
	public List<Integer> comercialesDAODePedidos(List<Pedido> pedidosDeCliente){
		return comercialDAO.comercialesDePedidos(pedidosDeCliente);
	}
	
	public List<ComercialDTO2> comercialesDTO2dePedidos(
			List<Pedido> pedidos, List<Integer> comerciales, List<Date> limites){
		return comercialDAO.estadisticasPedidos(pedidos, comerciales, limites);
	}
		
		

}