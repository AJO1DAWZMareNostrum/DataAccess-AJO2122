--
-- Domain created for the jobs in employee table, because all the elements are repeating themselves in the table
--
CREATE DOMAIN jobs_registered AS TEXT 
CHECK (VALUE = 'ANALYST' OR
VALUE = 'CLERK' OR
VALUE = 'MANAGER' OR
VALUE = 'PRESIDENT' OR
VALUE = 'SALESMAN');


CREATE TABLE public.employee (
    empno integer NOT NULL,
    ename character varying(10),
    job jobs_registered,
    deptno integer
);


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


--
-- DOMAIN in department number to restrict its value to a positive number
--
CREATE DOMAIN posint AS integer CHECK (VALUE > 0);

CREATE TABLE public.dept (
    deptno posint NOT NULL,
    dname character varying(14),
    loc character varying(13)
);


ALTER TABLE ONLY public.dept
    ADD CONSTRAINT dept_pkey PRIMARY KEY (deptno);
	
ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (empno);
	
ALTER TABLE ONLY public.employee
    ADD CONSTRAINT fk_employee_dpt FOREIGN KEY (deptno) REFERENCES public.dept(deptno);