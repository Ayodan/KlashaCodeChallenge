INSERT INTO country (id, name, population, capital_city, location, currency, iso2, iso3)
VALUES (1, 'Italy', 60000000, 'Rome', 'Europe', 'EUR', 'IT', 'ITA'),
       (2, 'Ghana', 30000000, 'Accra', 'Africa', 'USD', 'GH', 'GHA'),
       (3, 'New Zealand', 5000000, 'Wellington', 'Oceania', 'GBP', 'NZ', 'NZL');

INSERT INTO state (id, name, state_code, country_id)
VALUES (1, 'State1', 'ST1', 1),
       (2, 'State2', 'ST2', 2),
       (3, 'State3', 'ST3', 3);

INSERT INTO city (id, name, population, state_id)
VALUES (1, 'City1', 100000, 1),
       (2, 'City2', 200000, 2),
       (3, 'City3', 150000, 2);

INSERT INTO conversion_rate (id, source_currency, target_currency, rate)
VALUES (1, 'EUR', 'NGN', 493.06),
       (2, 'USD', 'NGN', 460.72),
       (3, 'JPY', 'NGN', 3.28),
       (4, 'GBP', 'NGN', 570.81),
       (5, 'EUR', 'UGX', 4),
       (6, 'USD', 'UGX', 3),
       (7, 'JPY', 'UGX', 26.62),
       (8, 'GBP', 'UGX', 4);




