-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-12-2023 a las 00:17:35
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `todoeventos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `id` int(55) NOT NULL,
  `nomreunion` varchar(100) NOT NULL,
  `uids` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `asistencia`
--

INSERT INTO `asistencia` (`id`, `nomreunion`, `uids`) VALUES
(1, 'Reunión de prueba', '1234567890abcdef'),
(2, '123', '8oebElkgM6dsSFZSbKRiCfyCT7r1'),
(3, '123', '8oebElkgM6dsSFZSbKRiCfyCT7r1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos`
--

CREATE TABLE `eventos` (
  `id` int(55) NOT NULL,
  `uid` varchar(55) NOT NULL,
  `nomreunion` varchar(100) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `localizacion` varchar(100) NOT NULL,
  `tipo` varchar(4) NOT NULL,
  `ocupado` int(99) NOT NULL,
  `cupo` int(99) NOT NULL,
  `fecha` date NOT NULL,
  `hora` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `eventos`
--

INSERT INTO `eventos` (`id`, `uid`, `nomreunion`, `descripcion`, `localizacion`, `tipo`, `ocupado`, `cupo`, `fecha`, `hora`) VALUES
(1, '8oebElkgM6dsSFZSbKRiCfyCT7r1', 'Reunión de prueba', 'Esta es una descripción de prueba.', 'Ubicación de prueba', 'TP', 1, 50, '0000-00-00', '23:59');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(255) NOT NULL,
  `uid` varchar(255) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(25) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `evento` char(2) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `ciudad` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `uid`, `nombre`, `apellido`, `correo`, `evento`, `estado`, `ciudad`) VALUES
(1, '8oebElkgM6dsSFZSbKRiCfyCT7r1', 'Brian', 'Pina', '202060252@ucc.mx', 'Mo', 'Veracruz', 'Vera');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `id` int(55) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `eventos`
--
ALTER TABLE `eventos`
  MODIFY `id` int(55) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
