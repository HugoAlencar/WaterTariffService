INSERT INTO client_category (id, name)
    VALUES
        (1, 'COMERCIAL'),
        (2, 'INDUSTRIAL'),
        (3, 'PARTICULAR'),
        (4, 'PÚBLICO')
    ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name;