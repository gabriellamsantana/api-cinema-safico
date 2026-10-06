package com.saficofilmes.api.service;

import com.saficofilmes.api.dto.AvaliacaoRequestDTO;
import com.saficofilmes.api.dto.AvaliacaoResponseDTO;
import com.saficofilmes.api.model.Avaliacao;
import com.saficofilmes.api.model.Filme;
import com.saficofilmes.api.repository.AvaliacaoRepository;
import com.saficofilmes.api.repository.FilmeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final FilmeRepository filmeRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, FilmeRepository filmeRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.filmeRepository = filmeRepository;
    }

    @Transactional(readOnly = true)
    public Page<AvaliacaoResponseDTO> listarTodos(Pageable pageable) {
        return avaliacaoRepository.findAll(pageable).map(this::converterParaResponseDTO);
    }

    @Transactional(readOnly = true)
    public AvaliacaoResponseDTO buscarPorId(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o ID: " + id));
        return converterParaResponseDTO(avaliacao);
    }

    @Transactional
    public AvaliacaoResponseDTO salvar(AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = new Avaliacao();
        converterParaEntidade(dto, avaliacao);
        avaliacao = avaliacaoRepository.save(avaliacao);
        return converterParaResponseDTO(avaliacao);
    }

    @Transactional
    public AvaliacaoResponseDTO atualizar(Long id, AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o ID: " + id));

        converterParaEntidade(dto, avaliacao);
        avaliacao = avaliacaoRepository.save(avaliacao);
        return converterParaResponseDTO(avaliacao);
    }

    @Transactional
    public void deletar(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada com o ID: " + id));
        avaliacaoRepository.delete(avaliacao);
    }

    private AvaliacaoResponseDTO converterParaResponseDTO(Avaliacao avaliacao) {
        Long filmeId = (avaliacao.getFilme() != null) ? avaliacao.getFilme().getId() : null;
        String filmeTitulo = (avaliacao.getFilme() != null) ? avaliacao.getFilme().getTitulo() : null;

        return new AvaliacaoResponseDTO(
                avaliacao.getId(),
                avaliacao.getFonte(),
                avaliacao.getPontuacao(),
                avaliacao.getConsensoCritica(),
                avaliacao.getUrlOrigem(),
                filmeId,
                filmeTitulo
        );
    }

    private void converterParaEntidade(AvaliacaoRequestDTO dto, Avaliacao avaliacao) {
        avaliacao.setFonte(dto.fonte());
        avaliacao.setPontuacao(dto.pontuacao());
        avaliacao.setConsensoCritica(dto.consensoCritica());
        avaliacao.setUrlOrigem(dto.urlOrigem());

        Filme filme = filmeRepository.findById(dto.filmeId())
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com o ID: " + dto.filmeId()));
        avaliacao.setFilme(filme);
    }
}
