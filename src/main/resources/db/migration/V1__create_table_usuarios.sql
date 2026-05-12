CREATE TABLE `usuarios` (
  `idusuarios` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cpf` varchar(14) NOT NULL COMMENT 'Cadastro de pessoa física',
  `email` varchar(255) NOT NULL COMMENT 'E-mail para acesso',
  `senha` varchar(255) NOT NULL,
  `data_nascimento` date NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`idusuarios`),
  KEY `cpf` (`cpf`),
  KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;