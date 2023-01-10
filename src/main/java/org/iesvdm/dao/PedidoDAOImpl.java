package org.iesvdm.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Date;

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
}
