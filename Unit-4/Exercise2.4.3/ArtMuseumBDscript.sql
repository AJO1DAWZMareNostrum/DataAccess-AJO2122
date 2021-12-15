--
-- Creation of domain to restrict the author code´s values, and creation of Author table
--
CREATE DOMAIN author_valid_code AS CHAR(7) 
CHECK (VALUE ~* '[a-z][a-z][a-z][0-9][0-9][0-9][0-9]');

CREATE TABLE author (
	code author_valid_code NOT NULL,
	name character varying(40),
	nationality character varying(15)
);

ALTER TABLE ONLY author
	ADD CONSTRAINTS author_pkey PRIMARY KEY (code);

--
-- Creation of ENUM Styles and the ArtWork table
--
CREATE TYPE styles AS ENUM ('grecoRoman', 'neoClassic', 'Cubism');

CREATE TABLE artwork (
	code integer NOT NULL,
	title character varying(50),
	dated date,
	style styles,
	author author_valid_code
);

CREATE SEQUENCE artwork_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE artwork_code_seq OWNED BY artwork.code;

ALTER TABLE ONLY artwork
ADD CONSTRAINTS artwork_pkey PRIMARY KEY (code);

ALTER TABLE ONLY artwork
ADD CONSTRAINTS artwork_author_fk FOREIGN KEY (code) REFERENCES author(code);	

--
--Creation of a domain for positive integers, ENUM´s for painting and material types, and creation of a composite type for Dimensions
--
CREATE DOMAIN posit_double AS double precision CHECK (VALUE > 0);

CREATE TYPE painting_types AS ENUM ('oilPainting', 'waterColour', 'pastel');

CREATE TYPE material_types AS ENUM ('iron', 'bronze', 'marble');

CREATE TYPE dimensions_type AS (
	width	posit_double,
	height  posit_double
);

--
-- Creation of Painting and Sculture tables, using herency in this case
--
CREATE TABLE IF NOT EXISTS painting (
	type painting_types,
	dimensions dimensions_type
) INHERITS (artwork);

ALTER TABLE ONLY painting
ADD CONSTRAINTS painting_pkey PRIMARY KEY (code);

ALTER TABLE ONLY painting
ADD CONSTRAINTS painting_artwork_fk FOREIGN KEY (code) REFERENCES artwork(code);


CREATE TABLE IF NOT EXISTS sculpture (
	material material_types,
	weigth posit_double
) INHERITS (artwork);

ALTER TABLE ONLY sculpture
ADD CONSTRAINTS sculpture_pkey PRIMARY KEY (code);

ALTER TABLE ONLY sculpture
ADD CONSTRAINTS sculpture_artwork_fk FOREIGN KEY (code) REFERENCES artwork(code);


--
-- I have added finally the constraints from the pgAdmin4 graphic editor, because of some errors at the time of trying to do it through this script.
--




