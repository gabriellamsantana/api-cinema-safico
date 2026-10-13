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

// Imports obrigatórios para o HATEOAS funcionar
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService service;

    @PostMapping
    public ResponseEntity<Filme> cadastrar(@Valid @RequestBody Filme filme) {
        Filme salvo = service.salvar(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<Page<Filme>> listarTodas(Pageable pageable) {
        // Nota: se o seu método no service se chamar listarTodos, ajuste aqui
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {
        Filme filme = service.buscarPorId(id);

        // HATEOAS: Adiciona um link para o próprio filme (self)
        filme.add(linkTo(methodOn(FilmeController.class).buscarPorId(id)).withSelfRel());

        // HATEOAS: Adiciona um link rápido para a lista completa
        filme.add(linkTo(methodOn(FilmeController.class).listarTodas(null)).withRel("lista-completa"));

        return ResponseEntity.ok(filme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id, @Valid @RequestBody Filme filme) {
        Filme filmeAtualizado = service.atualizar(id, filme);
        return ResponseEntity.ok(filmeAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}