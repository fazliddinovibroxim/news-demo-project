
CREATE SCHEMA auth;

CREATE TABLE role (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      created_by_id UUID,
                      updated_by_id UUID,
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      app_role_name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE auth_user (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        username VARCHAR(255) NOT NULL,
        full_name VARCHAR(255) NOT NULL,
        email VARCHAR(255) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        email_code VARCHAR(255),
        reset_token VARCHAR(255),
        token_expiry_date TIMESTAMP,
        role_id UUID REFERENCES role(id),
        created_by_id UUID,
        updated_by_id UUID,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        is_account_non_expired BOOLEAN DEFAULT TRUE,
        is_account_non_locked BOOLEAN DEFAULT TRUE,
        is_credentials_non_expired BOOLEAN DEFAULT TRUE,
        is_enabled BOOLEAN DEFAULT FALSE
);

ALTER TABLE role
    ADD CONSTRAINT fk_role_created_by FOREIGN KEY (created_by_id) REFERENCES auth_user(id),
    ADD CONSTRAINT fk_role_updated_by FOREIGN KEY (updated_by_id) REFERENCES auth_user(id);

-- ALTER TABLE example_entity
--     ADD CONSTRAINT fk_created_by
--         FOREIGN KEY (created_by_id) REFERENCES auth_user(id);
--
-- ALTER TABLE example_entity
--     ADD CONSTRAINT fk_updated_by
--         FOREIGN KEY (updated_by_id) REFERENCES auth_user(id);


-- Trigger for auto-update updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_role_updated_at
    BEFORE UPDATE ON role
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Create table for appPermissions (ElementCollection)
CREATE TABLE role_app_permissions (
                                      role_id UUID NOT NULL REFERENCES role(id) ON DELETE CASCADE,
                                      permission VARCHAR(50) NOT NULL,
                                      PRIMARY KEY (role_id, permission)
);
