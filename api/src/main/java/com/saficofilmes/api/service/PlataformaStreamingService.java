package com.saficofilmes.api.service;

import com.saficofilmes.api.dto.PlataformaStreamingRequestDTO;
import com.saficofilmes.api.dto.PlataformaStreamingResponseDTO;
import com.saficofilmes.api.model.PlataformaStreaming;
import com.saficofilmes.api.repository.PlataformaStreamingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlataformaStreamingService {

    private final PlataformaStreamingRepository plataformaRepository;

    public PlataformaStreamingService(PlataformaStreamingRepository plataformaRepository) {
        this.plataformaRepository = plataformaRepository;
    }

    @Transactional(readOnly = true)
    public Page<PlataformaStreamingResponseDTO> listarTodos(Pageable pageable) {
        return plataformaRepository.findAll(pageable).map(this::converterParaResponseDTO);
    }

    @Transactional(readOnly = true)
    public PlataformaStreamingResponseDTO buscarPorId(Long id) {
        PlataformaStreaming plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada com o ID: " + id));
        return converterParaResponseDTO(plataforma);
    }

    @Transactional
    public PlataformaStreamingResponseDTO salvar(PlataformaStreamingRequestDTO dto) {
        PlataformaStreaming plataforma = new PlataformaStreaming();
        converterParaEntidade(dto, plataforma);
        plataforma = plataformaRepository.save(plataforma);
        return converterParaResponseDTO(plataforma);
    }

    @Transactional
    public PlataformaStreamingResponseDTO atualizar(Long id, PlataformaStreamingRequestDTO dto) {
        PlataformaStreaming plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada com o ID: " + id));

        converterParaEntidade(dto, plataforma);
        plataforma = plataformaRepository.save(plataforma);
        return converterParaResponseDTO(plataforma);
    }

    @Transactional
    public void deletar(Long id) {
        PlataformaStreaming plataforma = plataformaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada com o ID: " + id));
        plataformaRepository.delete(plataforma);
    }

    private PlataformaStreamingResponseDTO converterParaResponseDTO(PlataformaStreaming plataforma) {
        return new PlataformaStreamingResponseDTO(
                plataforma.getId(),
                plataforma.getNome(),
                plataforma.getUrlBase(),
                plataforma.getRequerAssinatura()
        );
    }

    private void converterParaEntidade(PlataformaStreamingRequestDTO dto, PlataformaStreaming plataforma) {
        plataforma.setNome(dto.nome());
        plataforma.setUrlBase(dto.urlBase());
        plataforma.setRequerAssinatura(dto.requerAssinatura());
    }
}