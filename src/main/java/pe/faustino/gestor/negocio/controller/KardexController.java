package pe.faustino.gestor.negocio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.faustino.gestor.negocio.model.Kardex;
import pe.faustino.gestor.negocio.service.KardexService;

@RestController
@RequestMapping("/api/kardex")
@RequiredArgsConstructor
public class KardexController {

    private final KardexService kardexService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestParam Integer codiProd,
                                       @RequestParam Integer cantidad,
                                       @RequestParam Integer tipo) {
        try {
            Kardex nuevoMov = kardexService.registrarMovimiento(codiProd, cantidad, tipo);
            return ResponseEntity.ok(nuevoMov);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}