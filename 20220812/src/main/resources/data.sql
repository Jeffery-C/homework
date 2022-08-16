INSERT INTO base_meal (name, price, description) VALUES ('hamburger', 69, 'This is a delicious burger.');
INSERT INTO base_meal (name, price, description) VALUES ('tea', 30, 'Cool and quench thirst.');
INSERT INTO base_meal (name, price, description) VALUES ('sprite', 35, 'Cool and quench thirst.');
INSERT INTO base_meal (name, price, description) VALUES ('fries', 49, 'Fantastic fries.');

INSERT INTO _order (waiter) VALUES ('Jack');
INSERT INTO meal (quantity, base_meal_id) VALUES (2, 1); -- id=1, 2 hamburger
INSERT INTO meal (quantity, base_meal_id) VALUES (1, 3); -- id=2, 1 sprite
--INSERT INTO orders_meal (order_id, meal_id) VALUES (1, 1);
--INSERT INTO orders_meal (order_id, meal_id) VALUES (1, 2);
INSERT INTO meals_order (meal_id, order_id) VALUES (1, 1); -- 2 hamburger
INSERT INTO meals_order (meal_id, order_id) VALUES (2, 1); -- 1 sprite

INSERT INTO _order (waiter) VALUES ('Mary');
INSERT INTO meal (quantity, base_meal_id) VALUES (1, 4); -- id=3, 1 fries
INSERT INTO meal (quantity, base_meal_id) VALUES (1, 2); -- id=4, 1 tea
--INSERT INTO orders_meal (order_id, meal_id) VALUES (2, 4);
--INSERT INTO orders_meal (order_id, meal_id) VALUES (2, 3);
INSERT INTO meals_order (meal_id, order_id) VALUES (4, 2); -- 1 tea
INSERT INTO meals_order (meal_id, order_id) VALUES (3, 2); -- 1 fries

INSERT INTO _order (waiter) VALUES ('John');
INSERT INTO meal (quantity, base_meal_id) VALUES (1, 1); -- id=5, 1 hamburger
--INSERT INTO orders_meal (order_id, meal_id) VALUES (3, 5);
--INSERT INTO orders_meal (order_id, meal_id) VALUES (3, 3);
--INSERT INTO orders_meal (order_id, meal_id) VALUES (3, 2);
INSERT INTO meals_order (meal_id, order_id) VALUES (5, 3); -- 1 hamburger
INSERT INTO meals_order (meal_id, order_id) VALUES (3, 3); -- 1 fries
INSERT INTO meals_order (meal_id, order_id) VALUES (2, 3); -- 1 sprite



