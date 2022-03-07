--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2022-03-07 23:14:59

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 41116)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books (
    isbn character varying(13) NOT NULL,
    title character varying(90) NOT NULL,
    copies integer DEFAULT 1,
    cover character varying(255),
    outline character varying(255) NOT NULL,
    publisher character varying(60)
);


ALTER TABLE public.books OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 41130)
-- Name: lending; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lending (
    id integer NOT NULL,
    lendingdate date NOT NULL,
    returningdate date,
    book character varying(13) NOT NULL,
    borrower character varying(13) NOT NULL
);


ALTER TABLE public.lending OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 41129)
-- Name: lending_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lending_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lending_id_seq OWNER TO postgres;

--
-- TOC entry 3341 (class 0 OID 0)
-- Dependencies: 211
-- Name: lending_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lending_id_seq OWNED BY public.lending.id;


--
-- TOC entry 213 (class 1259 OID 41146)
-- Name: reservations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservations (
    id integer NOT NULL,
    date date NOT NULL,
    book character varying NOT NULL,
    borrower character varying NOT NULL
);


ALTER TABLE public.reservations OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 49308)
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.reservations ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 210 (class 1259 OID 41124)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    code character varying(8) NOT NULL,
    name character varying(25) NOT NULL,
    surname character varying(25) NOT NULL,
    birthdate date,
    fined date
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3178 (class 2604 OID 41133)
-- Name: lending id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lending ALTER COLUMN id SET DEFAULT nextval('public.lending_id_seq'::regclass);


--
-- TOC entry 3330 (class 0 OID 41116)
-- Dependencies: 209
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.books (isbn, title, copies, cover, outline, publisher) FROM stdin;
0141189207445	Love in the Time of Cholera	1			Penguin Books Ltd
0141403257445	Living to Tell the Tale	3			Penguin Books Ltd
0192834177425	The Tragedy of Macbeth	5			Oxford University Press
0521316928985	One Hundred Years of Solitude	6			Cambridge University Press
9780060722302	Nixon and Kissinger	1			HarperCollins Publishers Inc
9780141028736	Memories of My Melancholy Whores	1			Penguin Books Ltd
9780195062052	The American Style of Foreign Policy	1			Oxford University Press
9780198244387	Writings on Logic and Metaphysics	2			OXFORD UNIVERSITY PRESS
9780199535897	Romeo and Juliet	3			Oxford University Press
9780415141444	Dictionary of Lexicography	2			Taylor & Francis Ltd
9781402209222	Let Every Nation Know	2			Sourcebooks MediaFusion
9781407302898	Canoes of the Grand Ocean	2			British Archaeological Reports
9781901992298	Researc Framework for London Archaeology	3			Museum of London Archaeology Service
000	Neverending Story	2	\N	aaaaassss	Brugues
1000	The Great Storm	5	\N	oceanic adventures	Bruguera SL
\.


--
-- TOC entry 3333 (class 0 OID 41130)
-- Dependencies: 212
-- Data for Name: lending; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lending (id, lendingdate, returningdate, book, borrower) FROM stdin;
1	2008-11-28	2008-12-17	0141403257445	A786543
2	2008-11-04	2008-11-30	9781402209222	A786543
3	2008-11-27	2009-01-13	0192834177425	A786543
4	2008-11-24	2008-11-30	9780141028736	A786543
5	2008-11-29	2008-12-10	9781402209222	E433982
6	2008-11-11	2008-11-30	0521316928985	E433982
7	2008-11-30	2009-01-15	0192834177425	S976543
8	2008-11-28	2009-01-06	0192834177425	X328976
9	2008-12-11	2009-02-27	0141189207445	M512776
10	2008-11-28	2009-01-13	9780141028736	T654322
11	2008-11-29	2009-01-07	9780199535897	T654322
12	2008-11-07	2008-11-29	0141189207445	T654322
13	2022-02-19	\N	0521316928985	X328976
14	2022-02-19	\N	9780199535897	X328976
333	2022-01-31	2022-02-21	000	T312987
15	2022-01-19	\N	0141403257445	X328976
16	2022-02-19	2022-02-21	0521316928985	T654322
21	2022-01-21	2022-02-21	9781402209222	S976543
22	2022-02-22	2022-02-22	000	T654322
23	2022-02-22	2022-02-22	000	T654322
24	2022-02-22	2022-02-22	000	T654322
25	2022-02-22	2022-02-22	000	T654322
26	2022-02-22	2022-02-22	000	T654322
27	2022-03-03	\N	9780060722302	T312987
28	2022-03-03	2022-03-03	000	T654322
29	2022-03-03	\N	000	T312987
30	2022-03-03	2022-03-03	000	Q123123
31	2022-03-03	2022-03-03	000	Q123123
\.


