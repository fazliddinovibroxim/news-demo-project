CREATE SCHEMA IF NOT EXISTS category_scheme;


CREATE TABLE IF NOT EXISTS category (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_by_id UUID,
    updated_by_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    parent_id UUID NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_created_by FOREIGN KEY (created_by_id) REFERENCES auth_user(id),
    CONSTRAINT fk_updated_by FOREIGN KEY (updated_by_id) REFERENCES auth_user(id)
    );

    ---- category translation ----
CREATE TABLE IF NOT EXISTS category_translation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_by_id UUID,
    updated_by_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    category_id UUID NOT NULL,
    lang VARCHAR(5) NOT NULL,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    description TEXT,
    CONSTRAINT uq_category_lang UNIQUE(category_id, lang),
    CONSTRAINT uq_slug_lang UNIQUE(slug, lang),
    CONSTRAINT fk_created_by FOREIGN KEY (created_by_id) REFERENCES auth_user(id),
    CONSTRAINT fk_updated_by FOREIGN KEY (updated_by_id) REFERENCES auth_user(id),
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
    );

   --- Tag sql code - ----
CREATE TABLE IF NOT EXISTS tag (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_by_id UUID,
    updated_by_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    code VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_tag_created_by FOREIGN KEY (created_by_id) REFERENCES auth_user(id),
    CONSTRAINT fk_tag_updated_by FOREIGN KEY (updated_by_id) REFERENCES auth_user(id)
    );

