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
    level integer NOT NULL,
    model character varying(255),
    power integer NOT NULL,
    price integer NOT NULL,
    default_color character varying(255)
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
    user_car_horsepower integer NOT NULL,
    user_won boolean NOT NULL,
    race_time TIMESTAMP,
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
    tires_level integer NOT NULL,
    car_id bigint,
    user_id bigint,
    power integer NOT NULL
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
    rotation_angle REAL NOT NULL,
    x REAL NOT NULL,
    y REAL NOT NULL,
    scale REAL NOT NULL,
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
    loses_count integer NOT NULL,
    money integer NOT NULL,
    money_spend integer NOT NULL,
    money_won integer NOT NULL,
    races_count integer NOT NULL,
    telegram_id bigint,
    telegram_username character varying(255),
    username character varying(255),
    was_called_count integer NOT NULL,
    wins_count integer NOT NULL,
    current_car_id bigint,
    level integer NOT NULL,
    role character varying(255)
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

COPY public.car_model (id, brand, level, model, power, price, default_color) FROM stdin;
1	ЗАЗ	1	965	27	1000	#A9D5D7
2	ВАЗ	1	21093	72	3000	#7F2929
3	AUDI	5	R8	420	130000	#ff0000
4	PORSCHE	8	911 GT 2 RS	620	380000	#FDAC1A
\.


--
-- Data for Name: race; Type: TABLE DATA; Schema: public; Owner: ivan
--

COPY public.race (id, friend_car_horsepower, user_car_horsepower, user_won, friend_id, user_id) FROM stdin;
1	0	0	f	2	1
2	0	0	f	2	1
3	0	0	f	2	1
4	0	0	f	2	1
5	0	0	f	2	1
6	0	420	t	1	2
7	0	420	t	1	2
8	0	420	t	1	2
9	0	420	t	1	2
10	0	420	t	1	2
11	0	420	t	1	2
12	0	420	t	1	2
13	0	420	t	1	4
\.


--
-- Data for Name: user_car; Type: TABLE DATA; Schema: public; Owner: ivan
--

COPY public.user_car (id, absorber_level, brakes_level, color, exhaust_level, intercooler_level, tires_level, car_id, user_id, power) FROM stdin;
2	0	0	\N	0	0	0	1	2	0
3	0	0	#00ff00	0	0	0	3	2	420
1	0	0	#333333	0	0	0	1	1	0
4	0	0	#A9D5D7	0	0	0	1	3	27
5	0	0	#A9D5D7	0	0	0	1	4	27
6	0	0	#00ff00	0	0	0	4	4	620
7	0	0	#A9D5D7	0	0	0	1	7	27
8	0	0	#A9D5D7	0	0	0	1	6	27
9	0	0	#00ff33	0	0	0	3	4	420
\.


--
-- Data for Name: user_car_vinyl; Type: TABLE DATA; Schema: public; Owner: ivan
--

COPY public.user_car_vinyl (id, rotation_angle, x, y, user_car_id, vinyl_id) FROM stdin;
1	0	700	300	9	1
\.


--
-- Data for Name: user_friends; Type: TABLE DATA; Schema: public; Owner: ivan
--

COPY public.user_friends (user_id, friend_id) FROM stdin;
1	2
2	1
2	2
2	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: ivan
--

COPY public.users (id, first_name, fuel, fuel_tank_level, garage_capacity, last_name, loses_count, money, money_spend, money_won, races_count, telegram_id, telegram_username, username, was_called_count, wins_count, current_car_id, level, role) FROM stdin;
2	Vanek	90	0	0		0	5700	0	3500	1	1	\N	Tox1n	5	7	2	0	USER
3	\N	100	0	0	\N	0	4200	0	0	0	\N	\N	tox1n71	0	0	4	0	USER
7	Tox1n	100	0	0		0	4200	0	0	0	4	\N	tox1n71	0	0	7	0	USER
6	Tox1n	100	0	0		0	4200	0	0	0	4	\N	tox1n71	0	0	8	0	USER
4	Tox1n	90	0	0		0	444500	0	500	1	556023311	\N	tox1n71	0	1	5	0	USER
1	Sanek	100	0	0	Shadruhin	5	2200	0	0	0	0	\N	SSH52	8	0	1	0	USER
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

SELECT pg_catalog.setval('public.race_id_seq', 13, true);


--
-- Name: user_car_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.user_car_id_seq', 8, true);


--
-- Name: user_car_vinyl_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ivan
--

SELECT pg_catalog.setval('public.user_car_vinyl_id_seq', 1, false);


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

