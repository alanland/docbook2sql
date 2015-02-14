CREATE TABLE "key" (
  int        SERIAL NOT NULL, 
  varchar   varchar(10) NOT NULL, 
  "date"    date UNIQUE, 
  timestamp timestamp(7), 
  number    numeric(18, 2), 
  longtext  text DEFAULT 'xxxx', 
  PRIMARY KEY (int));
CREATE INDEX key 
  ON "key" (varchar);
CREATE UNIQUE INDEX key2 
  ON "key" (number);
CREATE INDEX key3 
  ON "key" (longtext);
CREATE INDEX key4 
  ON "key" (number);
CREATE UNIQUE INDEX key5 
  ON "key" (timestamp, number);

