package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Artista;
import br.com.cinemasafico.api.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository repository;

    public Page<Artista> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Artista buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artista não encontrada"));
    }

    public Page<Artista> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Artista salvar(Artista artista) {
        return repository.save(artista);
    }

    public Artista atualizar(Long id, Artista atualizado) {
        Artista existente = buscarPorId(id);
        existente.setNome(atualizado.getNome());
        existente.setFuncao(atualizado.getFuncao());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        Artista artista = buscarPorId(id);
        repository.delete(artista);
    }
}
