-- Ensure pgcrypto extension
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Ensure schema exists
CREATE SCHEMA IF NOT EXISTS news_schema;

CREATE TYPE news_status AS ENUM (
    'DRAFT',
    'REVIEW',
    'PUBLISHED',
    'UNPUBLISHED',
    'ARCHIVED'
    );

CREATE TABLE IF NOT EXISTS news (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_by_id UUID,
    updated_by_id UUID,
    category_id UUID,
    cover_media_id UUID,
    status news_status NOT NULL,
    is_featured BOOLEAN NOT NULL DEFAULT FALSE,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    publish_at TIMESTAMP,
    unpublish_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    CONSTRAINT fk_news_created_by FOREIGN KEY (created_by_id) REFERENCES auth_user(id),
    CONSTRAINT fk_news_updated_by FOREIGN KEY (updated_by_id) REFERENCES auth_user(id),
    CONSTRAINT fk_news_category FOREIGN KEY (category_id) REFERENCES category(id)
    );

CREATE TABLE news_translation (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                  news_id UUID NOT NULL REFERENCES news(id) ON DELETE CASCADE,
                                  lang VARCHAR(10) NOT NULL,

                                  title VARCHAR(500) NOT NULL,
                                  slug VARCHAR(500) NOT NULL,
                                  summary TEXT,
                                  content TEXT NOT NULL, -- HTML

                                  meta_title VARCHAR(255),
                                  meta_description VARCHAR(500),

                                  created_by_id UUID REFERENCES auth_user(id),
                                  updated_by_id UUID REFERENCES auth_user(id),

                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT uq_news_lang UNIQUE (news_id, lang),
                                  CONSTRAINT uq_slug_lang UNIQUE (slug, lang)
);


CREATE TABLE news_tag (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          news_id UUID NOT NULL REFERENCES news(id) ON DELETE CASCADE,
                          tag_id UUID NOT NULL REFERENCES tag(id) ON DELETE CASCADE,

                          created_by_id UUID REFERENCES auth_user(id),
                          updated_by_id UUID REFERENCES auth_user(id),

                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT uq_news_tag UNIQUE (news_id, tag_id)
);
CREATE TABLE news_history (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              news_id UUID NOT NULL REFERENCES news(id) ON DELETE CASCADE,
                              changed_by UUID NOT NULL REFERENCES auth_user(id),

                              from_status VARCHAR(50) NOT NULL,
                              to_status   VARCHAR(50) NOT NULL,

                              diff_json   JSONB,
                              changed_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_news_status_publish_at ON news (status, publish_at DESC);

CREATE UNIQUE INDEX idx_news_translation_slug_lang ON news_translation (slug, lang);

CREATE INDEX idx_news_translation_tsv ON news_translation
    USING GIN (to_tsvector('simple', title || ' ' || summary || ' ' || content));

