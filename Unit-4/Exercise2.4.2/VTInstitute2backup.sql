--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2021-12-14 13:47:42

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
-- TOC entry 829 (class 1247 OID 32867)
-- Name: posit_int; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.posit_int AS integer
	CONSTRAINT posit_int_check CHECK ((VALUE > 0));


ALTER DOMAIN public.posit_int OWNER TO postgres;

--
-- TOC entry 839 (class 1247 OID 32878)
-- Name: valid_score; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.valid_score AS integer
	CONSTRAINT valid_score_check CHECK (((VALUE >= 0) AND (VALUE <= 10)));


ALTER DOMAIN public.valid_score OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 32869)
-- Name: course; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.course (
    code integer NOT NULL,
    name character varying(40)
);


ALTER TABLE public.course OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 32872)
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
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 210
-- Name: courses_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.courses_code_seq OWNED BY public.course.code;


--
-- TOC entry 211 (class 1259 OID 32873)
-- Name: enrollment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enrollment (
    code integer NOT NULL,
    student public.posit_int NOT NULL,
    course integer NOT NULL
);


ALTER TABLE public.enrollment OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 32876)
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
-- TOC entry 3360 (class 0 OID 0)
-- Dependencies: 212
-- Name: enrollment_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.enrollment_code_seq OWNED BY public.enrollment.code;


--
-- TOC entry 213 (class 1259 OID 32880)
-- Name: scores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scores (
    enrollmentid integer,
    subjectid integer,
    score public.valid_score
);


ALTER TABLE public.scores OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 32883)
-- Name: student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student (
    firstname character varying(20) NOT NULL,
    lastname character varying(20) NOT NULL,
    idcard integer NOT NULL,
    email text[],
    phone integer[]
);


ALTER TABLE public.student OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 32888)
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subjects (
    code integer NOT NULL,
    name character varying(50),
    year public.posit_int,
    hours public.posit_int,
    course integer
);


ALTER TABLE public.subjects OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 32891)
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
-- TOC entry 3361 (class 0 OID 0)
-- Dependencies: 216
-- Name: subjects_Code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."subjects_Code_seq" OWNED BY public.subjects.code;


--
-- TOC entry 3190 (class 2604 OID 32892)
-- Name: course code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course ALTER COLUMN code SET DEFAULT nextval('public.courses_code_seq'::regclass);


--
-- TOC entry 3191 (class 2604 OID 32893)
-- Name: enrollment code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment ALTER COLUMN code SET DEFAULT nextval('public.enrollment_code_seq'::regclass);


--
-- TOC entry 3192 (class 2604 OID 32894)
-- Name: subjects code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects ALTER COLUMN code SET DEFAULT nextval('public."subjects_Code_seq"'::regclass);


--
-- TOC entry 3346 (class 0 OID 32869)
-- Dependencies: 209
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course (code, name) FROM stdin;
\.


--
-- TOC entry 3348 (class 0 OID 32873)
-- Dependencies: 211
-- Data for Name: enrollment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.enrollment (code, student, course) FROM stdin;
\.


--
-- TOC entry 3350 (class 0 OID 32880)
-- Dependencies: 213
-- Data for Name: scores; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.scores (enrollmentid, subjectid, score) FROM stdin;
\.


--
-- TOC entry 3351 (class 0 OID 32883)
-- Dependencies: 214
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.student (firstname, lastname, idcard, email, phone) FROM stdin;
\.


--
-- TOC entry 3352 (class 0 OID 32888)
-- Dependencies: 215
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.subjects (code, name, year, hours, course) FROM stdin;
\.


--
-- TOC entry 3362 (class 0 OID 0)
-- Dependencies: 210
-- Name: courses_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courses_code_seq', 1, false);


--
-- TOC entry 3363 (class 0 OID 0)
-- Dependencies: 212
-- Name: enrollment_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enrollment_code_seq', 1, false);


--
-- TOC entry 3364 (class 0 OID 0)
-- Dependencies: 216
-- Name: subjects_Code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."subjects_Code_seq"', 1, false);


--
-- TOC entry 3194 (class 2606 OID 32896)
-- Name: course courses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT courses_pkey PRIMARY KEY (code);


--
-- TOC entry 3196 (class 2606 OID 32898)
-- Name: enrollment enrollment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enrollment_pkey PRIMARY KEY (code);


--
-- TOC entry 3198 (class 2606 OID 32900)
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (idcard);


--
-- TOC entry 3201 (class 2606 OID 32902)
-- Name: subjects subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (code);


--
-- TOC entry 3199 (class 1259 OID 32903)
-- Name: fki_subjects_fkey; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_subjects_fkey ON public.subjects USING btree (course);


--
-- TOC entry 3202 (class 2606 OID 32904)
-- Name: enrollment enr_course_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enr_course_fk FOREIGN KEY (course) REFERENCES public.course(code);


--
-- TOC entry 3203 (class 2606 OID 32909)
-- Name: enrollment enr_student_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enr_student_fk FOREIGN KEY (student) REFERENCES public.student(idcard);


--
-- TOC entry 3204 (class 2606 OID 32914)
-- Name: scores scores_enrollment_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_enrollment_fk FOREIGN KEY (enrollmentid) REFERENCES public.enrollment(code);


--
-- TOC entry 3205 (class 2606 OID 32919)
-- Name: scores scores_subjects_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_subjects_fk FOREIGN KEY (subjectid) REFERENCES public.subjects(code);


--
-- TOC entry 3206 (class 2606 OID 32924)
-- Name: subjects subjects_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_fkey FOREIGN KEY (course) REFERENCES public.course(code);


-- Completed on 2021-12-14 13:47:43

--
-- PostgreSQL database dump complete
--

