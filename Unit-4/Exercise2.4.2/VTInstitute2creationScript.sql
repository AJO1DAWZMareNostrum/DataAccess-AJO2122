CREATE DOMAIN posit_int AS integer CHECK (VALUE > 0);


CREATE TABLE public.course (
    code integer NOT NULL,
    name character varying(40)
);

CREATE SEQUENCE public.courses_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE public.courses_code_seq OWNED BY public.course.code;


CREATE TABLE public.enrollment (
    code integer NOT NULL,
    student posit_int NOT NULL,
    course integer NOT NULL
);

CREATE SEQUENCE public.enrollment_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE public.enrollment_code_seq OWNED BY public.enrollment.code;

--
-- Creation of a Domain to restrict the score´s values (0 to 10), for the Scores table
--
CREATE DOMAIN valid_score AS integer
CHECK (VALUE >= 0 AND VALUE <= 10);

CREATE TABLE public.scores (
    enrollmentid integer,
    subjectid integer,
    score valid_score
);


--
-- Creation of Student table, adding some arrays to allow registering multiple phone numbers or emails
--
CREATE TABLE public.student (
    firstname character varying(20) NOT NULL,
    lastname character varying(20) NOT NULL,
    idcard integer NOT NULL,
    email text[],
    phone integer[]
);


--
-- Added a domain that restricts the use of integers only to positive values
--
CREATE TABLE public.subjects (
    code integer NOT NULL,
    name character varying(50),
    year posit_int,
    hours posit_int,
    course integer
);

CREATE SEQUENCE public."subjects_Code_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE public."subjects_Code_seq" OWNED BY public.subjects.code;


ALTER TABLE ONLY public.course ALTER COLUMN code SET DEFAULT nextval('public.courses_code_seq'::regclass);

ALTER TABLE ONLY public.enrollment ALTER COLUMN code SET DEFAULT nextval('public.enrollment_code_seq'::regclass);

ALTER TABLE ONLY public.subjects ALTER COLUMN code SET DEFAULT nextval('public."subjects_Code_seq"'::regclass);


--
-- Constraints
--
ALTER TABLE ONLY public.course
    ADD CONSTRAINT courses_pkey PRIMARY KEY (code);

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enrollment_pkey PRIMARY KEY (code);

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (idcard);

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (code);

CREATE INDEX fki_subjects_fkey ON public.subjects USING btree (course);

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enr_course_fk FOREIGN KEY (course) REFERENCES public.course(code);

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enr_student_fk FOREIGN KEY (student) REFERENCES public.student(idcard);

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_enrollment_fk FOREIGN KEY (enrollmentid) REFERENCES public.enrollment(code);

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_subjects_fk FOREIGN KEY (subjectid) REFERENCES public.subjects(code);

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_fkey FOREIGN KEY (course) REFERENCES public.course(code);	



