INSERT INTO tb_ficha_tecnica (ano_lancamento, duracao_minutos) VALUES (2019, 122);
INSERT INTO tb_ficha_tecnica (ano_lancamento, duracao_minutos) VALUES (2015, 118);

INSERT INTO filmes (titulo, sinopse, ficha_tecnica_id) VALUES ('Retrato de uma Jovem em Chamas', 'Na Franca do seculo XVIII, uma pintora recebe a tarefa de fazer o retrato de casamento de uma jovem.', 1);
INSERT INTO filmes (titulo, sinopse, ficha_tecnica_id) VALUES ('Carol', 'Na Nova York dos anos 1950, uma jovem funcionaria de loja de departamentos se apaixona por uma mulher mais velha.', 2);

INSERT INTO tb_artista (nome, funcao) VALUES ('Celine Sciamma', 'DIRETOR');
INSERT INTO tb_artista (nome, funcao) VALUES ('Adele Haenel', 'ATOR');
INSERT INTO tb_artista (nome, funcao) VALUES ('Cate Blanchett', 'ATOR');

INSERT INTO tb_plataforma (nome) VALUES ('MUBI');
INSERT INTO tb_plataforma (nome) VALUES ('Amazon Prime Video');

INSERT INTO filme_artista (filme_id, artista_id) VALUES (1, 1);
INSERT INTO filme_artista (filme_id, artista_id) VALUES (1, 2);
INSERT INTO filme_artista (filme_id, artista_id) VALUES (2, 3);

INSERT INTO filme_plataforma (filme_id, plataforma_id) VALUES (1, 1);
INSERT INTO filme_plataforma (filme_id, plataforma_id) VALUES (2, 2);

INSERT INTO tb_avaliacao (nota, comentario, fonte, filme_id) VALUES (98, 'Obra-prima absoluta sobre o olhar feminino e a memoria.', 'Cahiers du Cinema', 1);
INSERT INTO tb_avaliacao (nota, comentario, fonte, filme_id) VALUES (94, 'Atuacoes impecaveis e fotografia marcante.', 'The Guardian', 2);