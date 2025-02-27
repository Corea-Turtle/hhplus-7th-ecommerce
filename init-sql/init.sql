USE hhplus;

INSERT INTO user (name, balance) VALUES ('사용자잔고0', 0);
INSERT INTO user (name, balance) VALUES ('사용자잔고1000', 1000);

INSERT INTO coupon (type, value_of_type, remain_quantity, coupon_state, expired_date, create_date)
VALUES ('RATE', 10, 30, true, "2025-03-07", '2025-02-07'); --expired_date null인 경우 어떻게 처리할지..

INSERT INTO coupon (type, value_of_type, remain_quantity, coupon_state, expired_date, create_date)
VALUES ('DISCOUNT', 100, 30, true, '2025-03-07', '2025-02-07');

INSERT INTO user_coupon (user_id, coupon_id, state, issued_time, used_time)
VALUES (1, 1, 'AVAILABLE', '2025-02-12 12:00:00', NULL);

INSERT INTO user_coupon (user_id, coupon_id, state, issued_time, used_time)
VALUES (1, 2, 'AVAILABLE', '2025-02-12 12:00:00', NULL);


INSERT INTO statics (product_id, product_name, sales_volume, sales_date)
SELECT FLOOR(1 + (RAND() * 1000)),
       CONCAT('Product ', FLOOR(1 + (RAND() * 1000))),
       FLOOR(RAND() * 500),
       CURDATE() - INTERVAL FLOOR(RAND() * 365) DAY
FROM (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t1,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t2,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t3,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t4,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t5,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t6,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t7,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t8,
     (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) t9;