--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2021-11-28 21:44:45

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
-- TOC entry 212 (class 1259 OID 24594)
-- Name: course; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.course (
    code integer NOT NULL,
    name character varying(40)
);


ALTER TABLE public.course OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 24593)
-- Name: courses_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.courses_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.courses_code_seq OWNER TO postgres;

--
-- TOC entry 3351 (class 0 OID 0)
-- Dependencies: 211
-- Name: courses_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.courses_code_seq OWNED BY public.course.code;


--
-- TOC entry 215 (class 1259 OID 24617)
-- Name: enrollment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enrollment (
    code integer NOT NULL,
    student integer NOT NULL,
    course integer NOT NULL
);


ALTER TABLE public.enrollment OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 24616)
-- Name: enrollment_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.enrollment_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.enrollment_code_seq OWNER TO postgres;

--
-- TOC entry 3352 (class 0 OID 0)
-- Dependencies: 214
-- Name: enrollment_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.enrollment_code_seq OWNED BY public.enrollment.code;


--
-- TOC entry 216 (class 1259 OID 24670)
-- Name: scores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scores (
    enrollmentid integer,
    subjectid integer,
    score integer
);


ALTER TABLE public.scores OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 24611)
-- Name: student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student (
    firstname character varying(20) NOT NULL,
    lastname character varying(20) NOT NULL,
    idcard integer NOT NULL,
    email character varying(40),
    phone character varying(20)
);


ALTER TABLE public.student OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16396)
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subjects (
    code integer NOT NULL,
    name character varying(50),
    year integer,
    hours character varying(3),
    course integer
);


ALTER TABLE public.subjects OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16395)
-- Name: subjects_Code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."subjects_Code_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."subjects_Code_seq" OWNER TO postgres;

--
-- TOC entry 3353 (class 0 OID 0)
-- Dependencies: 209
-- Name: subjects_Code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."subjects_Code_seq" OWNED BY public.subjects.code;


--
-- TOC entry 3183 (class 2604 OID 24597)
-- Name: course code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course ALTER COLUMN code SET DEFAULT nextval('public.courses_code_seq'::regclass);


--
-- TOC entry 3184 (class 2604 OID 24620)
-- Name: enrollment code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment ALTER COLUMN code SET DEFAULT nextval('public.enrollment_code_seq'::regclass);


--
-- TOC entry 3182 (class 2604 OID 16399)
-- Name: subjects code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects ALTER COLUMN code SET DEFAULT nextval('public."subjects_Code_seq"'::regclass);


--
-- TOC entry 3341 (class 0 OID 24594)
-- Dependencies: 212
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course (code, name) FROM stdin;
1	Multiplatform App Development
2	Web Development
3	Graphic design
4	Economics
\.


--
-- TOC entry 3344 (class 0 OID 24617)
-- Dependencies: 215
-- Data for Name: enrollment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.enrollment (code, student, course) FROM stdin;
1	222	2
2	272	1
3	333	1
4	444	2
5	222	1
6	1234	1
7	1234	2
8	888	1
9	2424	2
\.


--
-- TOC entry 3345 (class 0 OID 24670)
-- Dependencies: 216
-- Data for Name: scores; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scores (enrollmentid, subjectid, score) FROM stdin;
1	1	5
1	4	7
1	9	5
1	10	7
2	1	8
2	4	3
2	9	7
2	10	6
3	1	8
3	4	4
3	9	7
3	10	6
4	2	5
4	3	5
4	6	7
4	11	6
4	12	8
7	2	3
7	3	6
7	6	7
7	11	5
7	12	9
8	1	3
8	4	4
8	9	7
8	10	7
9	2	8
9	3	7
9	6	7
9	11	6
9	12	5
\.


--
-- TOC entry 3342 (class 0 OID 24611)
-- Dependencies: 213
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student (firstname, lastname, idcard, email, phone) FROM stdin;
AARON	JAMET	222	aaronjametorgiles@gmail.com	600-333-323
ALBA	JAMET	272	albika92@gmail.com	701-444-343
GERONIMO	GARCIA	333	geronimogar@hotmail.es	666-555-444
CARLA	RODRIGUEZ	444	carlazzz@gmail.com	665-444-555
Cuco	Jimenez	888	cucojimz@gmail.es	722-323-323
Rocio	Flores	2424		
aaaaaa	ggggggggggg	1234		
Paquita	Hernandez	0		
Jose	García	101	jrgarcia@iesmarenostrum.com	616116611
Antonio	Luque	102	\N	\N
\.


--
-- TOC entry 3339 (class 0 OID 16396)
-- Dependencies: 210
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.subjects (code, name, year, hours, course) FROM stdin;
1	DATA ACCESS	2	120	1
2	DEVELOPMENT ENVIRONMENTS	1	96	2
3	DATABASE MANAGEMENT SYSTEMS	1	140	2
4	SERVICES AND PROCESSES	2	102	1
6	MARKUP LANGUAGES	1	96	2
10	FOL	1	110	1
11	INTERFACE DEVELOPMENT	2	116	2
8	TECHNICAL ENGLISH I	1	96	1
9	TECHNICAL ENGLISH II	2	96	1
12	MULTIMEDIA DEVELOPMENT	2	140	2
30	Introduction to CAD/CAM	1	60	3
40	Accounting	1	90	4
\.


--
-- TOC entry 3354 (class 0 OID 0)
-- Dependencies: 211
-- Name: courses_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courses_code_seq', 2, true);


--
-- TOC entry 3355 (class 0 OID 0)
-- Dependencies: 214
-- Name: enrollment_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enrollment_code_seq', 9, true);


--
-- TOC entry 3356 (class 0 OID 0)
-- Dependencies: 209
-- Name: subjects_Code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."subjects_Code_seq"', 12, true);


--
-- TOC entry 3189 (class 2606 OID 24599)
-- Name: course courses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT courses_pkey PRIMARY KEY (code);


--
-- TOC entry 3193 (class 2606 OID 24622)
-- Name: enrollment enrollment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enrollment_pkey PRIMARY KEY (code);


--
-- TOC entry 3191 (class 2606 OID 24615)
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (idcard);


--
-- TOC entry 3187 (class 2606 OID 16401)
-- Name: subjects subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (code);


--
-- TOC entry 3185 (class 1259 OID 24610)
-- Name: fki_subjects_fkey; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_subjects_fkey ON public.subjects USING btree (course);


--
-- TOC entry 3196 (class 2606 OID 24628)
-- Name: enrollment enr_course_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enr_course_fk FOREIGN KEY (course) REFERENCES public.course(code);


--
-- TOC entry 3195 (class 2606 OID 24623)
-- Name: enrollment enr_student_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enr_student_fk FOREIGN KEY (student) REFERENCES public.student(idcard);


--
-- TOC entry 3197 (class 2606 OID 24673)
-- Name: scores scores_enrollment_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_enrollment_fk FOREIGN KEY (enrollmentid) REFERENCES public.enrollment(code);


--
-- TOC entry 3198 (class 2606 OID 24678)
-- Name: scores scores_subjects_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_subjects_fk FOREIGN KEY (subjectid) REFERENCES public.subjects(code);


--
-- TOC entry 3194 (class 2606 OID 24605)
-- Name: subjects subjects_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_fkey FOREIGN KEY (course) REFERENCES public.course(code);


-- Completed on 2021-11-28 21:44:45

--
-- PostgreSQL database dump complete
--

