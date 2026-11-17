package br.com.cinemasafico.api.service;

import br.com.cinemasafico.api.model.Artista;
import br.com.cinemasafico.api.model.Filme;
import br.com.cinemasafico.api.model.Plataforma;
import br.com.cinemasafico.api.repository.FilmeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public Page<Filme> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Filme buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<Filme> buscarPorTitulo(String titulo, Pageable pageable) {
        return repository.findByTituloContainingIgnoreCase(titulo, pageable);
    }

    @Transactional
    public Filme salvar(Filme filme) {
        filme.setArtistas(vincularArtistasExistentes(filme.getArtistas()));
        filme.setPlataformas(vincularPlataformasExistentes(filme.getPlataformas()));
        if (filme.getAvaliacoes() == null) {
            filme.setAvaliacoes(new ArrayList<>());
        }
        return repository.save(filme);
    }

    @Transactional
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

    @Transactional
    public void deletar(Long id) {
        Filme filme = buscarPorId(id);
        repository.delete(filme);
    }

    private List<Artista> vincularArtistasExistentes(List<Artista> artistas) {
        List<Artista> gerenciados = new ArrayList<>();
        if (artistas != null) {
            for (Artista a : artistas) {
                if (a != null && a.getId() != null) {
                    Artista encontrado = entityManager.find(Artista.class, a.getId());
                    if (encontrado != null) {
                        gerenciados.add(encontrado);
                    }
                }
            }
        }
        return gerenciados;
    }

    private List<Plataforma> vincularPlataformasExistentes(List<Plataforma> plataformas) {
        List<Plataforma> gerenciadas = new ArrayList<>();
        if (plataformas != null) {
            for (Plataforma p : plataformas) {
                if (p != null && p.getId() != null) {
                    Plataforma encontrada = entityManager.find(Plataforma.class, p.getId());
                    if (encontrada != null) {
                        gerenciadas.add(encontrada);
                    }
                }
            }
        }
        return gerenciadas;
    }