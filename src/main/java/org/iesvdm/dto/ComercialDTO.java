package org.iesvdm.dto;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;

import org.iesvdm.modelo.Comercial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
//@NoArgsConstructor
//quitamos para q tire del super
//@AllArgsConstructor
@SuppressWarnings("unused")

public class ComercialDTO {
	
	//tendrá atributos de comercial además del listado de pedidos
	private Comercial comercialConPedidos;
	private List<PedidoDTO> misPedidos;
	private Double minimo;
	private Double maximo;
	private Double media;
	private Double suma;
	
	
	/*
	private Double totalImportes;
	private Double media;*/
	
	public ComercialDTO(Comercial comercial, List<PedidoDTO> list, DoubleSummaryStatistics datos) {
		this.comercialConPedidos = comercial;
		this.misPedidos = list;
		this.minimo = datos.getMin();
		this.maximo = datos.getMax();
		this.media = datos.getAverage();
		this.suma = datos.getSum();
		
		
		/*
		this.totalImportes = 0.0;
		this.media = 0.0;
		
		if(list != null) {
			 list.forEach(p -> this.totalImportes += p.getPedidoComercial().getTotal());
			 this.media = totalImportes/list.size();
		}*/
		
	}
	
}
