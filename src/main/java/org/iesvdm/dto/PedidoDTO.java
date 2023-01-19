package org.iesvdm.dto;

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
public class PedidoDTO {
	private Pedido pedidoComercial;
	private String nombreCliente;
	private String apellidoCliente;
	
	public PedidoDTO(Pedido pedido, String nombre, String apellido) {
		this.pedidoComercial = pedido;
		this.nombreCliente = nombre;
		this.apellidoCliente = apellido;
	}
}
