package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Filme;
import br.com.cinemasafico.api.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public Page<Filme> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Filme buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado"));
    }

    public Page<Filme> buscarPorTitulo(String titulo, Pageable pageable) {
        return repository.findByTituloContainingIgnoreCase(titulo, pageable);
    }

    public Filme salvar(Filme filme) {
        return repository.save(filme);
    }

    public Filme atualizar(Long id, Filme filmeAtualizado) {
        Filme filmeExistente = buscarPorId(id);
        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setSinopse(filmeAtualizado.getSinopse());

        if (filmeAtualizado.getFichaTecnica() != null && filmeExistente.getFichaTecnica() != null) {
            filmeExistente.getFichaTecnica().setAnoLancamento(filmeAtualizado.getFichaTecnica().getAnoLancamento());
            filmeExistente.getFichaTecnica().setDuracaoMinutos(filmeAtualizado.getFichaTecnica().getDuracaoMinutos());
        }
        return repository.save(filmeExistente);
    }

    public void deletar(Long id) {
        Filme filme = buscarPorId(id);
        repository.delete(filme);
    }
}