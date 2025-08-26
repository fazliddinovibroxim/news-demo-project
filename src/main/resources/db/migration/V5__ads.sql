CREATE SCHEMA IF NOT EXISTS ads;
CREATE TABLE ads_placement (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                               code VARCHAR(100) NOT NULL UNIQUE,
                               title VARCHAR(255) NOT NULL,
                               description TEXT,
                               is_active BOOLEAN NOT NULL DEFAULT TRUE,

                               created_by_id UUID REFERENCES auth_user(id),
                               updated_by_id UUID REFERENCES auth_user(id),

                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE ads_campaign (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              name VARCHAR(255) NOT NULL,
                              advertiser UUID REFERENCES auth_user(id),
                              status VARCHAR(20) NOT NULL CHECK (status IN ('DRAFT', 'ACTIVE', 'PAUSED', 'ENDED')),
                              start_at TIMESTAMP NOT NULL,
                              end_at TIMESTAMP,
                              daily_cap_impressions BIGINT,
                              daily_cap_clicks BIGINT,

                              created_by_id UUID REFERENCES auth_user(id),
                              updated_by_id UUID REFERENCES auth_user(id),
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE ads_creative (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              campaign_id UUID NOT NULL REFERENCES ads_campaign(id) ON DELETE CASCADE,
                              type VARCHAR(20) NOT NULL CHECK (type IN ('IMAGE', 'HTML')),
                              landing_url VARCHAR(500),
                              image_media_id UUID,
                              html_snippet TEXT,
                              is_active BOOLEAN NOT NULL DEFAULT TRUE,

                              created_by_id UUID REFERENCES auth_user(id),
                              updated_by_id UUID REFERENCES auth_user(id),
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT creative_content_check CHECK (
                                      (type = 'IMAGE' AND image_media_id IS NOT NULL AND html_snippet IS NULL)
                                      OR (type = 'HTML' AND html_snippet IS NOT NULL AND image_media_id IS NULL)
                                  )
);
CREATE TABLE ads_creative_translation (
                                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                          creative_id UUID NOT NULL REFERENCES ads_creative(id) ON DELETE CASCADE,
                                          lang VARCHAR(10) NOT NULL,
                                          title VARCHAR(255),
                                          alt_text VARCHAR(255),

                                          created_by_id UUID REFERENCES auth_user(id),
                                          updated_by_id UUID REFERENCES auth_user(id),
                                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                          CONSTRAINT uniq_creative_lang UNIQUE (creative_id, lang)
);
CREATE TABLE ads_assignment (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                placement_id UUID NOT NULL REFERENCES ads_placement(id) ON DELETE CASCADE,
                                campaign_id UUID NOT NULL REFERENCES ads_campaign(id) ON DELETE CASCADE,
                                creative_id UUID NOT NULL REFERENCES ads_creative(id) ON DELETE CASCADE,

                                weight INT NOT NULL DEFAULT 1,
                                lang_filter JSONB,
                                category_filter JSONB,

                                start_at TIMESTAMP NOT NULL,
                                end_at TIMESTAMP,
                                is_active BOOLEAN NOT NULL DEFAULT TRUE,

                                created_by_id UUID REFERENCES auth_user(id),
                                updated_by_id UUID REFERENCES auth_user(id),
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
