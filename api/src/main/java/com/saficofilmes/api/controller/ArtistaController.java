package com.saficofilmes.api.controller;

import com.saficofilmes.api.dto.ArtistaRequestDTO;
import com.saficofilmes.api.dto.ArtistaResponseDTO;
import com.saficofilmes.api.service.ArtistaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public ResponseEntity<Page<ArtistaResponseDTO>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(artistaService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(artistaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ArtistaResponseDTO> salvar(@RequestBody @Valid ArtistaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistaService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ArtistaRequestDTO dto) {
        return ResponseEntity.ok(artistaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        artistaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}