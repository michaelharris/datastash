-- Database: datastash

-- DROP DATABASE datastash;

CREATE DATABASE datastash
  WITH OWNER = datastash
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_GB.UTF-8'
       LC_CTYPE = 'en_GB.UTF-8'
       CONNECTION LIMIT = -1;
       
       
CREATE TABLE simpledocument (
    ID integer bigserial primary key,
    CONTENT text NOT NULL,
      ORIGIN varchar(50) NOT NULL,
       ORIGINTYPE varchar(50) NOT NULL,
    DESCRIPTION varchar(200) NOT NULL,
    CREATED timestamp default NULL,
    PUBLISHED timestamp default NULL
    
);

ALTER TABLE simpledocument ALTER COLUMN CREATED SET DEFAULT now();

-- GRANT ALL PRIVILEGES ON TABLE simpledocument TO datastash;
