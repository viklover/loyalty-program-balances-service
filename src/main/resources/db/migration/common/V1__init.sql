CREATE TABLE BALANCE
(
    id              UUID PRIMARY KEY,
    card_id         BIGSERIAL NOT NULL,
    value           INTEGER   NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);
