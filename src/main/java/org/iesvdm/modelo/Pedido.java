package org.iesvdm.modelo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Pedido {
	private int id;
	private Double total;
	private Date fecha;
	private int id_cliente;
	private int id_comercial;
}
