package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Avaliacao;
import br.com.cinemasafico.api.service.AvaliacaoService;
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
@RequestMapping("/api/v1/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de notas e críticas especializadas")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }


    @Operation(summary = "Listar todas as avaliações de forma paginada", description = "Retorna uma página de avaliações cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de avaliações recuperada com sucesso")
    public ResponseEntity<Page<Avaliacao>> listarTodas(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma avaliação pelo ID", description = "Retorna os detalhes de uma avaliação específica com links HATEOAS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido (erro de tipo)"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<EntityModel<Avaliacao>> buscarPorId(
            @Parameter(description = "ID da avaliação", example = "1", required = true) @PathVariable Long id) {
        Avaliacao avaliacao = service.buscarPorId(id);
        EntityModel<Avaliacao> model = EntityModel.of(avaliacao);
        model.add(linkTo(methodOn(AvaliacaoController.class).buscarPorId(id)).withSelfRel());
        model.add(linkTo(methodOn(AvaliacaoController.class).listarTodas(Pageable.unpaged())).withRel("avaliacoes"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Criar uma nova avaliação para um filme", description = "Registra uma avaliação crítica vinculada a um filme.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    public ResponseEntity<EntityModel<Avaliacao>> criar(@Valid @RequestBody Avaliacao avaliacao) {
        Avaliacao salva = service.salvar(avaliacao);
        EntityModel<Avaliacao> model = EntityModel.of(salva);
        model.add(linkTo(methodOn(AvaliacaoController.class).buscarPorId(salva.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma avaliação", description = "Atualiza os dados de uma avaliação existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou ID inválido"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<EntityModel<Avaliacao>> atualizar(
            @Parameter(description = "ID da avaliação", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody Avaliacao avaliacao) {
        Avaliacao atualizada = service.atualizar(id, avaliacao);
        EntityModel<Avaliacao> model = EntityModel.of(atualizada);
        model.add(linkTo(methodOn(AvaliacaoController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma avaliação pelo ID", description = "Remove uma avaliação pelo seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da avaliação", example = "1", required = true) @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}