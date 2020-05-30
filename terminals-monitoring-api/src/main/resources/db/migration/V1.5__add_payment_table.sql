CREATE TABLE IF NOT EXISTS payment
(
    id                 int           NOT NULL AUTO_INCREMENT,
    external_id        varchar(255)  UNIQUE,
    terminal_id        int           NOT NULL,
    amount             bigint        NOT NULL,
    status             int           NOT NULL,
    created_date       datetime      DEFAULT NULL,
    last_modified_date datetime      DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_terminal_id
        FOREIGN KEY (terminal_id)
            REFERENCES terminal (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8;
