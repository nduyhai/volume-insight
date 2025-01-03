INSERT INTO keyword (id, name)
VALUES (1, 'Keyword_1'),
       (2, 'Keyword_2'),
       (3, 'Keyword_3'),
       (4, 'Keyword_4'),
       (5, 'Keyword_5'),
       (6, 'Keyword_6'),
       (7, 'Keyword_7'),
       (8, 'Keyword_8'),
       (9, 'Keyword_9'),
       (10, 'Keyword_10');



INSERT INTO user_subscription (id, user_id, keyword_id, subscription_type, start_datetime,
                               end_datetime)
VALUES (1, 'e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b', 1, 'DAILY', '2025-01-01 00:00:00',
        '2025-03-01 00:00:00'),
       (2, 'e7b3aafe-60d7-4a8a-961b-6f6e5e89a10b', 2, 'HOURLY', '2025-01-01 00:00:00',
        '2025-03-01 00:00:00'),
       (3, '5f2bd0e6-4e6c-4021-90c8-16df7bfc9a2f', 3, 'DAILY', '2025-01-15 00:00:00',
        '2025-02-15 00:00:00'),
       (4, '5f2bd0e6-4e6c-4021-90c8-16df7bfc9a2f', 4, 'HOURLY', '2025-01-10 00:00:00',
        '2025-02-10 00:00:00'),
       (5, '0b35d894-37d9-4936-b769-cdb14f527bc1', 5, 'DAILY', '2025-01-20 00:00:00',
        '2025-02-20 00:00:00');
