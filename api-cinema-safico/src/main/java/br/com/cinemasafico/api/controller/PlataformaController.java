package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Plataforma;
import br.com.cinemasafico.api.service.PlataformaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {

    @Autowired
    private PlataformaService service;

    @PostMapping
    public ResponseEntity<Plataforma> cadastrar(@Valid @RequestBody Plataforma plataforma) {
        Plataforma salva = service.salvar(plataforma);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<Page<Plataforma>> listarTodas(Pageable pageable) {
        return ResponseEntity.ok(service.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plataforma> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plataforma> atualizar(@PathVariable Long id, @Valid @RequestBody Plataforma plataforma) {
        return ResponseEntity.ok(service.atualizar(id, plataforma));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}