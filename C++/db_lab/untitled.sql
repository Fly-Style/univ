CREATE SHEMA db_lab

CREATE TABLE IF NOT EXISTS db_lab.item
(
  item_id   INTEGER PRIMARY KEY ,
  type      VARCHAR(7),
  model_id  INTEGER NOT NULL,
  weight    INTEGER,
  rarity    VARCHAR(10) NOT NULL,
  slot      VARCHAR(7)
)


