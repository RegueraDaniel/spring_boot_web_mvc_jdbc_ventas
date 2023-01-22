package org.iesvdm.dto;

import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
//@NoArgsConstructor
//quitamos para q tire del super
@AllArgsConstructor
//@SuppressWarnings("unused")

public class ComercialDTO2 {
	
	//tendrá atributos de comercial además del listado de pedidos
	/*private int id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private float comision;
*/
	private Comercial comercial;
	
	private int trimestre;
	private int medioAnnio;
	private int annio;
	private int lustro;
	
	/*
	private Double totalImportes;
	private Double media;*/
	
	/*public ComercialDTO2(
			Comercial comercial,
			int trimestre, int medioAnnio, int annio, int lustro) {
		//int id, String nombre, String apellido1, String apellido2, float comision
		//this.super(id, nombre, apellido1, apellido2, comision);
		
		this.trimestre = trimestre;
		this.medioAnnio = medioAnnio;
		this.annio = annio;
		this.lustro = lustro;
		
		/*
		this.totalImportes = 0.0;
		this.media = 0.0;
		
		if(list != null) {
			 list.forEach(p -> this.totalImportes += p.getPedidoComercial().getTotal());
			 this.media = totalImportes/list.size();
		}*/
		
	//}
	
}
