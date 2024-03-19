PGDMP                      |         
   javastocks    16.2    16.2 #               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            	           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            
           1262    16398 
   javastocks    DATABASE     �   CREATE DATABASE javastocks WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE javastocks;
                postgres    false            �            1259    16400    article    TABLE       CREATE TABLE public.article (
    article_id integer NOT NULL,
    libelle character varying(255),
    categorie character varying(255),
    taille character varying(50),
    couleur character varying(50),
    poids character varying(55),
    quantite integer
);
    DROP TABLE public.article;
       public         heap    postgres    false            �            1259    16399    article_article_id_seq    SEQUENCE     �   CREATE SEQUENCE public.article_article_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.article_article_id_seq;
       public          postgres    false    216                       0    0    article_article_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.article_article_id_seq OWNED BY public.article.article_id;
          public          postgres    false    215            �            1259    16416    coureur    TABLE     �   CREATE TABLE public.coureur (
    coureur_id integer NOT NULL,
    coureur_nom character varying(255),
    coureur_prenom character varying(255)
);
    DROP TABLE public.coureur;
       public         heap    postgres    false            �            1259    16415    coureur_coureur_id_seq    SEQUENCE     �   CREATE SEQUENCE public.coureur_coureur_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.coureur_coureur_id_seq;
       public          postgres    false    220                       0    0    coureur_coureur_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.coureur_coureur_id_seq OWNED BY public.coureur.coureur_id;
          public          postgres    false    219            �            1259    16425    reservation    TABLE     �   CREATE TABLE public.reservation (
    reservation_id integer NOT NULL,
    date_reservation date,
    quantite_reservee integer,
    article_id integer,
    type_epreuve_id integer,
    coureur_id integer
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    16424    reservation_reservation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reservation_reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.reservation_reservation_id_seq;
       public          postgres    false    222                       0    0    reservation_reservation_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.reservation_reservation_id_seq OWNED BY public.reservation.reservation_id;
          public          postgres    false    221            �            1259    16409    type_epreuve    TABLE     |   CREATE TABLE public.type_epreuve (
    type_epreuve_id integer NOT NULL,
    type_epreuve_libelle character varying(255)
);
     DROP TABLE public.type_epreuve;
       public         heap    postgres    false            �            1259    16408     type_epreuve_type_epreuve_id_seq    SEQUENCE     �   CREATE SEQUENCE public.type_epreuve_type_epreuve_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public.type_epreuve_type_epreuve_id_seq;
       public          postgres    false    218                       0    0     type_epreuve_type_epreuve_id_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE public.type_epreuve_type_epreuve_id_seq OWNED BY public.type_epreuve.type_epreuve_id;
          public          postgres    false    217            _           2604    16403    article article_id    DEFAULT     x   ALTER TABLE ONLY public.article ALTER COLUMN article_id SET DEFAULT nextval('public.article_article_id_seq'::regclass);
 A   ALTER TABLE public.article ALTER COLUMN article_id DROP DEFAULT;
       public          postgres    false    216    215    216            a           2604    16419    coureur coureur_id    DEFAULT     x   ALTER TABLE ONLY public.coureur ALTER COLUMN coureur_id SET DEFAULT nextval('public.coureur_coureur_id_seq'::regclass);
 A   ALTER TABLE public.coureur ALTER COLUMN coureur_id DROP DEFAULT;
       public          postgres    false    220    219    220            b           2604    16428    reservation reservation_id    DEFAULT     �   ALTER TABLE ONLY public.reservation ALTER COLUMN reservation_id SET DEFAULT nextval('public.reservation_reservation_id_seq'::regclass);
 I   ALTER TABLE public.reservation ALTER COLUMN reservation_id DROP DEFAULT;
       public          postgres    false    222    221    222            `           2604    16412    type_epreuve type_epreuve_id    DEFAULT     �   ALTER TABLE ONLY public.type_epreuve ALTER COLUMN type_epreuve_id SET DEFAULT nextval('public.type_epreuve_type_epreuve_id_seq'::regclass);
 K   ALTER TABLE public.type_epreuve ALTER COLUMN type_epreuve_id DROP DEFAULT;
       public          postgres    false    218    217    218            �          0    16400    article 
   TABLE DATA           c   COPY public.article (article_id, libelle, categorie, taille, couleur, poids, quantite) FROM stdin;
    public          postgres    false    216   h)                 0    16416    coureur 
   TABLE DATA           J   COPY public.coureur (coureur_id, coureur_nom, coureur_prenom) FROM stdin;
    public          postgres    false    220   �)                 0    16425    reservation 
   TABLE DATA           �   COPY public.reservation (reservation_id, date_reservation, quantite_reservee, article_id, type_epreuve_id, coureur_id) FROM stdin;
    public          postgres    false    222   �)                  0    16409    type_epreuve 
   TABLE DATA           M   COPY public.type_epreuve (type_epreuve_id, type_epreuve_libelle) FROM stdin;
    public          postgres    false    218   �)                  0    0    article_article_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.article_article_id_seq', 3, true);
          public          postgres    false    215                       0    0    coureur_coureur_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.coureur_coureur_id_seq', 1, false);
          public          postgres    false    219                       0    0    reservation_reservation_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 1, false);
          public          postgres    false    221                       0    0     type_epreuve_type_epreuve_id_seq    SEQUENCE SET     O   SELECT pg_catalog.setval('public.type_epreuve_type_epreuve_id_seq', 1, false);
          public          postgres    false    217            d           2606    16407    article article_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (article_id);
 >   ALTER TABLE ONLY public.article DROP CONSTRAINT article_pkey;
       public            postgres    false    216            h           2606    16423    coureur coureur_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.coureur
    ADD CONSTRAINT coureur_pkey PRIMARY KEY (coureur_id);
 >   ALTER TABLE ONLY public.coureur DROP CONSTRAINT coureur_pkey;
       public            postgres    false    220            j           2606    16430    reservation reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    222            f           2606    16414    type_epreuve type_epreuve_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.type_epreuve
    ADD CONSTRAINT type_epreuve_pkey PRIMARY KEY (type_epreuve_id);
 H   ALTER TABLE ONLY public.type_epreuve DROP CONSTRAINT type_epreuve_pkey;
       public            postgres    false    218            k           2606    16431 '   reservation reservation_article_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_article_id_fkey FOREIGN KEY (article_id) REFERENCES public.article(article_id);
 Q   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_article_id_fkey;
       public          postgres    false    4708    222    216            l           2606    16441 '   reservation reservation_coureur_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_coureur_id_fkey FOREIGN KEY (coureur_id) REFERENCES public.coureur(coureur_id);
 Q   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_coureur_id_fkey;
       public          postgres    false    222    220    4712            m           2606    16436 ,   reservation reservation_type_epreuve_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_type_epreuve_id_fkey FOREIGN KEY (type_epreuve_id) REFERENCES public.type_epreuve(type_epreuve_id);
 V   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_type_epreuve_id_fkey;
       public          postgres    false    222    218    4710            �   G   x�3�L�ON�t��!NS.#β��T����Ԣb��N��̢TNNCS.cδļ$�&�\1z\\\ ԫ�            x������ � �            x������ � �             x������ � �     