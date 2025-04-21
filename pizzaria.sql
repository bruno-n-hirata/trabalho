# DDL (Criação de tabelas)

CREATE DATABASE IF NOT EXISTS pizzaria;
USE pizzaria;

CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)
);

CREATE TABLE pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE pizza (
    id_pizza INT AUTO_INCREMENT PRIMARY KEY,
    tamanho VARCHAR(10) NOT NULL,
    id_pedido INT NOT NULL,
    preco DECIMAL(10,2),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
);

CREATE TABLE sabor (
    id_sabor INT AUTO_INCREMENT PRIMARY KEY,
    nome_sabor VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE pizza_sabor (
    id_pizza INT NOT NULL,
    id_sabor INT NOT NULL,
    PRIMARY KEY (id_pizza, id_sabor),
    FOREIGN KEY (id_pizza) REFERENCES pizza(id_pizza),
    FOREIGN KEY (id_sabor) REFERENCES sabor(id_sabor)
);

# DML (Inserção, Atualização e Remoção)

INSERT INTO cliente (nome, telefone)
VALUES
    ('João Silva', '999999999'),
    ('Maria Oliveira', '988888888');

INSERT INTO pedido (data, id_cliente)
VALUES
    ('2025-04-13', 1),
    ('2025-04-14', 2);

INSERT INTO pizza (tamanho, id_pedido, preco)
VALUES
    ('Grande', 1, 25.00),
    ('Média', 1, 20.00),
    ('Pequena', 2, 15.00);

INSERT INTO sabor (nome_sabor, preco)
VALUES
    ('Calabresa', 35.00),
    ('Frango com Catupiry', 40.00),
    ('Marguerita', 30.00);

INSERT INTO pizza_sabor (id_pizza, id_sabor)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 2),
    (3, 3);

UPDATE sabor
SET nome_sabor = 'Frango Supremo'
WHERE id_sabor = 2;

DELETE FROM pizza_sabor
WHERE id_pizza = 3;

DELETE FROM pizza
WHERE id_pizza = 3;

# SELECT (Consultas básicas + operadores)

SELECT
    nome_sabor,
    preco * 0.9 AS preco_com_desconto
FROM sabor;

SELECT
    p.id_pizza,
    p.tamanho,
    s.nome_sabor
FROM pizza p
JOIN pizza_sabor ps ON p.id_pizza = ps.id_pizza
JOIN sabor s ON ps.id_sabor = s.id_sabor;

SELECT
    c.nome AS nome_cliente,
    ped.data AS data_pedido
FROM pedido ped
JOIN cliente c ON ped.id_cliente = c.id_cliente;

SELECT
    p.id_pizza,
    p.tamanho,
    SUM(s.preco) AS total_sabores
FROM pizza p
JOIN pizza_sabor ps ON p.id_pizza = ps.id_pizza
JOIN sabor s ON ps.id_sabor = s.id_sabor
GROUP BY p.id_pizza, p.tamanho;

SELECT
    nome_sabor,
    preco
FROM sabor
WHERE preco BETWEEN 30 AND 40;

SELECT
    nome_sabor,
    preco
FROM sabor
WHERE nome_sabor LIKE '%Frango%';

# Funções de Agregação e Agrupamento

SELECT
    c.nome AS nome_cliente,
    COUNT(ped.id_pedido) AS total_pedidos
FROM cliente c
LEFT JOIN pedido ped ON c.id_cliente = ped.id_cliente
GROUP BY c.id_cliente, c.nome;

SELECT
    AVG(preco) AS media_preco_sabores
FROM sabor;

SELECT
    ped.id_pedido,
    SUM(s.preco) AS total_sabores
FROM pedido ped
JOIN pizza pi ON ped.id_pedido = pi.id_pedido
JOIN pizza_sabor ps ON pi.id_pizza = ps.id_pizza
JOIN sabor s ON ps.id_sabor = s.id_sabor
GROUP BY ped.id_pedido;

SELECT
    tamanho,
    COUNT(*) AS total_pizzas
FROM pizza
GROUP BY tamanho;

SELECT *
FROM (
    SELECT
        ped.id_pedido,
        SUM(s.preco) AS total_sabores
    FROM pedido ped
    JOIN pizza pi ON ped.id_pedido = pi.id_pedido
    JOIN pizza_sabor ps ON pi.id_pizza = ps.id_pizza
    JOIN sabor s ON ps.id_sabor = s.id_sabor
    GROUP BY ped.id_pedido
) AS sub
WHERE sub.total_sabores > 50;
