package org.iesvdm.dto;

import java.util.List;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;

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
	
	public ComercialDTO(Comercial comercial, List<PedidoDTO> list) {
		this.comercialConPedidos = comercial;
		this.misPedidos = list;
	}
	
}
