package br.com.cinemasafico.api.controller;

import br.com.cinemasafico.api.model.Artista;
import br.com.cinemasafico.api.service.ArtistaService;
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
@RequestMapping({"/api/v1/artistas", "/api/v2/artistas"})
@Tag(name = "Artistas", description = "Endpoints para gerenciamento de atrizes, diretoras e roteiristas")
public class ArtistaController {

    private final ArtistaService service;

    public ArtistaController(ArtistaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos os artistas de forma paginada", description = "Retorna uma página de artistas cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de artistas recuperada com sucesso")
    public ResponseEntity<Page<Artista>> listarTodos(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um artista pelo ID", description = "Retorna os detalhes de um artista específico com links HATEOAS.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artista encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido (erro de tipo)"),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    public ResponseEntity<EntityModel<Artista>> buscarPorId(
            @Parameter(description = "ID do artista", example = "1", required = true) @PathVariable Long id) {
        Artista artista = service.buscarPorId(id);
        EntityModel<Artista> model = EntityModel.of(artista);
        model.add(linkTo(methodOn(ArtistaController.class).buscarPorId(id)).withSelfRel());
        model.add(linkTo(methodOn(ArtistaController.class).listarTodos(Pageable.unpaged())).withRel("artistas"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo artista", description = "Cria um novo registro de artista.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artista cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    public ResponseEntity<EntityModel<Artista>> criar(@Valid @RequestBody Artista artista) {
        Artista salvo = service.salvar(artista);
        EntityModel<Artista> model = EntityModel.of(salvo);
        model.add(linkTo(methodOn(ArtistaController.class).buscarPorId(salvo.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um artista", description = "Atualiza os dados de um artista existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artista atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou ID inválido"),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    public ResponseEntity<EntityModel<Artista>> atualizar(
            @Parameter(description = "ID do artista", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody Artista artista) {
        Artista atualizado = service.atualizar(id, artista);
        EntityModel<Artista> model = EntityModel.of(atualizado);
        model.add(linkTo(methodOn(ArtistaController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um artista pelo ID", description = "Remove um artista pelo seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artista excluído com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID fornecido é inválido"),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do artista", example = "1", required = true) @PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}