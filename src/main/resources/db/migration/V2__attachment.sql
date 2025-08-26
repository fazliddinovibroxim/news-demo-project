
CREATE SCHEMA IF NOT EXISTS attachments;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Attachment table
CREATE TABLE attachment (
                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

                            created_by_id UUID REFERENCES auth_user(id),
                            updated_by_id UUID REFERENCES auth_user(id),

                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            server_name VARCHAR(255) NOT NULL,
                            content_type VARCHAR(100) NOT NULL,
                            file_url VARCHAR(500) NOT NULL,
                            size BIGINT NOT NULL,
                            is_public BOOLEAN DEFAULT FALSE
);

CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_attachment_updated_at
    BEFORE UPDATE ON attachment
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
