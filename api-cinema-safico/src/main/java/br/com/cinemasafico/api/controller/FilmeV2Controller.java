package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Filme;
import br.com.cinemasafico.api.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/filmes")
@Tag(name = "Filmes (v2)", description = "Segunda versão do catálogo de filmes com envelope enriquecido e metadados de contrato")
public class FilmeV2Controller {

    public record FilmeV2Response(String versaoContrato, String consultadoEm, Filme dados) {}

    private final FilmeService service;

    public FilmeV2Controller(FilmeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar filmes (v2)", description = "Retorna a listagem paginada de filmes no contrato da versão 2.")
    @ApiResponse(responseCode = "200", description = "Listagem retornada com sucesso")
    public ResponseEntity<Page<Filme>> listarTodosV2(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por ID com metadados (v2)", description = "Retorna o filme encapsulado com versão do contrato, timestamp da consulta e links HATEOAS entre v1 e v2.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido (erro de tipo)"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    })
    public ResponseEntity<EntityModel<FilmeV2Response>> buscarPorIdV2(
            @Parameter(description = "ID do filme", example = "1", required = true) @PathVariable Long id) {
        Filme filme = service.buscarPorId(id);
        FilmeV2Response payloadV2 = new FilmeV2Response("2.0.0", LocalDateTime.now().toString(), filme);

        EntityModel<FilmeV2Response> resource = EntityModel.of(payloadV2);
        resource.add(linkTo(methodOn(FilmeV2Controller.class).buscarPorIdV2(id)).withSelfRel());
        resource.add(linkTo(methodOn(FilmeV2Controller.class).listarTodosV2(Pageable.unpaged())).withRel("lista-v2"));
        resource.add(linkTo(methodOn(FilmeController.class).buscarPorId(id)).withRel("versao-anterior-v1"));

        return ResponseEntity.ok(resource);
    }
}