CREATE TABLE IF NOT EXISTS user
(
    id                  int          NOT NULL AUTO_INCREMENT,
    name                varchar(255) NOT NULL,
    email               varchar(255) NOT NULL,
    password_hash       varchar(255) NOT NULL,
    encrypted_api_token varchar(255) NOT NULL,
    created_date        datetime DEFAULT NULL,
    last_modified_date  datetime DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_email (email)
) ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS terminal
(
    id                 int          NOT NULL AUTO_INCREMENT,
    owner_id           int          NOT NULL,
    name               varchar(255) DEFAULT NULL,
    city               varchar(255) NOT NULL,
    street             varchar(255) NOT NULL,
    house              varchar(255) NOT NULL,
    mac_address        varchar(255) NOT NULL,
    created_date       datetime     DEFAULT NULL,
    last_modified_date datetime     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_terminal_owner_id
        FOREIGN KEY (owner_id)
            REFERENCES user (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS status
(
    id                 int NOT NULL AUTO_INCREMENT,
    terminal_id        int NOT NULL,
    created_date       datetime DEFAULT NULL,
    last_modified_date datetime DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_status_terminal_id
        FOREIGN KEY (terminal_id)
            REFERENCES terminal (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS status_history
(
    id                 bigint NOT NULL AUTO_INCREMENT,
    terminal_id        int    NOT NULL,
    created_date       datetime DEFAULT NULL,
    last_modified_date datetime DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_status_history_terminal_id
        FOREIGN KEY (terminal_id)
            REFERENCES terminal (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
