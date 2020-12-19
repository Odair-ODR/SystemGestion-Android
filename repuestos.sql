-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-05-2019 a las 21:59:28
-- Versión del servidor: 10.1.16-MariaDB
-- Versión de PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `repuestos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `password` varchar(60) NOT NULL,
  `nivel` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `admins`
--

INSERT INTO `admins` (`id`, `usuario`, `nombre`, `password`, `nivel`) VALUES
(1, 'admin', 'Enderson', 'admin', 1),
(2, 'woody', 'Woody', '10482209', 0),
(3, 'Nuevo', 'Juan', '$2y$12$Gmky52KDUgtEPwKUG0GvDeC8PqNJ/QSeFsy6JYbmHzoapKAmeE6ra', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asociados`
--

CREATE TABLE `asociados` (
  `id_asociado` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `informacion` text NOT NULL,
  `imagen` text NOT NULL,
  `editado` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asociados`
--

INSERT INTO `asociados` (`id_asociado`, `nombre`, `informacion`, `imagen`, `editado`) VALUES
(1, 'NCKasociado', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam molestie placerat erat. Praesent vitae quam mi. Donec porta, ipsum a rhoncus aliquam, eros ipsum auctor ante, nec eleifend libero lorem id augue. Pellentesque eget nulla ipsum. Pellentesque habitant morbi tristique senectus et netus', 'descarga.jpg', '0000-00-00 00:00:00'),
(2, 'VERtsoaciado', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam molestie placerat erat. Praesent vitae quam mi. Donec porta, ipsum a rhoncus aliquam, eros ipsum auctor ante, nec eleifend libero lorem id augue. Pellentesque eget nulla ipsum. Pellentesque habitant morbi tristique senectus et netus', 'asociados-aneproma.jpg', '0000-00-00 00:00:00'),
(3, 'Rmhsociado', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam molestie placerat erat. Praesent vitae quam mi. Donec porta, ipsum a rhoncus aliquam, eros ipsum auctor ante, nec eleifend libero lorem id augue. Pellentesque eget nulla ipsum. Pellentesque habitant morbi tristique senectus et netus', 'descarga(1).jpg', '0000-00-00 00:00:00'),
(4, 'Rojas asociados', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam molestie placerat erat. Praesent vitae quam mi. Donec porta, ipsum a rhoncus aliquam, eros ipsum auctor ante, nec eleifend libero lorem id augue. Pellentesque eget nulla ipsum. Pellentesque habitant morbi tristique senectus et netus', 'descarga.png', '0000-00-00 00:00:00'),
(5, 'Rojas asociados', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam molestie placerat erat. Praesent vitae quam mi. Donec porta, ipsum a rhoncus aliquam, eros ipsum auctor ante, nec eleifend libero lorem id augue. Pellentesque eget nulla ipsum. Pellentesque habitant morbi tristique senectus et netus', 'descarga.png', '0000-00-00 00:00:00'),
(6, 'VERtsoaciado', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam molestie placerat erat. Praesent vitae quam mi. Donec porta, ipsum a rhoncus aliquam, eros ipsum auctor ante, nec eleifend libero lorem id augue. Pellentesque eget nulla ipsum. Pellentesque habitant morbi tristique senectus et netus', 'asociados-aneproma.jpg', '0000-00-00 00:00:00'),
(7, 'Nuevo', ' Nuevo asociado 			 							 ', 'CBPERU.jpg', '2019-04-30 11:15:12');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagenes`
--

CREATE TABLE `imagenes` (
  `id_imagen` int(11) NOT NULL,
  `tipo_imagen` int(2) NOT NULL,
  `url_imagen` text NOT NULL,
  `referencia_tipo_nombre` varchar(60) NOT NULL,
  `editado` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='razon es colocar ref a las img en la pag leer ref_tip_nom.';

--
-- Volcado de datos para la tabla `imagenes`
--

INSERT INTO `imagenes` (`id_imagen`, `tipo_imagen`, `url_imagen`, `referencia_tipo_nombre`, `editado`) VALUES
(2, 3, 'CARRO.png', '', '2019-04-25 16:15:23'),
(5, 1, 'CARRO.png', '', '2019-04-24 23:41:56'),
(6, 2, 'sonax-logo.png', '', '0000-00-00 00:00:00'),
(7, 2, 'hyundai-logo.png', '', '0000-00-00 00:00:00'),
(8, 2, '1.jpg', '', '2019-04-24 23:45:11'),
(9, 2, 'gedore-logo.png', '', '0000-00-00 00:00:00'),
(10, 2, 'logo-fm.png', '', '0000-00-00 00:00:00'),
(11, 2, 'hunter-logo.png', '', '0000-00-00 00:00:00'),
(12, 2, 'imag-sondas-lambda-bosch.jpg', '', '0000-00-00 00:00:00'),
(13, 1, '57414160_808022999590789_3282354349954564096_n.jpg', '', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marcas`
--

CREATE TABLE `marcas` (
  `id_marca` int(11) NOT NULL,
  `nombre_marca` varchar(100) NOT NULL,
  `img` text NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `editado` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `marcas`
--

INSERT INTO `marcas` (`id_marca`, `nombre_marca`, `img`, `tipo`, `editado`) VALUES
(1, 'Nombre marca', 'banner-carros.jpg', 'impulso', '2019-05-02 15:53:18'),
(2, 'Nueva marca Xx', 'WhatsApp Image 2019-04-30 at 11.01.13.jpeg', 'asociado', '0000-00-00 00:00:00'),
(3, 'Nueva marca X', 'WhatsApp Image 2019-04-30 at 10.57.18.jpeg', 'apoyo', '2019-05-02 15:55:16');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `noticias`
--

CREATE TABLE `noticias` (
  `id_noticia` int(11) NOT NULL,
  `titulo_noticia` varchar(100) NOT NULL,
  `url_noticia` text NOT NULL,
  `imagen_noticia` text NOT NULL,
  `id_tipo` int(11) NOT NULL,
  `editado` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `noticias`
--

INSERT INTO `noticias` (`id_noticia`, `titulo_noticia`, `url_noticia`, `imagen_noticia`, `id_tipo`, `editado`) VALUES
(2, 'Este texto debe ser lo suficientemente grande para que abarque todo el div, de forma que se vea igua', '', 'imag-baterias.jpg', 2, '2019-04-26 10:37:26'),
(3, 'Este texto debe ser lo suficientemente grande para que abarque todo el div, de forma que se vea igua', '', 'imag-cables-de-encendido-bosch.jpg', 3, '2019-04-26 10:37:38'),
(4, 'Este texto debe ser lo suficientemente grande para que abarque todo el div, de forma que se vea igua', '', 'imag-bujias.jpg', 4, '2019-04-26 10:35:26'),
(5, 'Este texto debe ser lo suficientemente grande para que abarque todo el div, de forma que se vea igua', '', '4.jpg', 5, '2019-04-26 10:36:30'),
(7, 'Este texto debe ser lo suficientemente grande para que abarque todo el div, de forma que se vea igua', '', 'QUIEREME.jpg', 1, '2019-04-26 10:37:11');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `texto`
--

CREATE TABLE `texto` (
  `id_texto` int(11) NOT NULL,
  `Texto` text NOT NULL,
  `tipo` text NOT NULL,
  `editado` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `texto`
--

INSERT INTO `texto` (`id_texto`, `Texto`, `tipo`, `editado`) VALUES
(1, 'Somos parte de la solución, somos parte de ti.', 'frase_header', '0000-00-00 00:00:00'),
(2, 'Empresas Asociadas.', 'div_derecho_contendo_central', '2019-04-29 15:33:36'),
(3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam semper lorem at eros congue malesuada. Duis ac fermentum quam, et sollicitudin tellusQuisque a tortor erat. Donec euismod eros et purus lobortis, a volutpat est accumsan.\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam semper lorem at eros congue malesuada. Duis ac fermentum quam, et sollicitudin tellusQuisque a tortor erat. Donec euismod eros et purus lobortis, a volutpat est accumsan.\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam semper lorem at eros congue malesuada. Duis ac fermentum quam, et sollicitudin tellusQuisque a tortor erat. Donec euismod eros et purus lobortis, a volutpat est accumsan.Quisque ultricies arcu eget est gravida venenatis. Cras mollis est in justo interdum tempus.', 'descripcion_empresa', '0000-00-00 00:00:00'),
(4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam semper lorem at eros congue malesuada. Duis ac fermentum quam, et sollicitudin tellusQuisque a tortor erat. Donec euismod eros et purus lobortis, a volutpat est accumsan.', 'mision', '0000-00-00 00:00:00'),
(5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam semper lorem at eros congue malesuada. Duis ac fermentum quam, et sollicitudin tellusQuisque a tortor erat. Donec euismod eros et purus lobortis, a volutpat est accumsan.\r\n\r\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam semper lorem at eros congue malesuada. Duis ac fermentum quam, et sollicitudin tellusQuisque a tortor erat. Donec euismod eros et purus lobortis, a volutpat est accumsan.Quisque ultricies arcu eget est gravida venenatis. Cras mollis est in justo interdum tempus.', 'vision', '0000-00-00 00:00:00'),
(6, 'La empresa peruana S.A. es una empresa multinacional cuyas operaciones atiende a todos los sectores productivos del país.', 'titulo_empresa', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo`
--

CREATE TABLE `tipo` (
  `id` int(11) NOT NULL,
  `tipo_noticia` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipo`
--

INSERT INTO `tipo` (`id`, `tipo_noticia`) VALUES
(1, 'Repuestos para Autos'),
(2, 'Empresa Peruana'),
(3, 'Brochure de Productos'),
(4, 'Nueva'),
(5, 'Nueva'),
(9, 'Tipo nuevo');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `asociados`
--
ALTER TABLE `asociados`
  ADD PRIMARY KEY (`id_asociado`);

--
-- Indices de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD PRIMARY KEY (`id_imagen`);

--
-- Indices de la tabla `marcas`
--
ALTER TABLE `marcas`
  ADD PRIMARY KEY (`id_marca`);

--
-- Indices de la tabla `noticias`
--
ALTER TABLE `noticias`
  ADD PRIMARY KEY (`id_noticia`),
  ADD KEY `id_tipo` (`id_tipo`);

--
-- Indices de la tabla `texto`
--
ALTER TABLE `texto`
  ADD PRIMARY KEY (`id_texto`);

--
-- Indices de la tabla `tipo`
--
ALTER TABLE `tipo`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `asociados`
--
ALTER TABLE `asociados`
  MODIFY `id_asociado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  MODIFY `id_imagen` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT de la tabla `marcas`
--
ALTER TABLE `marcas`
  MODIFY `id_marca` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `noticias`
--
ALTER TABLE `noticias`
  MODIFY `id_noticia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `texto`
--
ALTER TABLE `texto`
  MODIFY `id_texto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `tipo`
--
ALTER TABLE `tipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `noticias`
--
ALTER TABLE `noticias`
  ADD CONSTRAINT `noticias_ibfk_1` FOREIGN KEY (`id_tipo`) REFERENCES `tipo` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
