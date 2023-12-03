CREATE DATABESE BDSMAS;


CREATE TABLE Municipio(
	id SERIAL PRIMARY KEY,
	nome TEXT,
	uf VARCHAR(2)
);

CREATE TABLE Usuario(
	email TEXT PRIMARY KEY,
	nome TEXT,
	senha TEXT,
	idMunicipio INT,
	funcao INT DEFAULT 1,
	FOREIGN KEY (idMunicipio) REFERENCES Municipio(id)
);

CREATE TABLE Especie(
	comoCriar TEXT,
	comoCapturar TEXT,
	sobre TEXT,
	nome TEXT,
	id SERIAL PRIMARY KEY,
	emailUser TEXT,
	FOREIGN KEY (emailUser) REFERENCES Usuario(email)
);

CREATE TABLE Alerta(
	data TIMESTAMP,
	descricao TEXT,
	id SERIAL PRIMARY KEY,
	emailUsuario TEXT,
	idEspecie INT,
	FOREIGN KEY (emailUsuario) REFERENCES Usuario(email),
	FOREIGN KEY (idEspecie) REFERENCES Especie(id)
);

CREATE TABLE MunicipioEspecie(
	idMunicipio INT,
	idEspecie INT,
	PRIMARY KEY(idMunicipio, idEspecie),
	FOREIGN KEY (idMunicipio) REFERENCES Municipio(id),
	FOREIGN KEY (idEspecie) REFERENCES Especie(id)
);

SELECT current_database();