--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2021-12-15 11:38:50

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 827 (class 1247 OID 41040)
-- Name: author_valid_code; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.author_valid_code AS character(7)
	CONSTRAINT author_valid_code_check CHECK ((VALUE ~* '^[a-z][a-z][a-z][0-9][0-9][0-9][0-9]$'::text));


ALTER DOMAIN public.author_valid_code OWNER TO postgres;

--
-- TOC entry 840 (class 1247 OID 41062)
-- Name: posit_double; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.posit_double AS double precision
	CONSTRAINT posit_double_check CHECK ((VALUE > (0)::double precision));


ALTER DOMAIN public.posit_double OWNER TO postgres;

--
-- TOC entry 850 (class 1247 OID 41081)
-- Name: dimensions_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.dimensions_type AS (
	width public.posit_double,
	height public.posit_double
);


ALTER TYPE public.dimensions_type OWNER TO postgres;

--
-- TOC entry 847 (class 1247 OID 41072)
-- Name: material_types; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.material_types AS ENUM (
    'iron',
    'bronze',
    'marble'
);


ALTER TYPE public.material_types OWNER TO postgres;

--
-- TOC entry 844 (class 1247 OID 41065)
-- Name: painting_types; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.painting_types AS ENUM (
    'oilPainting',
    'waterColour',
    'pastel'
);


ALTER TYPE public.painting_types OWNER TO postgres;

--
-- TOC entry 834 (class 1247 OID 41048)
-- Name: styles; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.styles AS ENUM (
    'grecoRoman',
    'neoClassic',
    'Cubism'
);


ALTER TYPE public.styles OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 41055)
-- Name: artwork; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artwork (
    code integer NOT NULL,
    title character varying(50),
    dated date,
    style public.styles,
    author public.author_valid_code
);


ALTER TABLE public.artwork OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 41060)
-- Name: artwork_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.artwork_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artwork_code_seq OWNER TO postgres;

--
-- TOC entry 3357 (class 0 OID 0)
-- Dependencies: 211
-- Name: artwork_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.artwork_code_seq OWNED BY public.artwork.code;


--
-- TOC entry 209 (class 1259 OID 41042)
-- Name: author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.author (
    code public.author_valid_code NOT NULL,
    name character varying(40),
    nationality character varying(15)
);


ALTER TABLE public.author OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 41082)
-- Name: painting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.painting (
    paint_type public.painting_types,
    dimensions public.dimensions_type
)
INHERITS (public.artwork);


ALTER TABLE public.painting OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 41087)
-- Name: sculpture; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sculpture (
    material public.material_types,
    weigth public.posit_double
)
INHERITS (public.artwork);


ALTER TABLE public.sculpture OWNER TO postgres;

--
-- TOC entry 3348 (class 0 OID 41055)
-- Dependencies: 210
-- Data for Name: artwork; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.artwork (code, title, dated, style, author) FROM stdin;
\.


--
-- TOC entry 3347 (class 0 OID 41042)
-- Dependencies: 209
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.author (code, name, nationality) FROM stdin;
ajo1990	Aaron Jamet Orgiles	Spanish
\.


--
-- TOC entry 3350 (class 0 OID 41082)
-- Dependencies: 213
-- Data for Name: painting; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.painting (code, title, dated, style, author, paint_type, dimensions) FROM stdin;
222	The return of the sky	2005-02-11	neoClassic	ajo1990	oilPainting	(120.33,68.35)
\.


--
-- TOC entry 3351 (class 0 OID 41087)
-- Dependencies: 214
-- Data for Name: sculpture; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sculpture (code, title, dated, style, author, material, weigth) FROM stdin;
\.


--
-- TOC entry 3358 (class 0 OID 0)
-- Dependencies: 211
-- Name: artwork_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.artwork_code_seq', 1, false);


--
-- TOC entry 3200 (class 2606 OID 41093)
-- Name: artwork artwork_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artwork
    ADD CONSTRAINT artwork_pkey PRIMARY KEY (code);


--
-- TOC entry 3198 (class 2606 OID 41095)
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (code);


--
-- TOC entry 3202 (class 2606 OID 41102)
-- Name: painting painting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.painting
    ADD CONSTRAINT painting_pkey PRIMARY KEY (code);


--
-- TOC entry 3204 (class 2606 OID 41109)
-- Name: sculpture sculpture_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sculpture
    ADD CONSTRAINT sculpture_pkey PRIMARY KEY (code);


--
-- TOC entry 3205 (class 2606 OID 41096)
-- Name: artwork artwork_author_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artwork
    ADD CONSTRAINT artwork_author_fk FOREIGN KEY (author) REFERENCES public.author(code) NOT VALID;


--
-- TOC entry 3206 (class 2606 OID 41103)
-- Name: painting painting_author_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.painting
    ADD CONSTRAINT painting_author_fk FOREIGN KEY (author) REFERENCES public.author(code) NOT VALID;


--
-- TOC entry 3207 (class 2606 OID 41110)
-- Name: sculpture sculpture_author_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sculpture
    ADD CONSTRAINT sculpture_author_fk FOREIGN KEY (author) REFERENCES public.author(code) NOT VALID;


-- Completed on 2021-12-15 11:38:50

--
-- PostgreSQL database dump complete
--

