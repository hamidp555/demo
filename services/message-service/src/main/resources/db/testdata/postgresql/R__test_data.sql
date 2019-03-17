INSERT INTO messages (id, version, created_date, last_modified_date, message, properties) VALUES
(1, 0,  now(),  now(),'message 1', E'{\"isPalindrome\": \"flase\"}'),
(2, 0,  now(),  now(), 'message 2', E'{\"isPalindrome\": \"flase\"}'),
(3, 0,  now(),  now(), 'message 3', E'{\"isPalindrome\": \"flase\"}'),
(4, 0,  now(),  now(), 'message 4', E'{\"isPalindrome\": \"flase\"}')
ON CONFLICT DO NOTHING;

ALTER SEQUENCE hibernate_sequence RESTART with 5;