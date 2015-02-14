
CREATE TABLE `key` (
  int       int(10) NOT NULL AUTO_INCREMENT,
  varchar   varchar(10) NOT NULL,
  `date`    date UNIQUE,
  timestamp datetime NULL,
  number    numeric(18, 2),
  longtext  longtext DEFAULT 'xxxx',
  PRIMARY KEY (int));
CREATE INDEX key
  ON `key` (varchar);
CREATE UNIQUE INDEX key2
  ON `key` (number);
CREATE FULLTEXT INDEX key3
  ON `key` (longtext);
CREATE FULLTEXT INDEX key4
  ON `key` (number);
CREATE UNIQUE INDEX key5
  ON `key` (timestamp, number);
