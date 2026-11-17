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

import java.time.Instant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/filmes")
@Tag(name = "Filmes (v2)", description = "Contrato evoluído v2 com metadados de versão e navegação HATEOAS")
public class FilmeV2Controller {

    public record FilmeV2Response(String versaoContrato, Instant consultadoEm, Filme dados) {}

    private final FilmeService service;

    public FilmeV2Controller(FilmeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar filmes (Contrato v2)", description = "Retorna a lista paginada de filmes no contrato v2.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    public ResponseEntity<Page<Filme>> listarTodosV2(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por ID com metadados (Contrato v2)", description = "Retorna o filme encapsulado com metadados da versão 2.0.0 e links HATEOAS para v1 e v2.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public ResponseEntity<EntityModel<FilmeV2Response>> buscarPorIdV2(
            @Parameter(description = "ID do filme", example = "1", required = true) @PathVariable Long id) {
        Filme filme = service.buscarPorId(id);
        return ResponseEntity.ok(enveloparComHateoas(filme));
    }

    @PostMapping
    @Operation(summary = "Cadastrar filme (Contrato v2)", description = "Cria um novo filme e retorna o payload envelopado da v2.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filme cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    public ResponseEntity<EntityModel<FilmeV2Response>> criarV2(@Valid @RequestBody Filme filme) {
        Filme salvo = service.salvar(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(enveloparComHateoas(salvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme (Contrato v2)", description = "Atualiza um filme existente e retorna o payload envelopado da v2.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public ResponseEntity<EntityModel<FilmeV2Response>> atualizarV2(
            @Parameter(description = "ID do filme", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody Filme filme) {
        Filme atualizado = service.atualizar(id, filme);
        return ResponseEntity.ok(enveloparComHateoas(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir filme (Contrato v2)", description = "Remove um filme pelo identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Filme excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public ResponseEntity<Void> deletarV2(
            @Parameter(description = "ID do filme", example = "1", required = true) @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<FilmeV2Response> enveloparComHateoas(Filme filme) {
        FilmeV2Response payload = new FilmeV2Response("2.0.0", Instant.now(), filme);
        EntityModel<FilmeV2Response> model = EntityModel.of(payload);
        model.add(linkTo(methodOn(FilmeV2Controller.class).buscarPorIdV2(filme.getId())).withSelfRel());
        model.add(linkTo(methodOn(FilmeV2Controller.class).listarTodosV2(Pageable.unpaged())).withRel("lista-v2"));
        model.add(linkTo(methodOn(FilmeController.class).buscarPorId(filme.getId())).withRel("versao-anterior-v1"));
        return model;
    }
}