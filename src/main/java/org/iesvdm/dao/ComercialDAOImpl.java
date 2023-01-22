package org.iesvdm.dao;

import static java.util.stream.Collectors.toList;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.dto.ComercialDTO;
import org.iesvdm.dto.ComercialDTO2;
import org.iesvdm.mapstruct.ComercialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

//import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
//@AllArgsConstructor
public class ComercialDAOImpl implements ComercialDAO{
	@Autowired
	/*no pq mejor hacer un constructor*/
	private JdbcTemplate jdbcTemplate;
	 
	 
	 
	@Override
	public void create(Comercial comercial) {
		String sqlInsert = """
				INSERT INTO comercial (nombre, apellido1, apellido2, comisión) 
				   VALUES  (             ?,         ?,       ?,         ?)
			   """;

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		int rows = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { "id" });
			int idx = 1;
			ps.setString(idx++, comercial.getNombre());
			ps.setString(idx++, comercial.getApellido1());
			ps.setString(idx++, comercial.getApellido2());
			ps.setFloat(idx, comercial.getComision());
			return ps;
		},keyHolder);
		
		comercial.setId(keyHolder.getKey().intValue());

		log.info("Insertados {} registros.", rows);
	}
	
	@Override
	public List<Comercial> getAll(){
		
		List<Comercial> listCom = jdbcTemplate.query(
                "SELECT * FROM comercial",
                (rs, rowNum) -> new Comercial(rs.getInt("id"),
                						 	rs.getString("nombre"),
                						 	rs.getString("apellido1"),
                						 	rs.getString("apellido2"),
                						 	rs.getFloat("comisión")
                						 	)
        );
		
		log.info("Devueltos {} registros.", listCom.size());
		
        return listCom;
		 
	}
	
	@Override
	public Optional<Comercial>  find(int id){
		Comercial com =  jdbcTemplate
				.queryForObject("SELECT * FROM comercial WHERE id = ?"														
								, (rs, rowNum) -> new Comercial(rs.getInt("id"),
            						 						rs.getString("nombre"),
            						 						rs.getString("apellido1"),
            						 						rs.getString("apellido2"),
            						 						rs.getFloat("comisión")) 
								, id
								);
		
		if (com != null) { 
			return Optional.of(com);}
		else { 
			log.info("Comercial no encontrado.");
			return Optional.empty(); }
	}
	
	@Override
	public void update(Comercial comercial){
		int rows = jdbcTemplate.update("""
				UPDATE comercial SET 
								nombre = ?, 
								apellido1 = ?, 
								apellido2 = ?,
								comisión = ?
						WHERE id = ?
				""", comercial.getNombre()
				, comercial.getApellido1()
				, comercial.getApellido2()
				, comercial.getComision()
				, comercial.getId());

		log.info("Update de Comercial con {} registros actualizados.", rows);
	}
	
	@Override
	public void delete(int id){
		int rows = jdbcTemplate.update("DELETE FROM comercial WHERE id = ?", id);
		
		log.info("Delete de Comercial con {} registros eliminados.", rows);	 
	}
	 //saca las id de los comerciles de un listado de Pedidos
	@Override
	public List<Integer> comercialesDePedidos(List<Pedido> pedidosDeCliente){
		List<Integer> idComerciales = pedidosDeCliente.stream().map(Pedido::getId_comercial).distinct().toList();
		
        return idComerciales;
	}	
	
	
	//trae el mapeo
	@Autowired
	private ComercialMapper comercialMapper;
	
	//devuelve ComercialDTO2 con estadisticas
	@Override
	public List<ComercialDTO2> estadisticasPedidos(List<Pedido> pedidos, List<Integer> comerciales, List<Date> limites){
		List<ComercialDTO2> comercialesConEstadisticas = new ArrayList<>();
		
		Date currentDate = new Date();
		List<Integer> valoresEstadisticas = new ArrayList<>();
		
		//por cada id comercial...
		comerciales.forEach((Integer comercialId) -> {
			//usamos cada fecha límite sobre el listado de pedidos para sacar cada estadística y la guardamos en un listado
			limites.forEach((Date limite) ->{
				Long result = pedidos.stream()
						.filter(t -> t.getFecha().after(limite) && t.getFecha().before(currentDate))
						.count();
				valoresEstadisticas.add(result.intValue());
			});
			//uso del mapeo. Encontramos el id comercial para traer el Comercial y añadimos las estadisticas guardadas en el listado
			ComercialDTO2 comercialDTO2 = comercialMapper.comercialAComercialDTO2(this.find(comercialId.intValue()).get(), 
					valoresEstadisticas.get(0), valoresEstadisticas.get(1), valoresEstadisticas.get(2), valoresEstadisticas.get(3));
			
			//guardamos cada ComercialDTO2 para devolverlos en listado
			comercialesConEstadisticas.add(comercialDTO2);
		});
		
		return comercialesConEstadisticas;
	}
	 
}
