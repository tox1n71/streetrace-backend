--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- Name: car_model; Type: TABLE; Schema: public; Owner: ivan
--

CREATE TABLE public.car_model (
                                  id bigint NOT NULL,
                                  brand character varying(255),
                                  default_color character varying(255),
                                  level integer NOT NULL,
                                  model character varying(255),
                                  power integer NOT NULL,
                                  price integer NOT NULL
);


ALTER TABLE public.car_model OWNER TO ivan;

--
-- Name: car_model_id_seq; Type: SEQUENCE; Schema: public; Owner: ivan
--

CREATE SEQUENCE public.car_model_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.car_model_id_seq OWNER TO ivan;

--
-- Name: car_model_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ivan
--

ALTER SEQUENCE public.car_model_id_seq OWNED BY public.car_model.id;


--
-- Name: race; Type: TABLE; Schema: public; Owner: ivan
--

CREATE TABLE public.race (
                             id bigint NOT NULL,
                             friend_car_horsepower integer NOT NULL,
                             race_time timestamp(6) without time zone,
                             user_car_horsepower integer NOT NULL,
                             user_won boolean NOT NULL,
                             friend_id bigint,
                             user_id bigint
);


ALTER TABLE public.race OWNER TO ivan;

--
-- Name: race_id_seq; Type: SEQUENCE; Schema: public; Owner: ivan
--

CREATE SEQUENCE public.race_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.race_id_seq OWNER TO ivan;

--
-- Name: race_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ivan
--

ALTER SEQUENCE public.race_id_seq OWNED BY public.race.id;


--
-- Name: user_car; Type: TABLE; Schema: public; Owner: ivan
--

CREATE TABLE public.user_car (
                                 id bigint NOT NULL,
                                 absorber_level integer NOT NULL,
                                 brakes_level integer NOT NULL,
                                 color character varying(255),
                                 exhaust_level integer NOT NULL,
                                 intercooler_level integer NOT NULL,
                                 power integer NOT NULL,
                                 tires_level integer NOT NULL,
                                 car_id bigint,
                                 user_id bigint
);


ALTER TABLE public.user_car OWNER TO ivan;

--
-- Name: user_car_id_seq; Type: SEQUENCE; Schema: public; Owner: ivan
--

CREATE SEQUENCE public.user_car_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_car_id_seq OWNER TO ivan;

--
-- Name: user_car_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ivan
--

ALTER SEQUENCE public.user_car_id_seq OWNED BY public.user_car.id;


--
-- Name: user_car_vinyl; Type: TABLE; Schema: public; Owner: ivan
--

CREATE TABLE public.user_car_vinyl (
                                       id bigint NOT NULL,
                                       rotation_angle real NOT NULL,
                                       scale real NOT NULL,
                                       x real NOT NULL,
                                       y real NOT NULL,
                                       user_car_id bigint,
                                       vinyl_id bigint
);


ALTER TABLE public.user_car_vinyl OWNER TO ivan;

--
-- Name: user_car_vinyl_id_seq; Type: SEQUENCE; Schema: public; Owner: ivan
--

CREATE SEQUENCE public.user_car_vinyl_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_car_vinyl_id_seq OWNER TO ivan;

--
-- Name: user_car_vinyl_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ivan
--

ALTER SEQUENCE public.user_car_vinyl_id_seq OWNED BY public.user_car_vinyl.id;


--
-- Name: user_friends; Type: TABLE; Schema: public; Owner: ivan
--

CREATE TABLE public.user_friends (
                                     user_id bigint NOT NULL,
                                     friend_id bigint NOT NULL
);


ALTER TABLE public.user_friends OWNER TO ivan;

--
-- Name: users; Type: TABLE; Schema: public; Owner: ivan
--

CREATE TABLE public.users (
                              id bigint NOT NULL,
                              first_name character varying(255),
                              fuel integer NOT NULL,
                              fuel_tank_level integer NOT NULL,
                              garage_capacity integer NOT NULL,
                              last_name character varying(255),
                              level integer NOT NULL,
                              loses_count integer NOT NULL,
                              money integer NOT NULL,
                              money_spend integer NOT NULL,
                              money_won integer NOT NULL,
                              races_count integer NOT NULL,
                              role character varying(255),
                              telegram_id bigint,
                              username character varying(255),
                              was_called_count integer NOT NULL,
                              wins_count integer NOT NULL,
                              current_car_id bigint
);


ALTER TABLE public.users OWNER TO ivan;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: ivan
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO ivan;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ivan
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: vinyl; Type: TABLE; Schema: public; Owner: ivan
--

CREATE TABLE public.vinyl (
                              id bigint NOT NULL,
                              name character varying(255),
                              price integer NOT NULL
);


ALTER TABLE public.vinyl OWNER TO ivan;

--
-- Name: vinyl_id_seq; Type: SEQUENCE; Schema: public; Owner: ivan
--

