package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Plataforma;
import br.com.cinemasafico.api.service.PlataformaService;
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
@RequestMapping("/plataformas")
@Tag(name = "Plataformas", description = "Endpoints para gerenciamento das plataformas de streaming")
public class PlataformaController {

    private final PlataformaService service;

    public PlataformaController(PlataformaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas as plataformas de forma paginada", description = "Retorna uma página de plataformas cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de plataformas recuperada com sucesso")
    public ResponseEntity<Page<Plataforma>> listarTodas(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listarTodas(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma plataforma pelo ID", description = "Retorna os detalhes de uma plataforma específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plataforma encontrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido (erro de tipo)"),
            @ApiResponse(responseCode = "404", description = "Plataforma não encontrada")
    })
    public ResponseEntity<EntityModel<Plataforma>> buscarPorId(
            @Parameter(description = "ID da plataforma", example = "1", required = true) @PathVariable Long id) {
        Plataforma plataforma = service.buscarPorId(id);
        EntityModel<Plataforma> model = EntityModel.of(plataforma);
        model.add(linkTo(methodOn(PlataformaController.class).buscarPorId(id)).withSelfRel());
        model.add(linkTo(methodOn(PlataformaController.class).listarTodas(Pageable.unpaged())).withRel("plataformas"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova plataforma", description = "Adiciona um serviço de streaming ao catálogo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plataforma cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    public ResponseEntity<EntityModel<Plataforma>> cadastrar(@Valid @RequestBody Plataforma plataforma) {
        Plataforma salva = service.salvar(plataforma);
        EntityModel<Plataforma> model = EntityModel.of(salva);
        model.add(linkTo(methodOn(PlataformaController.class).buscarPorId(salva.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados de uma plataforma", description = "Atualiza uma plataforma existente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plataforma atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou ID inválido"),
            @ApiResponse(responseCode = "404", description = "Plataforma não encontrada")
    })
    public ResponseEntity<EntityModel<Plataforma>> atualizar(
            @Parameter(description = "ID da plataforma", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody Plataforma plataforma) {
        Plataforma atualizada = service.atualizar(id, plataforma);
        EntityModel<Plataforma> model = EntityModel.of(atualizada);
        model.add(linkTo(methodOn(PlataformaController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma plataforma pelo ID", description = "Remove uma plataforma do cadastro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plataforma excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido"),
            @ApiResponse(responseCode = "404", description = "Plataforma não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da plataforma", example = "1", required = true) @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}