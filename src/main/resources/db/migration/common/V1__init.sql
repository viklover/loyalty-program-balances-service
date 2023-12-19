CREATE TABLE IF NOT EXISTS BALANCE
(
    id              UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    card_id         BIGSERIAL                NOT NULL,
    value           INTEGER                  NOT NULL,
    expiration_date TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at      TIMESTAMP                NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP                NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_balance_cardId ON BALANCE (card_id);
