DO $$
    DECLARE
        now_utc TIMESTAMP := timezone('utc', now());
        category_id_comercial int := 1;
        category_id_industrial int := 2;
        category_id_particular int := 3;
        category_id_publico int := 4;

    BEGIN
        UPDATE water_tariff_table
        SET end_time_utc = now_utc
        WHERE end_time_utc IS NULL;

        WITH new_water_tariff_tables AS (
            INSERT INTO water_tariff_table (client_category_id, start_time_utc)
                VALUES
                    (1, now_utc),
                    (2, now_utc),
                    (3, now_utc),
                    (4, now_utc)
                RETURNING id, client_category_id
        )
        INSERT INTO water_tariff_table_range (water_tariff_table_id, range_start_in_m3, range_end_in_m3, price_per_m3_in_cents)
        SELECT
            new_tables.id,
            new_table_ranges.range_start_in_m3,
            new_table_ranges.range_end_in_m3,
            new_table_ranges.price_per_m3_in_cents
        FROM new_water_tariff_tables new_tables
                 CROSS JOIN LATERAL (
            SELECT *
            FROM (VALUES
                      (category_id_comercial, 0, 10, 617),
                      (category_id_comercial, 11, 20, 419),
                      (category_id_comercial, 21, 30, 450),
                      (category_id_comercial, 31, NULL, 500),
                      (category_id_industrial, 0, 10, 100),
                      (category_id_industrial, 11, 20, 200),
                      (category_id_industrial, 21, 30, 300),
                      (category_id_industrial, 31, NULL, 400),
                      (category_id_particular, 0, 10, 617),
                      (category_id_particular, 11, 20, 419),
                      (category_id_particular, 21, 30, 450),
                      (category_id_particular, 31, NULL, 500),
                      (category_id_publico, 0, 10, 100),
                      (category_id_publico, 11, 20, 200),
                      (category_id_publico, 21, 30, 400),
                      (category_id_publico, 31, NULL, 800)
                 ) new_ranges (client_category_id, range_start_in_m3, range_end_in_m3, price_per_m3_in_cents)
            WHERE new_tables.client_category_id = new_ranges.client_category_id
            ) new_table_ranges;
    END $$;
