package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Filme;
import br.com.cinemasafico.api.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService service;

    // Endpoint para criar um novo filme
    @PostMapping
    public ResponseEntity<Filme> cadastrar(@Valid @RequestBody Filme filme) {
        Filme filmeSalvo = service.salvar(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(filmeSalvo); // Retorna 201 Created
    }

    // Endpoint para listar todos os filmes com paginação
    @GetMapping
    public ResponseEntity<Page<Filme>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable)); // Retorna 200 OK
    }

    // Endpoint para buscar um filme específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id)); // Retorna 200 OK
    }

    // Endpoint para atualizar um filme pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id, @Valid @RequestBody Filme filme) {
        Filme filmeAtualizado = service.atualizar(id, filme);
        return ResponseEntity.ok(filmeAtualizado); // Retorna 200 OK
    }

    // Endpoint para eliminar um filme pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}