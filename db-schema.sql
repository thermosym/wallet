CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `balance` decimal(16,2) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `transaction_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transaction_amount` decimal(16,2) DEFAULT NULL,
  `credit_account` varchar(255) DEFAULT NULL,
  `credit_status` varchar(255) DEFAULT NULL,
  `debit_account` varchar(255) DEFAULT NULL,
  `debit_status` varchar(255) DEFAULT NULL,
  `execution_time` datetime DEFAULT NULL,
  `transaction_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;