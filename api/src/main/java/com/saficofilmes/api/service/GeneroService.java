package com.saficofilmes.api.service;

import com.saficofilmes.api.dto.GeneroRequestDTO;
import com.saficofilmes.api.dto.GeneroResponseDTO;
import com.saficofilmes.api.model.Genero;
import com.saficofilmes.api.repository.GeneroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Transactional(readOnly = true)
    public Page<GeneroResponseDTO> listarTodos(Pageable pageable) {
        return generoRepository.findAll(pageable).map(this::converterParaResponseDTO);
    }

    @Transactional(readOnly = true)
    public GeneroResponseDTO buscarPorId(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com o ID: " + id));
        return converterParaResponseDTO(genero);
    }

    @Transactional
    public GeneroResponseDTO salvar(GeneroRequestDTO dto) {
        Genero genero = new Genero();
        converterParaEntidade(dto, genero);
        genero = generoRepository.save(genero);
        return converterParaResponseDTO(genero);
    }

    @Transactional
    public GeneroResponseDTO atualizar(Long id, GeneroRequestDTO dto) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com o ID: " + id));

        converterParaEntidade(dto, genero);
        genero = generoRepository.save(genero);
        return converterParaResponseDTO(genero);
    }

    @Transactional
    public void deletar(Long id) {
        Genero genero = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com o ID: " + id));
        generoRepository.delete(genero);
    }

    private GeneroResponseDTO converterParaResponseDTO(Genero genero) {
        return new GeneroResponseDTO(
                genero.getId(),
                genero.getNome(),
                genero.getDescricao()
        );
    }

    private void converterParaEntidade(GeneroRequestDTO dto, Genero genero) {
        genero.setNome(dto.nome());
        genero.setDescricao(dto.descricao());
    }
}