CREATE SEQUENCE public.vinyl_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vinyl_id_seq OWNER TO ivan;

--
-- Name: vinyl_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ivan
--

ALTER SEQUENCE public.vinyl_id_seq OWNED BY public.vinyl.id;


--
-- Name: car_model id; Type: DEFAULT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.car_model ALTER COLUMN id SET DEFAULT nextval('public.car_model_id_seq'::regclass);


--
-- Name: race id; Type: DEFAULT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.race ALTER COLUMN id SET DEFAULT nextval('public.race_id_seq'::regclass);


--
-- Name: user_car id; Type: DEFAULT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car ALTER COLUMN id SET DEFAULT nextval('public.user_car_id_seq'::regclass);


--
-- Name: user_car_vinyl id; Type: DEFAULT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car_vinyl ALTER COLUMN id SET DEFAULT nextval('public.user_car_vinyl_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: vinyl id; Type: DEFAULT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.vinyl ALTER COLUMN id SET DEFAULT nextval('public.vinyl_id_seq'::regclass);


--
-- Data for Name: car_model; Type: TABLE DATA; Schema: public; Owner: ivan
--

COPY public.car_model (id, brand, default_color, level, model, power, price) FROM stdin;
1	ЗАЗ	#A9D5D7	1	965	27	1000
2	ВАЗ	#7F2929	1	21093	72	3000
3	AUDI	#ff0000	5	R8	420	130000
4	PORSCHE	#FDAC1A	8	911 GT 2 RS	620	380000
\.




--
-- Data for Name: vinyl; Type: TABLE DATA; Schema: public; Owner: ivan
--

COPY public.vinyl (id, name, price) FROM stdin;
1	fire	2500
\.


--
-- Name: car_model_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.car_model_id_seq', 1, true);


--
-- Name: race_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.race_id_seq', 10, true);


--
-- Name: user_car_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.user_car_id_seq', 7, true);


--
-- Name: user_car_vinyl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.user_car_vinyl_id_seq', 14, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.users_id_seq', 7, true);


--
-- Name: vinyl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.vinyl_id_seq', 1, false);


--
-- Name: car_model car_model_pkey; Type: CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.car_model
    ADD CONSTRAINT car_model_pkey PRIMARY KEY (id);


--
-- Name: race race_pkey; Type: CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.race
    ADD CONSTRAINT race_pkey PRIMARY KEY (id);


--
-- Name: user_car user_car_pkey; Type: CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car
    ADD CONSTRAINT user_car_pkey PRIMARY KEY (id);


--
-- Name: user_car_vinyl user_car_vinyl_pkey; Type: CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car_vinyl
    ADD CONSTRAINT user_car_vinyl_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: vinyl vinyl_pkey; Type: CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.vinyl
    ADD CONSTRAINT vinyl_pkey PRIMARY KEY (id);


--
-- Name: user_car_vinyl fk112qch8e9i0ek6dp5h82efio5; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car_vinyl
    ADD CONSTRAINT fk112qch8e9i0ek6dp5h82efio5 FOREIGN KEY (vinyl_id) REFERENCES public.vinyl(id);


--
-- Name: user_friends fk11y5boh1e7gh60rdqixyetv3x; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_friends
    ADD CONSTRAINT fk11y5boh1e7gh60rdqixyetv3x FOREIGN KEY (friend_id) REFERENCES public.users(id);


--
-- Name: race fk3n61l4n71cye3yhvnxwwgmfii; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.race
    ADD CONSTRAINT fk3n61l4n71cye3yhvnxwwgmfii FOREIGN KEY (friend_id) REFERENCES public.users(id);


--
-- Name: users fk3sci4ub1602pm4gd1hkc7ohrt; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk3sci4ub1602pm4gd1hkc7ohrt FOREIGN KEY (current_car_id) REFERENCES public.user_car(id);


--
-- Name: user_car fk7qayctf1mgegb7ghekwnw20e9; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car
    ADD CONSTRAINT fk7qayctf1mgegb7ghekwnw20e9 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: user_car fke1lixpj7rtvgenj260sp00r37; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car
    ADD CONSTRAINT fke1lixpj7rtvgenj260sp00r37 FOREIGN KEY (car_id) REFERENCES public.car_model(id);


--
-- Name: user_car_vinyl fkfid7c0kl250itdpp2qrmh2g2m; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_car_vinyl
    ADD CONSTRAINT fkfid7c0kl250itdpp2qrmh2g2m FOREIGN KEY (user_car_id) REFERENCES public.user_car(id);


--
-- Name: user_friends fkk08ugelrh9cea1oew3hgxryw2; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.user_friends
    ADD CONSTRAINT fkk08ugelrh9cea1oew3hgxryw2 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: race fkn2e3ga76gso47j56s88rya1sj; Type: FK CONSTRAINT; Schema: public; Owner: ivan
--

ALTER TABLE ONLY public.race
    ADD CONSTRAINT fkn2e3ga76gso47j56s88rya1sj FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

