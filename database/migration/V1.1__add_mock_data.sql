DO $$
BEGIN
    IF '${runMockData}' = 'true' THEN
        INSERT INTO url.slugs (slug, target)
        VALUES ('1fd33d239faf4cd4871eb535f203b68d', 'https://www.google.com');

        INSERT INTO url.slugs (slug, target)
        VALUES ('2fd33d239faf4cd4871eb535f203b68d', 'https://www.yahoo.com');
    END IF;
END $$;
