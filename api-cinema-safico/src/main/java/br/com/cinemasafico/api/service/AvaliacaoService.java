package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Avaliacao;
import br.com.cinemasafico.api.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository repository;

    public Page<Avaliacao> listarTodas(Pageable pageable) { return repository.findAll(pageable); }

    public Avaliacao buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    public Avaliacao salvar(Avaliacao avaliacao) { return repository.save(avaliacao); }

    public Avaliacao atualizar(Long id, Avaliacao atualizada) {
        Avaliacao existente = buscarPorId(id);
        existente.setFonte(atualizada.getFonte());
        existente.setNota(atualizada.getNota());
        existente.setComentario(atualizada.getComentario());
        return repository.save(existente);
    }

    public void deletar(Long id) { repository.delete(buscarPorId(id)); }
}