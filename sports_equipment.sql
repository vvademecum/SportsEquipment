-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Ноя 02 2022 г., 11:36
-- Версия сервера: 8.0.30
-- Версия PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `sports_equipment`
--

-- --------------------------------------------------------

--
-- Структура таблицы `characteristics`
--

CREATE TABLE `characteristics` (
  `id` bigint NOT NULL,
  `guarantee_period` varchar(255) DEFAULT NULL,
  `country_id` bigint DEFAULT NULL,
  `gender_id` bigint DEFAULT NULL,
  `kind_of_sport_id` bigint DEFAULT NULL,
  `season_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `characteristics`
--

INSERT INTO `characteristics` (`id`, `guarantee_period`, `country_id`, `gender_id`, `kind_of_sport_id`, `season_id`) VALUES
(33, 'Отсутствует', 0, 3, 3, 4),
(45, 'Отсутствует', 6, 2, 1, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `client`
--

CREATE TABLE `client` (
  `id` bigint NOT NULL,
  `login` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `patronymic` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `contacts_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `client`
--

INSERT INTO `client` (`id`, `login`, `name`, `password`, `patronymic`, `surname`, `contacts_id`) VALUES
(3, 'client', 'Николай', '$2a$10$KlB62rbNoQmCdyM/xBn3VuulIin56xS6A7VxkA/OQ6B772Jo.P6PK', 'Валерьевич', 'Кабанов', NULL),
(8, 'sdafgasdfg', 'Nikolay', '$2a$10$Avj1FjkNGrWrXigh8NRmwO41/YDU/ppoa5EbHA4I/rIOPVhoIknMq', 'sdfgsdfg', 'Kabanov', 18),
(51, 'admin', 'Валентин', '$2a$10$KLE3eIgnU/4JXPh22XKo9e3WUCdeBtY/58pKqglhOlWmWo713lveG', 'Иванович', 'Василкин', NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `contacts`
--

CREATE TABLE `contacts` (
  `id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `contacts`
--

INSERT INTO `contacts` (`id`, `address`, `email`, `phone_number`) VALUES
(9, '1231', 'sdfgsdfg', 'sdfgsdfg'),
(11, 'улица Сокольнический Вал, 50к2', 'lolshtokek@gmail.com', '89271807922'),
(12, 'rtyu', 'thuik', '234523452345'),
(15, 'asdfasdf', '1212', 'asdfasdf'),
(16, 'фыва', 'фыва', 'фыва'),
(18, 'улица Сокольнический Вал, 50к2', 'lolshtokek@gmail.com', '89271807922'),
(197, 'sdgadgdg', 'lolshtokek@gmail.com', '+79271807922'),
(200, '50/2dfgasd', 'lolshtokek@gmail.com', '+79271807922');

-- --------------------------------------------------------

--
-- Структура таблицы `country`
--

CREATE TABLE `country` (
  `id` bigint NOT NULL,
  `country` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `country`
--

INSERT INTO `country` (`id`, `country`) VALUES
(0, 'Китай'),
(1, 'Тайвань'),
(2, 'Бангладеш'),
(3, 'Россия'),
(4, 'США'),
(5, 'Индия'),
(6, 'Афганистан');

-- --------------------------------------------------------

--
-- Структура таблицы `employee`
--

CREATE TABLE `employee` (
  `id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `login` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number_passport` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `patronymic` varchar(255) DEFAULT NULL,
  `series_passport` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `role_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `employee`
--

INSERT INTO `employee` (`id`, `email`, `login`, `name`, `number_passport`, `password`, `patronymic`, `series_passport`, `surname`, `role_id`) VALUES
(7, 'lolshtokek@gmail.com', 'cashier', 'Николай', 'asdf', '$2a$10$8z5jNRIDacha0xO/rqRXzuxaGId4I5sFcJeIBeDO9Nz/L9J6HzO4e', 'Валерьевич', 'sadf', 'Кабанов', 1),
(17, 'sdfgsdfg', 'haha', 'sdfgsdfg', 'sdfgsdfg', '$2a$10$TOZ4gwIFSg5hxRV.beaRN.hX4/m/QzNQRBLkZ6e6Sy3niRMt8RzCq', 'sdfgsdfg', 'sdfgsdfg', 'sdfgsdfg', 0),
(52, 'gorev@gmail.com', 'admin', 'Илья', '232323', '$2a$10$KuYkTlDxdBjxDELIqzB7DebgjXSThkPgvd9JQoM5TjIRP1B9ztd36', 'Александрович', '1212', 'Горев', 0),
(187, 'lolshtokek@gmail.com', '...............', '.............', ',,,,,,', '$2a$10$LWyHG1m1jvMv5X/rBozLCemzVkfpuV3KqkyX8SIRIVOjdi/rHgq8m', ',,', ',,,,', '..............', 0);

-- --------------------------------------------------------

--
-- Структура таблицы `gender`
--

CREATE TABLE `gender` (
  `id` bigint NOT NULL,
  `gender` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `gender`
--

INSERT INTO `gender` (`id`, `gender`) VALUES
(0, 'Женский'),
(1, 'Мужской'),
(2, 'ATTACK HELICOPTER'),
(3, 'Общий');

-- --------------------------------------------------------

--
-- Структура таблицы `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(201);

-- --------------------------------------------------------

--
-- Структура таблицы `kind_of_sport`
--

CREATE TABLE `kind_of_sport` (
  `id` bigint NOT NULL,
  `kind_of_sport` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `kind_of_sport`
--

INSERT INTO `kind_of_sport` (`id`, `kind_of_sport`) VALUES
(0, 'Дзюдо'),
(1, 'Кёрлинг'),
(2, 'Легкая атлетика'),
(3, 'Настольный теннис');

-- --------------------------------------------------------

--
-- Структура таблицы `product`
--

CREATE TABLE `product` (
  `id` bigint NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `cost` double NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `quantity_in_stock` int NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `characteristics_id` bigint DEFAULT NULL,
  `provider_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `product`
--

INSERT INTO `product` (`id`, `category`, `cost`, `description`, `quantity_in_stock`, `surname`, `characteristics_id`, `provider_id`, `name`) VALUES
(34, 'Мячи', 20, 'мячик B)', 10, NULL, 33, 14, 'Мяч теннисный'),
(46, 'Булочки', 0, 'много мака.', 10, NULL, 45, 14, 'Булочка с маком');

-- --------------------------------------------------------

--
-- Структура таблицы `product_cheque`
--

CREATE TABLE `product_cheque` (
  `id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `cheque_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `product_cheque`
--

INSERT INTO `product_cheque` (`id`, `quantity`, `cheque_id`, `product_id`) VALUES
(160, 1, 151, 34);

-- --------------------------------------------------------

--
-- Структура таблицы `provider`
--

CREATE TABLE `provider` (
  `id` bigint NOT NULL,
  `contacts_id` bigint DEFAULT NULL,
  `org_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `provider`
--

INSERT INTO `provider` (`id`, `contacts_id`, `org_name`) VALUES
(14, 197, 'АХХАХАХАХАХАХАХ'),
(198, NULL, 'dfgdfg'),
(199, 200, 'asdfasdfa');

-- --------------------------------------------------------

--
-- Структура таблицы `role`
--

CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `role`
--

INSERT INTO `role` (`id`, `role`) VALUES
(0, 'ADMIN'),
(1, 'CASHIER'),
(2, 'HR'),
(3, 'STOREKEEPER');

-- --------------------------------------------------------

--
-- Структура таблицы `season`
--

CREATE TABLE `season` (
  `id` bigint NOT NULL,
  `season` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `season`
--

INSERT INTO `season` (`id`, `season`) VALUES
(0, 'Лето'),
(1, 'Зима'),
(2, 'Весна'),
(3, 'Осень'),
(4, 'Демисезонный');

-- --------------------------------------------------------

--
-- Структура таблицы `сheque`
--

CREATE TABLE `сheque` (
  `id` bigint NOT NULL,
  `date_of_purchase` datetime(6) DEFAULT NULL,
  `client_id` bigint DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Дамп данных таблицы `сheque`
--

INSERT INTO `сheque` (`id`, `date_of_purchase`, `client_id`, `employee_id`) VALUES
(142, '2022-11-02 00:00:00.000000', 3, 7),
(151, '2022-11-01 00:00:00.000000', 8, 17),
(156, '2022-11-01 00:00:00.000000', 51, 52);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `characteristics`
--
ALTER TABLE `characteristics`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKhcsd04inixe7x9docaq4f4g4p` (`country_id`),
  ADD KEY `FKffs3l3e4edep4v495qg51lh05` (`gender_id`),
  ADD KEY `FKk4wyrmwmqdhjjbw8gb9ysr5tr` (`kind_of_sport_id`),
  ADD KEY `FKpbtc940xb84l0a4p6vfu7qpik` (`season_id`);

--
-- Индексы таблицы `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK88s2aajxktwxt6mmqt6hftpu0` (`contacts_id`);

--
-- Индексы таблицы `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3046kvjyysq288vy3lsbtc9nw` (`role_id`);

--
-- Индексы таблицы `gender`
--
ALTER TABLE `gender`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `kind_of_sport`
--
ALTER TABLE `kind_of_sport`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlcyr7t24uocpkd45tqpjixdx9` (`characteristics_id`),
  ADD KEY `FKfmt6vehie8ep9pq0nnvv9een7` (`provider_id`);

--
-- Индексы таблицы `product_cheque`
--
ALTER TABLE `product_cheque`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKaq3cswrkfcs7d80q8g2089qpa` (`cheque_id`),
  ADD KEY `FKf8dsqehwxj2hiixrmjym693u9` (`product_id`);

--
-- Индексы таблицы `provider`
--
ALTER TABLE `provider`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgxp9qgrdjilovvomwvgecv9al` (`contacts_id`);

--
-- Индексы таблицы `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `season`
--
ALTER TABLE `season`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `сheque`
--
ALTER TABLE `сheque`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK64t74x1jthh4d8j16lu6md2hg` (`client_id`),
  ADD KEY `FKa96w800ks90lcb1t7sqedlcij` (`employee_id`);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `characteristics`
--
ALTER TABLE `characteristics`
  ADD CONSTRAINT `FKffs3l3e4edep4v495qg51lh05` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  ADD CONSTRAINT `FKhcsd04inixe7x9docaq4f4g4p` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  ADD CONSTRAINT `FKk4wyrmwmqdhjjbw8gb9ysr5tr` FOREIGN KEY (`kind_of_sport_id`) REFERENCES `kind_of_sport` (`id`),
  ADD CONSTRAINT `FKpbtc940xb84l0a4p6vfu7qpik` FOREIGN KEY (`season_id`) REFERENCES `season` (`id`);

--
-- Ограничения внешнего ключа таблицы `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FK88s2aajxktwxt6mmqt6hftpu0` FOREIGN KEY (`contacts_id`) REFERENCES `contacts` (`id`);

--
-- Ограничения внешнего ключа таблицы `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FK3046kvjyysq288vy3lsbtc9nw` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Ограничения внешнего ключа таблицы `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FKfmt6vehie8ep9pq0nnvv9een7` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`),
  ADD CONSTRAINT `FKlcyr7t24uocpkd45tqpjixdx9` FOREIGN KEY (`characteristics_id`) REFERENCES `characteristics` (`id`);

--
-- Ограничения внешнего ключа таблицы `product_cheque`
--
ALTER TABLE `product_cheque`
  ADD CONSTRAINT `FKaq3cswrkfcs7d80q8g2089qpa` FOREIGN KEY (`cheque_id`) REFERENCES `сheque` (`id`),
  ADD CONSTRAINT `FKf8dsqehwxj2hiixrmjym693u9` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Ограничения внешнего ключа таблицы `provider`
--
ALTER TABLE `provider`
  ADD CONSTRAINT `FKgxp9qgrdjilovvomwvgecv9al` FOREIGN KEY (`contacts_id`) REFERENCES `contacts` (`id`);

--
-- Ограничения внешнего ключа таблицы `сheque`
--
ALTER TABLE `сheque`
  ADD CONSTRAINT `FK64t74x1jthh4d8j16lu6md2hg` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `FKa96w800ks90lcb1t7sqedlcij` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
