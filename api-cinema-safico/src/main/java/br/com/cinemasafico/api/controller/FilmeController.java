package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Filme;
import br.com.cinemasafico.api.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/filmes")
@Tag(name = "Filmes (v1)", description = "Endpoints para gerenciamento do catálogo de filmes")
public class FilmeController {

    private final FilmeService service;

    public FilmeController(FilmeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os filmes de forma paginada", description = "Retorna uma página de filmes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de filmes recuperada com sucesso")
    public ResponseEntity<Page<Filme>> listarTodos(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um filme pelo ID", description = "Retorna os detalhes de um filme específico com links HATEOAS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido (erro de tipo)"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public ResponseEntity<EntityModel<Filme>> buscarPorId(
            @Parameter(description = "ID do filme", example = "1", required = true) @PathVariable Long id) {
        Filme filme = service.buscarPorId(id);
        EntityModel<Filme> model = EntityModel.of(filme);
        model.add(linkTo(methodOn(FilmeController.class).buscarPorId(id)).withSelfRel());
        model.add(linkTo(methodOn(FilmeController.class).listarTodos(Pageable.unpaged())).withRel("filmes"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo filme", description = "Cria um novo filme no catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filme cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    public ResponseEntity<EntityModel<Filme>> criar(@Valid @RequestBody Filme filme) {
        Filme salvo = service.salvar(filme);
        EntityModel<Filme> model = EntityModel.of(salvo);
        model.add(linkTo(methodOn(FilmeController.class).buscarPorId(salvo.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um filme", description = "Atualiza os dados de um filme existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou ID inválido"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public ResponseEntity<EntityModel<Filme>> atualizar(
            @Parameter(description = "ID do filme", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody Filme filme) {
        Filme atualizado = service.atualizar(id, filme);
        EntityModel<Filme> model = EntityModel.of(atualizado);
        model.add(linkTo(methodOn(FilmeController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um filme pelo ID", description = "Remove um filme pelo seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Filme excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do filme", example = "1", required = true) @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}