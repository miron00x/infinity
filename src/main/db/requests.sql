SELECT * FROM bills
JOIN users ON users.id = bills.user_id
JOIN services ON services.id = bills.service_id;  //all data

SELECT
	users.name,
	service.title,
	bills.bill,
	bills.paid
FROM bills
JOIN users ON users.id = bills.user_id
JOIN services ON services.id = bills.service_id; //credit