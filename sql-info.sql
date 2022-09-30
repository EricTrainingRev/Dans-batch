-- DDL
	-- constraints are often used with DDL: we will focus on the Primary Key constraint
create table users(
	user_id serial primary key, -- primary key places the "unique" and "not null" constraints on the column
	first_name varchar(20),
	last_name varchar(20),
	username varchar(20),
	pass varchar(20)
);

drop table users;

alter table users rename to customers;

-- DML

insert into customers values 
	(default,'Eric','Suminski','RevTrainer','12345'), -- seperate entries with a comma
	(default,'Ted','Lasso','football coach','alwaysHappy');
	
update "customers" set first_name = 'Dan' where last_name  = 'Suminski';
update "customers" set first_name = 'Eric' where user_id = 1;

-- % represents 0-any amount of characters, _ represents a single character
delete from customers where last_name like '%s%';

-- DQL 

select * from customers where first_name = 'Eric';
select username, pass from customers where user_id = 4;
select * from customers;
select username, pass from customers;

-- DCL handles creating users in your database and providing permissions for them

-- TCL is what allows your queries to be persisted

insert into customers values(default,'first','last','username','pass');