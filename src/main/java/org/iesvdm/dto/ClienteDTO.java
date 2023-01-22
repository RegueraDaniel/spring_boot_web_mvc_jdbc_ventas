package org.iesvdm.dto;

import org.iesvdm.modelo.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class ClienteDTO {
	
	private int id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String ciudad;
	private int categoria;
	
	private String critica;
}
