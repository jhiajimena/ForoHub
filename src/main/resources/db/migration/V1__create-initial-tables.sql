-- V1__create_initial_tables.sql

-- Table: category
CREATE TABLE category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Table: sub_category
CREATE TABLE sub_category(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category_id INTEGER NOT NULL,
    CONSTRAINT fk_subcategory_category FOREIGN KEY (category_id) REFERENCES category(id)
);

--Table: course
CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    description TEXT,
    subcategory_id INTEGER NOT NULL,
    CONSTRAINT fk_course_subcategory FOREIGN KEY (subcategory_id) REFERENCES sub_category(id)
);

--Table: profile
CREATE TABLE profile (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

--Table: permission
CREATE TABLE permission (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

--Table: profile_permission (many-to-many join table)
CREATE TABLE profile_permission(
    profile_id INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    PRIMARY KEY (profile_id, permission_id),
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES profile(id),
    CONSTRAINT fk_permission FOREIGN KEY (permission_id) REFERENCES permission(id)
);

--Table : user
CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR (500) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR (200) NOT NULL,
    profile_id INTEGER NOT NULL,
    CONSTRAINT fk_user_profile FOREIGN KEY (profile_id) REFERENCES profile(id)
    );

 --Table: topic
 CREATE TYPE TopicStatus AS ENUM ('UNANSWERED', 'ANSWERED', 'RESOLVED');
 CREATE TABLE topic(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    type VARCHAR(50) NOT NULL,
    message TEXT,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status TopicStatus NOT NULL DEFAULT 'UNANSWERED',
    author_id BIGINT NOT NULL,
    course_id INTEGER,
    CONSTRAINT fk_topic_user FOREIGN KEY (author_id) REFERENCES "user" (id),
    CONSTRAINT fk_topic_course FOREIGN KEY (course_id) REFERENCES course(id)
);

--Table: response
CREATE TABLE response (
    id BIGSERIAL PRIMARY KEY,
    message TEXT,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_solution BOOLEAN NOT NULL DEFAULT FALSE,
    topic_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT fk_response_topic FOREIGN KEY (topic_id) REFERENCES topic (id),
    CONSTRAINT fk_response_user FOREIGN KEY (author_id) REFERENCES "user" (id)
);