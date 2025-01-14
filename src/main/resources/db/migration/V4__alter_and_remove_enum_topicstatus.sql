-- V4__alter_and_remove_enum_topic_status.sql

-- Change the 'status' column from ENUM to VARCHAR
ALTER TABLE topic
    ALTER COLUMN status TYPE VARCHAR(50) USING status::VARCHAR;

-- Set the default value for the 'status' column
ALTER TABLE topic
    ALTER COLUMN status SET DEFAULT 'UNANSWERED';

-- Drop the ENUM type 'TopicStatus' if it exists
    DROP TYPE IF EXISTS TopicStatus;