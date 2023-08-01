DROP DATABASE IF EXISTS mdd;
CREATE DATABASE mdd;
USE mdd;


CREATE TABLE IF NOT EXISTS `topics` (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL
);

INSERT INTO `topics` (id, name, description)
VALUES
  (1, 'Rust', 'Tout ce qui concerne la programmation en Rust.'),
  (2, 'C++', 'Tout ce qui concerne la programmation en C++.'),
  (3, 'VueJs', 'Tout ce qui concerne la programmation en VueJs.'),
  (4, 'PHP', 'Tout ce qui concerne la programmation en PHP .'),
  (5, '.Net', 'Tout ce qui concerne la programmation en .Net');