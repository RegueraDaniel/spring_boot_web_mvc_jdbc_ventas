package org.iesvdm.controlador;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.iesvdm.dto.ComercialDTO2;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.service.ComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.validation.Valid;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;
	
	private ComercialService comercialService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService, ComercialService comercialService) {
		this.clienteService = clienteService;
		this.comercialService = comercialService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
	}
	

	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id ) {
		
		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);
		
		List<Pedido> pedidosDelCliente = clienteService.pedidosDeUnCliente(id.intValue());
		
		List<Integer> limites = Arrays.asList(-3, -6, -12, -60);
		
		List<Date> limiteFechas = comercialService.fechasLimites(limites);
		
		List<Integer> idComercialesDePedidos = comercialService.comercialesDAODePedidos(pedidosDelCliente);
		
		List<ComercialDTO2> listaComerciales =comercialService.comercialesDTO2dePedidos(pedidosDelCliente, idComercialesDePedidos, limiteFechas);
		model.addAttribute("estadisticasComerciales", listaComerciales);
		return "detalle-cliente";
	}
	
	@GetMapping("/validation")
	public String validation(@ModelAttribute Cliente cliente,  Model model) {
			
		return "validation";
		
	}
	
	@PostMapping("/validation")
	public String validationPost(@Valid @ModelAttribute Cliente cliente, BindingResult bindigResulted, Model model) {
		
		model.addAttribute("cliente", cliente);
		model.addAttribute("toString", cliente.toString());
				
		return "validation" ;
	}
	
	@GetMapping("/clientes/crear")
	public String crear(Model model) {
		
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		
		return "crear-cliente";
		
	}
	
	@PostMapping("/clientes/crear")
	public RedirectView submitCrear( @ModelAttribute Cliente cliente,  Model model) {
		
		clienteService.newCliente(cliente);
				
		return new RedirectView("/clientes") ;
	}
	
	@GetMapping("/clientes/editar/{id}")
	public String editar(Model model, @PathVariable Integer id) {
		
		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);
		
		return "editar-cliente";
		
	}
	
	
	@PostMapping("/clientes/editar/{id}")
	public RedirectView submitEditar(@ModelAttribute("cliente") Cliente cliente) {
		
		clienteService.replaceCliente(cliente);		
		
		return new RedirectView("/clientes");
	}
	
	@PostMapping("/clientes/borrar/{id}")
	public RedirectView submitBorrar(@PathVariable Integer id) {
		
		clienteService.deleteCliente(id);
		
		return new RedirectView("/clientes");
	}
	
}
