-- Table creating

CREATE TABLE rails_requests (
  request_id SERIAL PRIMARY KEY,
  request_user_id INTEGER,
  request_date DATE,
  request_station_begin VARCHAR(20),
  request_station_end VARCHAR(20),
  FOREIGN KEY (request_user_id) REFERENCES rails_user(user_id)
);

CREATE TABLE rails_user (
  user_id SERIAL PRIMARY KEY,
  user_name VARCHAR(16),
  user_surname VARCHAR(16),
  user_pass VARCHAR(256)
);

CREATE TABLE rails_admin (
  admin_id SERIAL PRIMARY KEY,
  admin_name VARCHAR(16),
  admin_pass VARCHAR(256)
);

CREATE TABLE rails_train (
  train_id SERIAL PRIMARY KEY,
  train_hours INTEGER NOT NULL,
  train_places INTEGER NOT NULL,
  train_begin_time TIME,
  train_end_time TIME
);

CREATE TABLE rails_station (
  station_id SERIAL PRIMARY KEY,
  station_name VARCHAR(16)
);

CREATE TABLE rails_ways (
way_id SERIAL PRIMARY KEY,
way_trainID INTEGER,
way_stationID INTEGER,
way_counter INTEGER,
FOREIGN KEY (way_trainID) REFERENCES rails_train(train_id)
);


CREATE TABLE rails_carriage (
  carriage_id SERIAL PRIMARY KEY,
  carriage_number SMALLINT,
  carriage_places SMALLINT,
  carriage_trainID INTEGER,
  carriage_type VARCHAR(8)
 );

