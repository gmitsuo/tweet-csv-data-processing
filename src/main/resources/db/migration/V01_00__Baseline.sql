CREATE TABLE public.targets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE PUBLIC.insults (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500) UNIQUE
);

CREATE TABLE public.tweets (
     id BIGINT PRIMARY KEY,
     date DATE NOT NULL,
     tweet VARCHAR(500) NOT NULL,
     insult_id BIGINT NOT NULL,
     target_id BIGINT NOT NULL,
     CONSTRAINT fk_target FOREIGN KEY(target_id) REFERENCES targets(id),
     CONSTRAINT fk_insult FOREIGN KEY(insult_id) REFERENCES insults(id)
);

CREATE INDEX tweet_date_idx ON tweets (date);
