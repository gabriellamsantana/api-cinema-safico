package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Plataforma;
import br.com.cinemasafico.api.repository.PlataformaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlataformaService {

    @Autowired
    private PlataformaRepository repository;

    public Page<Plataforma> listarTodas(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Plataforma buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));
    }

    public Plataforma salvar(Plataforma plataforma) {
        return repository.save(plataforma);
    }

    public Plataforma atualizar(Long id, Plataforma plataformaAtualizada) {
        Plataforma existente = buscarPorId(id);
        existente.setNome(plataformaAtualizada.getNome());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        Plataforma plataforma = buscarPorId(id);
        repository.delete(plataforma);
    }
}