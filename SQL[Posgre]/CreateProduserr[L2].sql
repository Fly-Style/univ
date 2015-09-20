-- Table: "Producer"

-- DROP TABLE "Producer";

CREATE TABLE "Producer"
(
ID serial PRIMARY KEY,
surname varchar(20) NOT NULL unique,
status varchar(10) NOT NULL,
town varchar(25) NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE "Producer"
  OWNER TO postgres;
