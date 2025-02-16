CREATE TABLE Usuario
(
	id_usuario INT NOT NULL,
  	nome_usuario VARCHAR(20) NOT NULL,
  	data_cadastro DATE NOT NULL,
	
  	PRIMARY KEY (id_usuario)
);

CREATE TABLE Jogo
(
	id_jogo INT NOT NULL,
  	nome_jogo VARCHAR(20) NOT NULL,
  	preco_jogo VARCHAR(10) NOT NULL,
  	genero VARCHAR(20),
  	desenvolvedora VARCHAR(20),
  	publicadora VARCHAR(20),
  	
  	PRIMARY KEY (id_jogo)
);

CREATE TABLE Avaliacao (
    id_avaliacao INT NOT NULL,
    comentario TEXT,
    classificacao INT,
  
  	PRIMARY KEY (id_avaliacao)
);

CREATE TABLE Expansao (
    id_expansao INT NOT NULL,
    id_jogo INT NOT NULL,
    nome_expansao VARCHAR(20) NOT NULL,
    preco_expansao VARCHAR(10) NOT NULL,
  
    FOREIGN KEY (id_jogo) REFERENCES Jogo(id_jogo)
);

CREATE TABLE Compra_Jogo (
    id_jogo INT NOT NULL,
    id_usuario INT NOT NULL,
    data_compra_jogo DATE NOT NULL,
  
    PRIMARY KEY (id_jogo, id_usuario),
    FOREIGN KEY (id_jogo) REFERENCES Jogo(id_jogo),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Compra_Expansao (
    id_expansao INT NOT NULL,
    id_usuario INT NOT NULL,
    data_compra_expansao DATE NOT NULL,
  
    PRIMARY KEY (id_expansao, id_usuario),
    FOREIGN KEY (id_expansao) REFERENCES Expansao(id_expansao),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Avalia (
    id_jogo INT NOT NULL,
    id_avaliacao INT NOT NULL,
    id_usuario INT NOT NULL,
    data_avaliacao DATE NOT NULL,
  
    PRIMARY KEY (id_jogo, id_avaliacao, id_usuario),
    FOREIGN KEY (id_jogo) REFERENCES Jogo(id_jogo),
    FOREIGN KEY (id_avaliacao) REFERENCES Avaliacao(id_avaliacao),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
);