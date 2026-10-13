package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Filme;
import br.com.cinemasafico.api.repository.FilmeRepository; // Esta é a linha que faltava!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public Page<Filme> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Filme buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Filme não encontrado"));
    }

    public Filme salvar(Filme filme) {
        return repository.save(filme);
    }
}