package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import org.iesvdm.modelo.Pedido;
import org.iesvdm.dto.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository

public class PedidoDAOImpl implements PedidoDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<PedidoDTO> getPedidosDelComercial(int idComercial){
		
		List<PedidoDTO> listped = jdbcTemplate.query(
                "SELECT * FROM pedido LEFT OUTER JOIN cliente on pedido.id_cliente = cliente.id WHERE id_comercial=?", 
                (rs, rowNum) -> { Pedido ped = new Pedido (
                							rs.getInt("id"),
                						 	rs.getDouble("total"),
                						 	rs.getDate("fecha"),
                						 	rs.getInt("id_cliente"),
                						 	rs.getInt("id_comercial")
                						 	); 
                				PedidoDTO pedDTO = new PedidoDTO(ped, 
                							rs.getString("nombre"),
                						 	rs.getString("apellido1"));
                				return pedDTO;
                				
                }, idComercial
        );
		
		log.info("Devueltos {} registros.", listped.size());
		
        return listped;
		 
	}
	
	@Override
	public List<String> getImportesMayorAMenor(List<PedidoDTO> lista){
		List<PedidoDTO> listaAdevolver = new ArrayList<>();
		listaAdevolver.addAll(lista);
		
		List<String> topClientes = lista.stream()
				.sorted((p1, p2)-> Double.compare(p2.getPedidoComercial().getTotal(), p1.getPedidoComercial().getTotal()))
				.map(PedidoDTO::getNombreCliente)
				.distinct()
				.collect(Collectors.toList());
		
			
		Map<String, Optional<PedidoDTO>> prueba = lista.stream()	
				.sorted((p1, p2)-> Double.compare(p2.getPedidoComercial().getTotal(), p1.getPedidoComercial().getTotal()))
				.collect(groupingBy(PedidoDTO::getNombreCliente, LinkedHashMap::new, maxBy(comparingDouble(p -> p.getPedidoComercial().getTotal() ))) );
		
		/*
		List<String> devolucion = prueba.entrySet().stream()
									.sorted((p1, p2)-> Double.compare(((PedidoDTO) p2).getPedidoComercial().getTotal(), ((PedidoDTO)p1).getPedidoComercial().getTotal()))
									.map( p -> ((PedidoDTO) p).getNombreCliente()+" "+((PedidoDTO) p).getPedidoComercial().getTotal() )
									.collect(Collectors.toList());
		*/
		/*
		List<String> devolucion = prueba.entrySet().stream()
				.sorted((p1, p2)-> Double.compare(( p2).getPedidoComercial().getTotal(), (p1).getPedidoComercial().getTotal()))
				.map( p -> ( p).getNombreCliente()+" "+(p).getPedidoComercial().getTotal() )
				.collect(Collectors.toList());
		*/
		List<String> top = new ArrayList<>();
		prueba.forEach((t, u) -> System.out.println(top.add( t +" "+ u.get().getPedidoComercial().getTotal())));
		//devolucion.forEach(System.out::println);
		
		return top;
	}
	
	public List<PedidoDTO> pedidosCliente(int idCliente){
		List<PedidoDTO> listped = jdbcTemplate.query(
                "SELECT * FROM pedido LEFT OUTER JOIN comercial on pedido.id_comercial = comercial.id WHERE id_cliente=?", 
                (rs, rowNum) -> { Pedido ped = new Pedido (
                							rs.getInt("id"),
                						 	rs.getDouble("total"),
                						 	rs.getDate("fecha"),
                						 	rs.getInt("id_cliente"),
                						 	rs.getInt("id_comercial")
                						 	); 
                				PedidoDTO pedDTO = new PedidoDTO(ped, 
                							rs.getString("nombre"),
                						 	rs.getString("apellido1"));
                				return pedDTO;
                				
                }, idCliente
        );
		
		log.info("Devueltos {} registros.", listped.size());
		
		return listped;
	}
	
}
