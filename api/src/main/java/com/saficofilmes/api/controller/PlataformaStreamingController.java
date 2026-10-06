package com.saficofilmes.api.controller;

import com.saficofilmes.api.dto.PlataformaStreamingRequestDTO;
import com.saficofilmes.api.dto.PlataformaStreamingResponseDTO;
import com.saficofilmes.api.service.PlataformaStreamingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plataformas")
public class PlataformaStreamingController {

    private final PlataformaStreamingService plataformaService;

    public PlataformaStreamingController(PlataformaStreamingService plataformaService) {
        this.plataformaService = plataformaService;
    }

    @GetMapping
    public ResponseEntity<Page<PlataformaStreamingResponseDTO>> listarTodos(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(plataformaService.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlataformaStreamingResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(plataformaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlataformaStreamingResponseDTO> salvar(@RequestBody @Valid PlataformaStreamingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plataformaService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlataformaStreamingResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PlataformaStreamingRequestDTO dto) {
        return ResponseEntity.ok(plataformaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        plataformaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
