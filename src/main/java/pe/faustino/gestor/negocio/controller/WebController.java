package pe.faustino.gestor.negocio.controller;

import lombok.RequiredArgsConstructor;
import pe.faustino.gestor.negocio.model.Producto;
import pe.faustino.gestor.negocio.repository.UsuarioRepository;
import pe.faustino.gestor.negocio.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final UsuarioRepository usuarioRepository;
    private final ProductoService productoService;

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model) {
        var usuarioOpt = usuarioRepository.findByLogiUsuaAndPassUsua(username, password);
        if (usuarioOpt.isPresent()) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Credenciales incorrectas.");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("nuevoProducto", new Producto());
        return "dashboard";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/dashboard";
    }
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoService.eliminar(id);
        return "redirect:/dashboard";
    }
}