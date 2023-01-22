package org.iesvdm.modelo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//La anotación @Data de lombok proporcionará el código de: 
//getters/setters, toString, equals y hashCode
//propio de los objetos POJOS o tipo Beans
@Data
//Para generar un constructor con lombok con todos los args

/*anotacion importante para constructor sin argumentos*/
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class Cliente {
	
	private int id;
	@NotBlank(message = "{error.nombre}" )
	@Size(max=30, message = "{error.mombre.size.max}")
	private String nombre;
	
	@NotBlank(message = "{error.apellido1}")
	@Size(max=30, message = "{error.apellido1.size.max}")
	private String apellido1;
	
	private String apellido2;
	
	@NotBlank(message = "{error.ciudad}")
	@Size(max=50, message = "{error.ciudad.size.max}")
	private String ciudad;
	
	@NotBlank(message = "{error.categoria}")
	@Min(value=100, message = "{error.categoria.min.value}")
	@Max(value=1000, message = "{error.categoria.max.value}")
	private int categoria;
	
}
