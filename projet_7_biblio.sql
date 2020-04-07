-- Database: db_biblio_book

-- DROP DATABASE db_biblio_book;

CREATE DATABASE db_biblio_book
    WITH 
    OWNER = test7
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.book

-- DROP TABLE public.book;

CREATE TABLE public.book
(
    id bigint NOT NULL DEFAULT nextval('book_id_seq'::regclass),
    author character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    image character varying(255) COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT book_pkey PRIMARY KEY (id)
)


INSERT INTO public.book(
	id, author, description, image, title)
	VALUES (1,'Stephen King','Enfants, dans leur petite ville de Derry, Ben, Eddie, Richie et la petite bande du « Club des ratés »','/images/ca_stephenKing.jpg','ÇA, tome 1'),
	(2,'Iggulden','Si les pierres pouvaient parler... elles appelleraient à la guerre.','/images/iggulden.png','Les Prodiges de l''\empire - Tome 2 : Shiang'),
	(3,'George R.R. Martin','Le royaume des sept couronnes est sur le point de connaître son plus terrible hiver','/images/georgeMartin.png','Game Of Thrones, Le trône de fer - Tome 2 : L''\intégrale'),
	(4,'Tolkien','Pour la première fois en France, la BnF célèbre J.R.R. Tolkien à l''\occasion d''\une grande exposition événement.','/images/tolkien.png','Voyage en Terre du Milieu');


-- Table: public.category

-- DROP TABLE public.category;

CREATE TABLE public.category
(
    id bigint NOT NULL DEFAULT nextval('category_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT category_pkey PRIMARY KEY (id)
)



INSERT INTO public.category(
	id, name)
	VALUES (1,'Fantasy'),(2,'Science Fiction'),(3,'Romance'),(4,'Littérature'),(5,'Thrillers'),(6,'Action & Aventure'),(7,'Horreur');


-- Table: public.book_categories

-- DROP TABLE public.book_categories;

CREATE TABLE public.book_categories
(
    book_id bigint NOT NULL,
    category_id bigint NOT NULL,
    CONSTRAINT book_categories_pkey PRIMARY KEY (book_id, category_id),
    CONSTRAINT fken2tgslrqspfien26r5hagxf9 FOREIGN KEY (category_id)
        REFERENCES public.category (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkrq5mftm1ejl023epqbn42lpa3 FOREIGN KEY (book_id)
        REFERENCES public.book (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

INSERT INTO public.book_categories(
	book_id, category_id)
	VALUES (1,1),(2,4),(3,1),(4,6);

-- Table: public.copy

-- DROP TABLE public.copy;

CREATE TABLE public.copy
(
    id bigint NOT NULL DEFAULT nextval('copy_id_seq'::regclass),
    is_available integer,
    reference_number integer,
    book_id bigint,
    CONSTRAINT copy_pkey PRIMARY KEY (id),
    CONSTRAINT fkof5k7k6c41i06j6fj3slgsmam FOREIGN KEY (book_id)
        REFERENCES public.book (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


INSERT INTO public.copy(
	id, is_available, reference_number, book_id)
	VALUES (1,0,'12345',1),(2,1,'54321',2),(3,1,'32134',2),(4,0,'87846',3),(5,1,'12753',4);

-- Table: public.loan

-- DROP TABLE public.loan;

CREATE TABLE public.loan
(
    id bigint NOT NULL DEFAULT nextval('loan_id_seq'::regclass),
    id_person integer,
    date character varying(255) COLLATE pg_catalog."default",
    is_second integer,
    copy_id bigint,
    CONSTRAINT loan_pkey PRIMARY KEY (id),
    CONSTRAINT fkw8dbi4aoljiy817dnmnpaikd FOREIGN KEY (copy_id)
        REFERENCES public.copy (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)


INSERT INTO public.loan(
	id, id_person, date, is_second, copy_id)
	VALUES (1,1,'2019-08-28',NULL,1),(2,2,'2019-10-04',NULL,3),(3,4,'2019-11-22',NULL,3);


-- Database: db_biblio_person

-- DROP DATABASE db_biblio_person;

CREATE DATABASE db_biblio_person
    WITH 
    OWNER = test7
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.person

-- DROP TABLE public.person;

CREATE TABLE public.person
(
    id bigint NOT NULL DEFAULT nextval('person_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default",
    firstname character varying(255) COLLATE pg_catalog."default",
    is_admin integer,
    lastname character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT person_pkey PRIMARY KEY (id)
)


INSERT INTO public.person(
	id, email, firstname, is_admin, lastname, password)
	VALUES (1, 'majdamanso@gmail.com','majda',1, 'manso', 'Majida123'),
	(2, 'jack@gmail.com','jack',1, 'jose', 'Majida123'),
	(3, 'marie@gmail.com','marie',1, 'cris', 'Majida123'),
	(4, 'rima@gmail.com','rima',1, 'yossef', 'Majida123');