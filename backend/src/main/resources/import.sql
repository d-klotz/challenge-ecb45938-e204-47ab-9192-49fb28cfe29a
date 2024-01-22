-- import.sql

-- Insert some default data into the Institution table
INSERT INTO institutions (id, name, type) VALUES
                                             ('5c5f5bc1-5ee4-49a0-9112-003c612e0826', 'Neo Bank', 'CENTRAL'),
                                             ('8162dacc-0b84-4bb7-b005-56b8bdee578e', 'Nubank', 'COOPERATIVA');

-- Insert some default data into the Event table
INSERT INTO events (id, name, start_date, end_date, institution_id, active) VALUES
                                                                               ('f677eea1-7721-4e00-91d1-0181b0f9fac7', 'Event 1', '2024-01-01', '2024-01-10', '5c5f5bc1-5ee4-49a0-9112-003c612e0826', true),
                                                                               ('5f07088c-6fa7-49cc-93f9-29e93a20f943', 'Event 2', '2024-02-01', '2024-02-10', '8162dacc-0b84-4bb7-b005-56b8bdee578e', false),
                                                                                ('fa528c85-46fc-4adb-9803-2865235cf748', 'Evento de Colaboradores', '2024-02-01', '2024-02-10', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('7b539b52-0100-4591-8b69-a436a032b38b', 'Novos Integrantes 2024', '2024-02-01', '2024-02-20', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('5c2150a4-f049-4285-a6b3-bc6d72d06f92', 'Divulgacao de resultados', '2024-02-01', '2024-02-15', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('f4d2d7da-1ab2-40b5-881f-81735e0b1b86', 'Clube do Livro do Cooperado', '2024-02-01', '2024-02-11', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('a5354ba9-bfb5-4f3b-b8df-d175857405a2', 'Feira Limpa Nome', '2024-02-01', '2024-02-21', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('7c8dca33-31d2-4afb-a07f-ae3631d287ce', 'Boas vindas novos integrantes', '2024-02-01', '2024-02-22', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('ac2586f2-6a57-4c16-aa4d-87464620bb62', 'Aniversariantes de Abril', '2024-02-01', '2024-02-02', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('ac93042c-bd22-4ce9-92d3-e9b1326dafc0', 'Festa da Firma', '2024-02-01', '2024-02-03', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true),
                                                                                ('ecb45938-e204-47ab-9192-49fb28cfe29a', 'Feira Agrotop 2024', '2024-02-01', '2024-02-05', '8162dacc-0b84-4bb7-b005-56b8bdee578e', true);
