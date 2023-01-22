package org.iesvdm.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.iesvdm.dto.ComercialDTO2;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

public interface ComercialDAO {
	
	public void create(Comercial comercial);
	
	public List<Comercial> getAll();
	
	public Optional<Comercial>  find(int id);
	
	public void update(Comercial comercial);
	
	public void delete(int id);
	
	public List<Integer> comercialesDePedidos(List<Pedido> pedidosDeCliente);
	
	//devuelve valores límites pasados para orquilla de tiempo
	public static List<Date> limitesEstadisticos(List<Integer> limites){
		List<Date> timeLimits = new ArrayList<>();
		
		Date currentDate = new Date();

        limites.forEach((Integer fecha) -> {
        	Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.MONTH, fecha);
            Date limite = c.getTime();
            timeLimits.add(limite);
        });
        
		return timeLimits;
	}
	
	public List<ComercialDTO2> estadisticasPedidos(List<Pedido> pedidos, List<Integer> comerciales, List<Date> limites);
}
