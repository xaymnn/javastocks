--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-03-24 17:11:50

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
-- TOC entry 216 (class 1259 OID 16400)
-- Name: article; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.article (
    article_id integer NOT NULL,
    libelle character varying(255),
    categorie character varying(255),
    taille character varying(50),
    couleur character varying(50),
    poids character varying(55),
    quantite integer,
    est_supprime boolean DEFAULT false
);


ALTER TABLE public.article OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16399)
-- Name: article_article_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.article_article_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.article_article_id_seq OWNER TO postgres;

--
-- TOC entry 4875 (class 0 OID 0)
-- Dependencies: 215
-- Name: article_article_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.article_article_id_seq OWNED BY public.article.article_id;


--
-- TOC entry 220 (class 1259 OID 16416)
-- Name: coureur; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.coureur (
    coureur_id integer NOT NULL,
    coureur_nom character varying(255),
    coureur_prenom character varying(255)
);


ALTER TABLE public.coureur OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16415)
-- Name: coureur_coureur_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.coureur_coureur_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.coureur_coureur_id_seq OWNER TO postgres;

--
-- TOC entry 4876 (class 0 OID 0)
-- Dependencies: 219
-- Name: coureur_coureur_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.coureur_coureur_id_seq OWNED BY public.coureur.coureur_id;


--
-- TOC entry 222 (class 1259 OID 16425)
-- Name: reservation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservation (
    reservation_id integer NOT NULL,
    date_reservation date,
    quantite_reservee integer,
    article_id integer,
    type_epreuve_id integer,
    coureur_id integer
);


ALTER TABLE public.reservation OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16424)
-- Name: reservation_reservation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reservation_reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reservation_reservation_id_seq OWNER TO postgres;

--
-- TOC entry 4877 (class 0 OID 0)
-- Dependencies: 221
-- Name: reservation_reservation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reservation_reservation_id_seq OWNED BY public.reservation.reservation_id;


--
-- TOC entry 218 (class 1259 OID 16409)
-- Name: type_epreuve; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.type_epreuve (
    type_epreuve_id integer NOT NULL,
    type_epreuve_libelle character varying(255)
);


ALTER TABLE public.type_epreuve OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16408)
-- Name: type_epreuve_type_epreuve_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.type_epreuve_type_epreuve_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.type_epreuve_type_epreuve_id_seq OWNER TO postgres;

--
-- TOC entry 4878 (class 0 OID 0)
-- Dependencies: 217
-- Name: type_epreuve_type_epreuve_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.type_epreuve_type_epreuve_id_seq OWNED BY public.type_epreuve.type_epreuve_id;


--
-- TOC entry 4703 (class 2604 OID 16403)
-- Name: article article_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.article ALTER COLUMN article_id SET DEFAULT nextval('public.article_article_id_seq'::regclass);


--
-- TOC entry 4706 (class 2604 OID 16419)
-- Name: coureur coureur_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coureur ALTER COLUMN coureur_id SET DEFAULT nextval('public.coureur_coureur_id_seq'::regclass);


--
-- TOC entry 4707 (class 2604 OID 16428)
-- Name: reservation reservation_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation ALTER COLUMN reservation_id SET DEFAULT nextval('public.reservation_reservation_id_seq'::regclass);


--
-- TOC entry 4705 (class 2604 OID 16412)
-- Name: type_epreuve type_epreuve_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_epreuve ALTER COLUMN type_epreuve_id SET DEFAULT nextval('public.type_epreuve_type_epreuve_id_seq'::regclass);


--
-- TOC entry 4863 (class 0 OID 16400)
-- Dependencies: 216
-- Data for Name: article; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.article (article_id, libelle, categorie, taille, couleur, poids, quantite, est_supprime) FROM stdin;
2	veste lakers	T	XL	Noire		30	f
1	coca	B	\N	\N		45	t
5	cola	B	\N	\N		400	f
6	Maillot de course	T	M	Rouge	150g	69	f
3	fanta	B	\N	\N	330g	149	f
4	Hawaii	B	\N	\N		200	t
\.


--
-- TOC entry 4867 (class 0 OID 16416)
-- Dependencies: 220
-- Data for Name: coureur; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.coureur (coureur_id, coureur_nom, coureur_prenom) FROM stdin;
1	Brahim	Abdelkader
2	Soufiane	Rahimi
3	Yassine	Bounou
4	Achraf	Hakimi
5	Hakim	Ziyech
6	Ilyass	Akhoumach
\.


--
-- TOC entry 4869 (class 0 OID 16425)
-- Dependencies: 222
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reservation (reservation_id, date_reservation, quantite_reservee, article_id, type_epreuve_id, coureur_id) FROM stdin;
1	2024-03-14	92	1	1	1
3	2024-03-28	33	5	3	6
\.


--
-- TOC entry 4865 (class 0 OID 16409)
-- Dependencies: 218
-- Data for Name: type_epreuve; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.type_epreuve (type_epreuve_id, type_epreuve_libelle) FROM stdin;
1	Course a velo
2	Marathon
3	Course 100m
\.


--
-- TOC entry 4879 (class 0 OID 0)
-- Dependencies: 215
-- Name: article_article_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.article_article_id_seq', 6, true);


--
-- TOC entry 4880 (class 0 OID 0)
-- Dependencies: 219
-- Name: coureur_coureur_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.coureur_coureur_id_seq', 6, true);


--
-- TOC entry 4881 (class 0 OID 0)
-- Dependencies: 221
-- Name: reservation_reservation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 1, false);


--
-- TOC entry 4882 (class 0 OID 0)
-- Dependencies: 217
-- Name: type_epreuve_type_epreuve_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.type_epreuve_type_epreuve_id_seq', 3, true);


--
-- TOC entry 4709 (class 2606 OID 16407)
-- Name: article article_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (article_id);


--
-- TOC entry 4713 (class 2606 OID 16423)
-- Name: coureur coureur_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.coureur
    ADD CONSTRAINT coureur_pkey PRIMARY KEY (coureur_id);


--
-- TOC entry 4715 (class 2606 OID 16430)
-- Name: reservation reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);


--
-- TOC entry 4711 (class 2606 OID 16414)
-- Name: type_epreuve type_epreuve_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.type_epreuve
    ADD CONSTRAINT type_epreuve_pkey PRIMARY KEY (type_epreuve_id);


--
-- TOC entry 4716 (class 2606 OID 16431)
-- Name: reservation reservation_article_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_article_id_fkey FOREIGN KEY (article_id) REFERENCES public.article(article_id);


--
-- TOC entry 4717 (class 2606 OID 16441)
-- Name: reservation reservation_coureur_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_coureur_id_fkey FOREIGN KEY (coureur_id) REFERENCES public.coureur(coureur_id);


--
-- TOC entry 4718 (class 2606 OID 16436)
-- Name: reservation reservation_type_epreuve_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_type_epreuve_id_fkey FOREIGN KEY (type_epreuve_id) REFERENCES public.type_epreuve(type_epreuve_id);


-- Completed on 2024-03-24 17:11:50

--
-- PostgreSQL database dump complete
--

