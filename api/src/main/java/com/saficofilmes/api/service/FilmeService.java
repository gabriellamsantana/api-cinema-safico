package com.saficofilmes.api.service;

import com.saficofilmes.api.dto.FilmeRequestDTO;
import com.saficofilmes.api.dto.FilmeResponseDTO;
import com.saficofilmes.api.model.Filme;
import com.saficofilmes.api.repository.FilmeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional(readOnly = true)
    public Page<FilmeResponseDTO> listarTodos(Pageable pageable) {
        return filmeRepository.findAll(pageable)
                .map(this::converterParaResponseDTO);
    }

    @Transactional(readOnly = true)
    public FilmeResponseDTO buscarPorId(Long id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com o ID: " + id));
        return converterParaResponseDTO(filme);
    }

    @Transactional
    public FilmeResponseDTO salvar(FilmeRequestDTO dto) {
        Filme filme = new Filme();
        converterParaEntidade(dto, filme);
        filme = filmeRepository.save(filme);
        return converterParaResponseDTO(filme);
    }

    @Transactional
    public FilmeResponseDTO atualizar(Long id, FilmeRequestDTO dto) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com o ID: " + id));

        converterParaEntidade(dto, filme);
        filme = filmeRepository.save(filme);
        return converterParaResponseDTO(filme);
    }

    @Transactional
    public void deletar(Long id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com o ID: " + id));
        filmeRepository.delete(filme);
    }

    @Transactional(readOnly = true)
    public Page<FilmeResponseDTO> buscarPorNomeArtista(String nome, Pageable pageable) {
        return filmeRepository.buscarPorArtista(nome, pageable)
                .map(this::converterParaResponseDTO);
    }

    private FilmeResponseDTO converterParaResponseDTO(Filme filme) {
        String nomeDiretor = (filme.getDiretor() != null) ? filme.getDiretor().getNome() : null;

        List<String> nomesGeneros = filme.getGeneros().stream()
                .map(genero -> genero.getNome())
                .collect(Collectors.toList());

        return new FilmeResponseDTO(
                filme.getId(),
                filme.getTitulo(),
                filme.getSinopse(),
                filme.getAnoLancamento(),
                filme.getDuracaoMinutos(),
                filme.getTipoRepresentatividade(),
                nomeDiretor,
                nomesGeneros
        );
    }

    private void converterParaEntidade(FilmeRequestDTO dto, Filme filme) {
        filme.setTitulo(dto.titulo());
        filme.setSinopse(dto.sinopse());
        filme.setAnoLancamento(dto.anoLancamento());
        filme.setDuracaoMinutos(dto.duracaoMinutos());
        filme.setTipoRepresentatividade(dto.tipoRepresentatividade());
    }
}}