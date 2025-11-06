DO $$
BEGIN
    IF '${addMockData}' = 'true' THEN
        INSERT INTO url.slugs (slug, target)
        VALUES ('1fd33d239faf4cd4', 'https://www.google.com/');

        INSERT INTO url.slugs (slug, target)
        VALUES ('2fd33d239faf4cd4', 'https://www.yahoo.com/');

        INSERT INTO url.redirect_events (slug_id, user_agent, created)
        VALUES (1, 'Mock Data', '2025-11-05 16:52:51.048793');

        INSERT INTO url.redirect_events (slug_id, user_agent, created)
        VALUES (1, 'Mock Data', '2025-11-04 16:52:51.048793');

        INSERT INTO url.redirect_events (slug_id, user_agent, created)
        VALUES (1, 'Mock Data', '2025-11-04 14:52:51.048793');

        INSERT INTO url.redirect_events (slug_id, user_agent, created)
        VALUES (1, 'Mock Data', '2025-11-02 16:52:51.048793');

        INSERT INTO url.redirect_events (slug_id, user_agent, created)
        VALUES (1, 'Mock Data', '2025-11-01 16:52:51.048793');

        INSERT INTO url.redirect_events (slug_id, user_agent, created)
        VALUES (1, 'Mock Data', '2025-10-25 16:52:51.048793');
    END IF;
END $$;
