package com.saficofilmes.api.service;

import com.saficofilmes.api.dto.ArtistaRequestDTO;
import com.saficofilmes.api.dto.ArtistaResponseDTO;
import com.saficofilmes.api.model.Artista;
import com.saficofilmes.api.repository.ArtistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @Transactional(readOnly = true)
    public Page<ArtistaResponseDTO> listarTodos(Pageable pageable) {
        return artistaRepository.findAll(pageable).map(this::converterParaResponseDTO);
    }

    @Transactional(readOnly = true)
    public ArtistaResponseDTO buscarPorId(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista não encontrado com o ID: " + id));
        return converterParaResponseDTO(artista);
    }

    @Transactional
    public ArtistaResponseDTO salvar(ArtistaRequestDTO dto) {
        Artista artista = new Artista();
        converterParaEntidade(dto, artista);
        artista = artistaRepository.save(artista);
        return converterParaResponseDTO(artista);
    }

    @Transactional
    public ArtistaResponseDTO atualizar(Long id, ArtistaRequestDTO dto) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista não encontrado com o ID: " + id));

        converterParaEntidade(dto, artista);
        artista = artistaRepository.save(artista);
        return converterParaResponseDTO(artista);
    }

    @Transactional
    public void deletar(Long id) {
        Artista artista = artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista não encontrado com o ID: " + id));
        artistaRepository.delete(artista);
    }

    private ArtistaResponseDTO converterParaResponseDTO(Artista artista) {
        return new ArtistaResponseDTO(
                artista.getId(),
                artista.getNome(),
                artista.getDataNascimento(),
                artista.getNacionalidade(),
                artista.getBiografia()
        );
    }

    private void converterParaEntidade(ArtistaRequestDTO dto, Artista artista) {
        artista.setNome(dto.nome());
        artista.setDataNascimento(dto.dataNascimento());
        artista.setNacionalidade(dto.nacionalidade());
        artista.setBiografia(dto.biografia());
    }
}
