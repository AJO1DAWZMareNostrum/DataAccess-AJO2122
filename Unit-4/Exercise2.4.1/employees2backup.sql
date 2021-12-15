--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2021-12-14 11:44:09

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
-- TOC entry 826 (class 1247 OID 32840)
-- Name: jobs_registered; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.jobs_registered AS text
	CONSTRAINT jobs_registered_check CHECK (((VALUE = 'ANALYST'::text) OR (VALUE = 'CLERK'::text) OR (VALUE = 'MANAGER'::text) OR (VALUE = 'PRESIDENT'::text) OR (VALUE = 'SALESMAN'::text)));


ALTER DOMAIN public.jobs_registered OWNER TO postgres;

--
-- TOC entry 833 (class 1247 OID 32851)
-- Name: posint; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.posint AS integer
	CONSTRAINT posint_check CHECK ((VALUE > 0));


ALTER DOMAIN public.posint OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 32842)
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee (
    empno integer NOT NULL,
    ename character varying(10),
    job public.jobs_registered,
    deptno integer
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- TOC entry 211 (class 1255 OID 32847)
-- Name: employees_by_name(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.employees_by_name() RETURNS SETOF public.employee
    LANGUAGE plpgsql
    AS $$
DECLARE
	myemployee employee;
BEGIN
	FOR myemployee IN SELECT * FROM employee WHERE ename LIKE "A%" LOOP
	RETURN NEXT myemployee;
END LOOP; 
END; 
$$;


ALTER FUNCTION public.employees_by_name() OWNER TO postgres;

--
-- TOC entry 212 (class 1255 OID 32848)
-- Name: employees_in_a_department(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.employees_in_a_department(dept_number integer) RETURNS SETOF public.employee
    LANGUAGE plpgsql
    AS $$
DECLARE
	myemployee employee;
BEGIN
	FOR myemployee IN SELECT * FROM employee WHERE deptno=dept_number LOOP
	RETURN NEXT myemployee;
END LOOP; 
END; 
$$;


ALTER FUNCTION public.employees_in_a_department(dept_number integer) OWNER TO postgres;

--
-- TOC entry 213 (class 1255 OID 32849)
-- Name: employees_in_a_job(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.employees_in_a_job(e_job character varying) RETURNS SETOF public.employee
    LANGUAGE plpgsql
    AS $$
DECLARE
	myemployee employee;
BEGIN
	FOR myemployee IN SELECT * FROM employee WHERE job=e_job LOOP
	RETURN NEXT myemployee;
END LOOP; 
END; 
$$;


ALTER FUNCTION public.employees_in_a_job(e_job character varying) OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 32853)
-- Name: dept; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dept (
    deptno public.posint NOT NULL,
    dname character varying(14),
    loc character varying(13)
);


ALTER TABLE public.dept OWNER TO postgres;

--
-- TOC entry 3323 (class 0 OID 32853)
-- Dependencies: 210
-- Data for Name: dept; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dept (deptno, dname, loc) FROM stdin;
10	ACCOUNTING	NEW YORK
20	RESEARCH	DALLAS
30	SALES	CHICAGO
40	OPERATIONS	BOSTON
\.


--
-- TOC entry 3322 (class 0 OID 32842)
-- Dependencies: 209
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employee (empno, ename, job, deptno) FROM stdin;
7369	SMITH	CLERK	20
7499	ALLEN	SALESMAN	30
7521	WARD	SALESMAN	30
7566	JONES	MANAGER	20
7654	MARTIN	SALESMAN	30
7698	BLAKE	MANAGER	30
7782	CLARK	MANAGER	10
7788	SCOTT	ANALYST	20
7839	KING	PRESIDENT	10
7844	TURNER	SALESMAN	30
7876	ADAMS	CLERK	20
7900	JAMES	CLERK	30
7902	FORD	ANALYST	20
7934	MILLER	CLERK	10
\.


--
-- TOC entry 3181 (class 2606 OID 32857)
-- Name: dept dept_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dept
    ADD CONSTRAINT dept_pkey PRIMARY KEY (deptno);


--
-- TOC entry 3179 (class 2606 OID 32859)
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (empno);


--
-- TOC entry 3182 (class 2606 OID 32860)
-- Name: employee fk_employee_dpt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT fk_employee_dpt FOREIGN KEY (deptno) REFERENCES public.dept(deptno);


-- Completed on 2021-12-14 11:44:10

--
-- PostgreSQL database dump complete
--

