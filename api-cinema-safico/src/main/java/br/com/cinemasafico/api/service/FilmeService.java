package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Filme;
import br.com.cinemasafico.api.repository.FilmeRepository; // Esta é a linha que faltava!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    // Método para atualizar um filme existente
    public Filme atualizar(Long id, Filme filmeAtualizado) {
        // Primeiro, garantimos que o filme existe usando o método que já criámos
        Filme filmeExistente = buscarPorId(id);

        // Atualizamos apenas os campos permitidos
        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setSinopse(filmeAtualizado.getSinopse());

        // Atualiza a ficha técnica associada
        if (filmeAtualizado.getFichaTecnica() != null && filmeExistente.getFichaTecnica() != null) {
            filmeExistente.getFichaTecnica().setAnoLancamento(filmeAtualizado.getFichaTecnica().getAnoLancamento());
            filmeExistente.getFichaTecnica().setDuracaoMinutos(filmeAtualizado.getFichaTecnica().getDuracaoMinutos());
        }

        return repository.save(filmeExistente);
    }

    // Método para eliminar um filme
    public void deletar(Long id) {
        Filme filme = buscarPorId(id);
        repository.delete(filme);
    }
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