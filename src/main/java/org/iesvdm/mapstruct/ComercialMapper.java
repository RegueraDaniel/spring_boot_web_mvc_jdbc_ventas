/*A detalle-cliente añade la lista de comerciales asociados 
 * junto con el conteo de pedidos por comercial, 
 * número de pedidos realizados  en el último trimestre, semestre, año, y lustro 
 * para el cliente en cuestión. 
 */

package org.iesvdm.mapstruct;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.dto.ComercialDTO2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComercialMapper {
	
	//cambiar critica y criticaIn
	@Mapping(target = "trimestre", source = "trimestreIn")
	@Mapping(target = "medioAnnio", source = "medioAnnioIn") 
	@Mapping(target = "annio", source = "annioIn")
	@Mapping(target = "lustro", source = "lustroIn")
	//agregar anotacion mapping por cada atributo extra
	public ComercialDTO2 comercialAComercialDTO2(
							Comercial comercial, int trimestreIn, int medioAnnioIn, int annioIn, int lustroIn);
	
	
	//operacion inversa
	public Comercial comercialDTO2AComercial(ComercialDTO2 comercialDTO2);
	
}