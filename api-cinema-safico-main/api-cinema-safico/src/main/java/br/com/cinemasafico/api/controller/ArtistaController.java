package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Artista;
import br.com.cinemasafico.api.service.ArtistaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService service;

    @PostMapping
    public ResponseEntity<Artista> cadastrar(@Valid @RequestBody Artista artista) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(artista));
    }

    @GetMapping
    public ResponseEntity<Page<Artista>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @Valid @RequestBody Artista artista) {
        return ResponseEntity.ok(service.atualizar(id, artista));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}