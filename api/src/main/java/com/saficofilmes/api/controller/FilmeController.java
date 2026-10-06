package com.saficofilmes.api.controller;

import com.saficofilmes.api.dto.FilmeRequestDTO;
import com.saficofilmes.api.dto.FilmeResponseDTO;
import com.saficofilmes.api.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    public ResponseEntity<Page<FilmeResponseDTO>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(filmeService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(filmeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<FilmeResponseDTO> salvar(@RequestBody @Valid FilmeRequestDTO dto) {
        FilmeResponseDTO salvo = filmeService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);

    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid FilmeRequestDTO dto) {
        return ResponseEntity.ok(filmeService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        filmeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
