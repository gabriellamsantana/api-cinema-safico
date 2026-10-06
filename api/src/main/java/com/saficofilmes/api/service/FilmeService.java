package com.saficofilmes.api.service;

import com.saficofilmes.api.dto.FilmeRequestDTO;
import com.saficofilmes.api.dto.FilmeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    public Page<FilmeResponseDTO> listarTodos(Pageable pageable) {
        return Page.empty();
    }

    public FilmeResponseDTO buscarPorId(Long id) {
        return null;
    }

    public FilmeResponseDTO salvar(FilmeRequestDTO dto) {
        return null;
    }

    public FilmeResponseDTO atualizar(Long id, FilmeRequestDTO dto) {
        return null;
    }

    public void deletar(Long id) {
    }
}