--
-- TOC entry 3334 (class 0 OID 41146)
-- Dependencies: 213
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reservations (id, date, book, borrower) FROM stdin;
3	2022-03-02	000	M512776
4	2022-03-02	0141189207445	M512776
7	2022-03-03	0192834177425	T654322
8	2022-03-03	9780060722302	T654322
\.


--
-- TOC entry 3331 (class 0 OID 41124)
-- Dependencies: 210
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (code, name, surname, birthdate, fined) FROM stdin;
A786543	Eirini	Eleftheriou	1983-11-14	\N
B329087	Dimitra	Iwannou	1984-04-10	\N
K893417	Euaggelos	Prwtopappas	1984-09-18	\N
M512776	Gewrgios	Oikonomopoulos	1982-08-12	\N
P734007	Lamprini	Peristeri	1981-11-14	\N
T312987	Nikos	Lamprou	1985-08-28	\N
T654322	Maria	Bergopoulou	1965-09-05	\N
X188263	Stefania	Meliggiwti	1982-02-10	\N
X328976	Paraskeui	Triantafullaki	1986-09-18	\N
X887543	Aggeliki	Paraskeuopoulou	1980-08-12	\N
Z124533	Basileios	Zarokwstas	1979-02-10	\N
E433982	Aristeidis	Fragkoulis	1965-08-28	2022-02-24
E722654	Kwnstantinos	Sarantis	1994-09-05	\N
S976543	Basiliki	Eutaksia	1985-04-10	2022-03-03
Q123123	Aaron	Jamet	1990-09-22	\N
\.


--
-- TOC entry 3342 (class 0 OID 0)
-- Dependencies: 211
-- Name: lending_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lending_id_seq', 31, true);


--
-- TOC entry 3343 (class 0 OID 0)
-- Dependencies: 214
-- Name: reservations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservations_id_seq', 8, true);


--
-- TOC entry 3186 (class 2606 OID 41150)
-- Name: reservations Reservations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT "Reservations_pkey" PRIMARY KEY (id);


--
-- TOC entry 3180 (class 2606 OID 41123)
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (isbn);


--
-- TOC entry 3184 (class 2606 OID 41135)
-- Name: lending lending_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lending
    ADD CONSTRAINT lending_pkey PRIMARY KEY (id);


--
-- TOC entry 3182 (class 2606 OID 41128)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (code);


--
-- TOC entry 3187 (class 2606 OID 41136)
-- Name: lending lending_ibfk_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lending
    ADD CONSTRAINT lending_ibfk_1 FOREIGN KEY (book) REFERENCES public.books(isbn) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3188 (class 2606 OID 41141)
-- Name: lending lending_ibfk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lending
    ADD CONSTRAINT lending_ibfk_2 FOREIGN KEY (borrower) REFERENCES public.users(code) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3189 (class 2606 OID 41153)
-- Name: reservations reservations_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_fk1 FOREIGN KEY (book) REFERENCES public.books(isbn) NOT VALID;


--
-- TOC entry 3190 (class 2606 OID 41158)
-- Name: reservations reservations_fk2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_fk2 FOREIGN KEY (borrower) REFERENCES public.users(code) NOT VALID;


-- Completed on 2022-03-07 23:14:59

--
-- PostgreSQL database dump complete
--

