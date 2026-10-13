package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Artista;
import br.com.cinemasafico.api.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository repository;

    public Page<Artista> listarTodos(Pageable pageable) { return repository.findAll(pageable); }

    public Artista buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Artista não encontrado"));
    }

    public Artista salvar(Artista artista) { return repository.save(artista); }

    public Artista atualizar(Long id, Artista atualizado) {
        Artista existente = buscarPorId(id);
        existente.setNome(atualizado.getNome());
        existente.setFuncao(atualizado.getFuncao());
        return repository.save(existente);
    }

    public void deletar(Long id) { repository.delete(buscarPorId(id)); }
}
