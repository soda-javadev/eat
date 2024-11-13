INSERT INTO event_template (id, template_name, event_name, type, minute, hour, day_of_week, day_of_month, active)
VALUES (1, 'agl1 12:00', 'agl1', 'DAILY', 59, 23, null, null, false);
INSERT INTO event_template (id, template_name, event_name, type, minute, hour, day_of_week, day_of_month, active)
VALUES (2, 'agl2 12:00', 'agl2', 'WEEKLY', 33, 13, 'MONDAY', null, true);
INSERT INTO event_template (id, template_name, event_name, type, minute, hour, day_of_week, day_of_month, active)
VALUES (3, 'agl3 12:00', 'agl3', 'MONTHLY', 18, 2, null, 3, true);
INSERT INTO event_template (id, template_name, event_name, type, minute, hour, day_of_week, day_of_month, active)
VALUES (4, 'agl4 12:00', 'agl4', 'DAILY', 12, 22, null, null, true);
INSERT INTO event_template (id, template_name, event_name, type, minute, hour, day_of_week, day_of_month, active)
VALUES (5, 'agl5 12:00', 'agl5', 'WEEKLY', 33, 13, 'FRIDAY', null, false);
INSERT INTO event_template (id, template_name, event_name, type, minute, hour, day_of_week, day_of_month, active)
VALUES (6, 'agl6 12:00', 'agl6', 'MONTHLY', 33, 13, null, 19, false);
INSERT INTO event_template (id, template_name, event_name, type, minute, hour, day_of_week, day_of_month, active)
VALUES (7, 'agl7 12:00', 'agl7', 'WEEKLY', 2, 3, 'WEDNESDAY', null, true);

SELECT SETVAL('event_template_seq', (SELECT MAX(id) FROM event_template));