INSERT INTO Usuario (id_usuario, nome_usuario, data_cadastro) VALUES
(1463, 'Batfreak6', '2015-06-12'),
(1201, 'SrJepetola', '2009-03-01'),
(1674, 'Gotorade', '2017-01-05'),
(1603, 'Vinizika', '2016-09-05'),
(2135, 'JPMax', '2020-03-03');

INSERT INTO Jogo (id_jogo, nome_jogo, preco_jogo, genero, desenvolvedora, publicadora) VALUES
(100, 'Dark souls : Remastered', 'R$154.90', 'RPG', 'FromSoftware/QLOC', 'Bandai Namco'),
(101, 'Dark souls 2', 'R$154.90', 'RPG', 'FromSoftware', 'Bandai Namco'),
(102, 'Dark souls 3', 'R$229.90', 'RPG', 'FromSoftware', 'Bandai Namco'),
(103, 'Elden Ring', 'R$229.90', 'RPG', 'FromSoftware', 'Bandai Namco'),
(104, 'Sekiro', 'R$199.99', 'Aventura', 'FromSoftware/Activision', 'FromSoftware'),
(105, 'Bloodborne','R$99.50', 'RPG', 'FromSoftware', 'Sony Computer Entertainment'),
(106, 'Counter Strike Source 2', 'Free', 'FPS', 'Valve', 'Valve'),
(107, 'Lego Star Wars The Complete Saga', 'R$599.99', 'Aventura', 'Traveller’s Tales', 'LucasArts/Disney'),
(108, 'Fortnite', 'Free', 'Battle Royale', 'Epic Games Studio', 'Epic Games'),
(109, 'Destiny 2', 'Free', 'MMORPG', 'Bungie', 'Bungie'),
(110, 'Overwatch 2', 'Free', 'FPS', 'Blizzard', 'Blizzard Entertainment'),
(111, 'Assassin’s Creed Unity', 'R$89.99', 'Ação', 'Ubisoft', 'Ubisoft'),
(112, 'Hollow Knight', 'R$46.99', 'Aventura', 'Team Cherry', 'Team Cherry'),
(113, 'Mana Spark', 'R$24.89', 'RPG', 'Kishimoto Studio', 'BEHEMUTT');

INSERT INTO Compra_Jogo (id_jogo, id_usuario, data_compra_jogo) VALUES
(112, 1463, '2023-08-20'),
(113, 1463, '2023-08-25'),
(103, 1463, '2023-09-26'),
(101, 1201, '2023-07-21'),
(104, 1201, '2023-09-03'),
(106, 1201, '2023-06-15'),
(108, 1674, '2023-04-01'),
(109, 1674, '2023-10-15'),
(110, 1603, '2023-03-15'),
(107, 1603, '2023-09-13'),
(111, 2135, '2023-01-04'),
(113, 2135, '2023-11-05'),
(110, 2135, '2023-03-11');

INSERT INTO Expansao (id_expansao, nome_expansao, preco_expansao, id_jogo) VALUES
(10, 'Artorias of the Abyss', 'R$30,00', 100),
(11, 'Crown of the Old Iron King', 'R$53,99', 101),
(12, 'The Ringed City', 'R$149,99', 102),
(13, 'Shadow of the Erdtree', 'R$199,99', 103),
(14, 'Shadows Died Once', 'R$99,99', 104),
(15, 'The Old Hunters', 'R$49,75', 105),
(16, 'Shadow Keep', 'R$89,99', 109),
(17, 'Witch Queen', 'R$109,99', 109),
(18, 'Beyond Light', 'R$109,99', 109),
(19, 'Light Fall', 'R$189,99', 110),
(20, 'Invasion', 'R$189,99', 110),
(21, 'Silk Song', 'Free', 112),
(22, 'Trupe Grimm', 'R$89,99', 112),
(23, 'Spark Return´s', 'R$159,99', 113);

INSERT INTO Compra_Expansao (id_expansao, id_usuario, data_compra_expansao) VALUES
(10, 1674, '2023-09-03'),
(11, 1463, '2023-09-05'),
(12, 1463, '2023-07-15'),
(13, 1201, '2023-05-09'),
(14, 1603, '2023-09-29'),
(15, 2135, '2023-08-15'),
(16, 2135, '2023-07-13'),
(17, 2135, '2023-10-15'),
(18, 2135, '2023-04-10'),
(19, 2135, '2023-07-05'),
(20, 2135, '2023-10-15'),
(21, 1603, '2023-08-25'),
(22, 2135, '2023-07-04'),
(23, 1201, '2023-03-03');

INSERT INTO Avaliacao (id_avaliacao, comentario, classificacao) VALUES
(1, 'Jogo muito bom parabéns!', 10),
(2, 'Jogo ótimo', 9),
(3, 'Pode melhorar', 6),
(4, 'Muito bom, porem com muitos bugs', 4),
(5, 'Péssimo!', 1),
(6, 'horrivel', 1),
(7, 'que jogo perfeito', 10),
(8, 'mataram o jogo com as atualizações', 3),
(9, 'muito difícil', 5);

INSERT INTO Avalia (id_jogo, id_avaliacao, id_usuario, data_avaliacao) VALUES
(100, 1, 1463, '2023-12-11'),
(101, 2, 2135, '2023-10-31'),
(102, 3, 1201, '2023-11-16'),
(103, 4, 1674, '2023-12-06'),
(104, 5, 1463, '2023-11-16'),
(105, 6, 1603, '2023-01-11'),
(106, 7, 2135, '2023-10-13'),
(107, 8, 1674, '2023-12-16'),
(108, 9, 1201, '2024-01-02');