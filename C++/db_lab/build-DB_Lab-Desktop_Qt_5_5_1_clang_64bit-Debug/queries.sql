CREATE SHEMA db_lab

CREATE TABLE IF NOT EXISTS db_lab.player
(
  nick      VARCHAR(20) PRIMARY KEY ,
  pet_id    INTEGER,
  speed     INTEGER,
  damage    INTEGER,
  armor     REAL,
  hp        INTEGER,
  stamina   INTEGER
 )

CREATE TABLE IF NOT EXISTS db_lab.item
(
	item_id   INTEGER PRIMARY KEY ,
	type      VARCHAR(7),
	model_id  INTEGER NOT NULL,
	weight    INTEGER,
	rarity    VARCHAR(10) NOT NULL,
	slot      VARCHAR(7)
)

CREATE TABLE IF NOT EXISTS db_lab.quests
(
  id        INTEGER PRIMARY KEY ,
  name      VARCHAR(40),
  task      VARCHAR(10),
  prise     VARCHAR(10) NOT NULL,
  loot_id   INT[5]
)


CREATE TABLE IF NOT EXISTS db_lab.loot
(
	loot_id INTEGER PRIMARY KEY,
	item_id INTEGER,
	rarity  REAL
)

CREATE TABLE IF NOT EXISTS db_lab.pet
(
	id 			INTEGER PRIMARY KEY,
	pet_name 	VARCHAR(20),
	type  		VARCHAR(5),
	hp			INTEGER,
	armor 		REAL
)

CREATE TABLE IF NOT EXISTS db_lab.inventory
(
	item_id 	INTEGER,
	slot		VARCHAR(7),
	weight 		INTEGER
)

CREATE TABLE IF NOT EXISTS db_lab.backpack
(
	item_id 	INTEGER,
	qnt			INTEGER,
	weight 		INTEGER
)

INSERT INTO db_lab.item (item_id, type, model_id, weight, rarity, slot)
VALUES
	(1 ,'weapon', 	1001, 40, 'legendary', 'wpn_bhd'),
	(2 ,'weapon', 	1002, 20, 'mythtical', 'wpn_ahd'),
	(3 ,'close', 	1101, 80, 'rare', 		'cuirass'),
	(4 ,'weapon', 	1003, 30, 'rare', 		'wpn_rhd'),
	(5 ,'trink', 	1201, 10, 'mythtical', 	'trinket'),
	(6 ,'close',	1401, 5,  'legendary', 	'amulet')

INSERT INTO db_lab.loot (loot_id, item_id, rarity)
VALUES
	(1, 5, 0.005),
	(2, 6, 0.001),
	(3, 5, 0.01)

	INSERT INTO db_lab.backpack(item_id, qnt, weight)
VALUES
	(2, 1, 25),
	(5, 6, 10)

	INSERT INTO db_lab.quests(id, name, task, prise, loot_id)
VALUES
	(1, 'Killing the Kel`Thurad', 		'destroy', '100-500', '{1}'),
	(2, 'Archemond Devour', 			'destroy', '2000-2500', '{1, 5, 6}'),
	(3, 'Arthas Comeback', 				'destroy', '1000-3000', '{2}'),
	(4, 'Fall the Sargeras', 			'destroy', '1000000',	'{1, 2, 3, 5, 6}')

	INSERT INTO db_lab.pet (id, pet_name, type, hp, armor)
VALUES
	(1, 'Frost Wolf',  'wolf', 100, 5.5),
	(2, 'Sand Ant',    'ant',  200, 0.5),
	(3, 'Rexxar Boar', 'boar', 420, 11.7)









