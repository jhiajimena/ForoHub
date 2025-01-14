-- V2__add_unique_and_notnull_to_topic_and_response_title_and_message.sql

-- Make 'title' column in topic table unique
ALTER TABLE topic
    ADD CONSTRAINT title_unique UNIQUE (title);

-- Make 'message' column in topic table not null and unique
ALTER TABLE topic
    ALTER COLUMN message SET NOT NULL,
    ADD CONSTRAINT message_unique UNIQUE (message);

-- Make 'message' column in response table not null
ALTER TABLE response
    ALTER COLUMN message SET NOT NULL;

-- Make 'name' column in category table unique
ALTER TABLE category
    ADD CONSTRAINT name_category_unique UNIQUE (name);

-- Make 'name' column in subcategory table unique
ALTER TABLE sub_category
    ADD CONSTRAINT name_subcategory_unique UNIQUE (name);

-- Make 'name' column in course table unique
ALTER TABLE course
    ADD CONSTRAINT name_course_unique UNIQUE (name);