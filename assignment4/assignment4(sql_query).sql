create table ParentOf(parent varchar(20), child varchar(20));

insert into ParentOf values('Alice', 'Carol');
insert into ParentOf values('Bob', 'Carol');
insert into ParentOf values('Carol', 'Dave');
insert into ParentOf values('Carol', 'George');
insert into ParentOf values('Dave', 'Mary');
insert into ParentOf values('Eve', 'Mary');
insert into ParentOf values('Mary', 'Frank');


create table Employee(ID int, salary int);
create table Manager(mId int, eID int);
create table Project(name varchar(20), mgrID int);

insert into Employee values(123, 100);
insert into Employee values(234, 90);
insert into Employee values(345, 80);
insert into Employee values(456, 70);
insert into Employee values(567, 60);

insert into Manager values(123, 234);
insert into Manager values(234, 345);
insert into Manager values(234, 456);
insert into Manager values(345, 567);

insert into Project values('X', 123);


create table Flight(orig varchar(20), dest varchar(20), airline varchar(20), cost int);

insert into Flight values('A', 'ORD', 'United', 200);
insert into Flight values('ORD', 'B', 'American', 100);
insert into Flight values('A', 'PHX', 'Southwest', 25);
insert into Flight values('PHX', 'LAS', 'Southwest', 30);
insert into Flight values('LAS', 'CMH', 'Frontier', 60);
insert into Flight values('CMH', 'B', 'Frontier', 60);
insert into Flight values('A', 'B', 'JetBlue', 195);


with RECURSIVE
	Ancestor(a,d) as (select parent as a, child as d from parentof
				  union
				  select Ancestor.a, ParentOf.child as d
				  from Ancestor, ParentOf
				  where Ancestor.d = ParentOf.parent)
	select a from Ancestor where d = 'Frank'
	order by a;

with RECURSIVE
	Superior as (select * from Manager
				union 
				select S.mID, M.eID
				from Superior S, Manager M
				where S.eID = M.mID)
select sum(salary)
from Employee
where ID in
	(select mgrID from Project where name = 'X'
	union
	select eID from Project, Superior
	where Project.name = 'X' AND Project.mgrID = Superior.mID)
	
with recursive
	FromA(dest,total) as
		(select dest, cost as total from Flight where orig = 'A'
		 union
		 select F.dest, cost+total as total
		 from FromA FA, Flight F
		 where FA.dest = F.orig)
select * from FromA
order by total;

with recursive
	FromA(dest,total) as
		(select dest, cost as total from Flight where orig = 'A'
		 union
		 select F.dest, cost+total as total
		 from FromA FA, Flight F
		 where FA.dest = F.orig)
select min(total) from FromA where dest = 'B';

create table Sales(storeID varchar(20), itemID varchar(20), custID varchar(20), price int);
create table Customer(custID varchar(20), cName varchar(20), gender char, age int);
create table Item(itemID varchar(20), category varchar(20), color varchar(20));
create table Store(storeID varchar(20), city varchar(20), county varchar(20), state char(2));

insert into Customer values('cust1', 'Amy', 'F', 20);
insert into Customer values('cust2', 'Bob', 'M', 21);
insert into Customer values('cust3', 'Craig', 'M', 25);
insert into Customer values('cust4', 'Doris', 'F', 22);

insert into Item values('item1', 'Tshirt', 'blue');
insert into Item values('item2', 'Jacket', 'blue');
insert into Item values('item3', 'Tshirt', 'red');
insert into Item values('item4', 'Jacket', 'blue');
insert into Item values('item5', 'Jacket', 'red');

insert into Store values('store1', 'Palo Alto', 'Santa Clara', 'CA');
insert into Store values('store2', 'Mountain View', 'Santa Clara', 'CA');
insert into Store values('store3', 'Menlo Park', 'San Mateo', 'CA');
insert into Store values('store4', 'Belmont', 'San Mateo', 'CA');
insert into Store values('store5', 'Seattle', 'King', 'WA');
insert into Store values('store6', 'Redmond', 'King', 'WA');


insert into Sales values('store1', 'item1', 'cust1', 10);
insert into Sales values('store1', 'item1', 'cust2', 15);
insert into Sales values('store1', 'item1', 'cust3', 20);
insert into Sales values('store1', 'item1', 'cust3', 25);
insert into Sales values('store1', 'item2', 'cust1', 30);
insert into Sales values('store1', 'item2', 'cust2', 35);
insert into Sales values('store1', 'item2', 'cust3', 40);
insert into Sales values('store1', 'item2', 'cust2', 45);
insert into Sales values('store1', 'item3', 'cust1', 50);
insert into Sales values('store1', 'item3', 'cust1', 55);
insert into Sales values('store2', 'item3', 'cust2', 60);
insert into Sales values('store2', 'item1', 'cust2', 65);
insert into Sales values('store2', 'item2', 'cust3', 70);
insert into Sales values('store2', 'item2', 'cust3', 75);
insert into Sales values('store2', 'item2', 'cust4', 80);
insert into Sales values('store2', 'item2', 'cust4', 85);
insert into Sales values('store2', 'item2', 'cust1', 90);
insert into Sales values('store2', 'item2', 'cust1', 95);
insert into Sales values('store2', 'item2', 'cust1', 95);
insert into Sales values('store2', 'item2', 'cust2', 90);

insert into Sales values('store3', 'item2', 'cust2', 85);
insert into Sales values('store3', 'item2', 'cust2', 80);
insert into Sales values('store3', 'item2', 'cust3', 75);
insert into Sales values('store3', 'item2', 'cust3', 70);
insert into Sales values('store3', 'item3', 'cust3', 65);
insert into Sales values('store3', 'item3', 'cust2', 60);
insert into Sales values('store3', 'item3', 'cust2', 55);
insert into Sales values('store3', 'item3', 'cust2', 50);
insert into Sales values('store3', 'item3', 'cust3', 45);
insert into Sales values('store3', 'item3', 'cust3', 40);

insert into Sales values('store4', 'item3', 'cust1', 35);
insert into Sales values('store4', 'item3', 'cust1', 30);
insert into Sales values('store4', 'item3', 'cust2', 25);
insert into Sales values('store4', 'item3', 'cust2', 20);
insert into Sales values('store4', 'item3', 'cust2', 15);
insert into Sales values('store4', 'item3', 'cust2', 10);
insert into Sales values('store4', 'item4', 'cust3', 15);
insert into Sales values('store4', 'item4', 'cust3', 20);
insert into Sales values('store4', 'item4', 'cust3', 25);
insert into Sales values('store4', 'item4', 'cust3', 30);

insert into Sales values('store5', 'item4', 'cust4', 35);
insert into Sales values('store5', 'item4', 'cust4', 40);
insert into Sales values('store5', 'item4', 'cust4', 45);
insert into Sales values('store5', 'item4', 'cust4', 50);
insert into Sales values('store5', 'item4', 'cust1', 55);
insert into Sales values('store5', 'item4', 'cust1', 60);
insert into Sales values('store5', 'item4', 'cust1', 65);
insert into Sales values('store5', 'item4', 'cust2', 70);
insert into Sales values('store5', 'item5', 'cust2', 75);
insert into Sales values('store5', 'item5', 'cust2', 80);

insert into Sales values('store6', 'item5', 'cust3', 85);
insert into Sales values('store6', 'item5', 'cust3', 90);
insert into Sales values('store6', 'item2', 'cust3', 95);
insert into Sales values('store6', 'item2', 'cust4', 90);
insert into Sales values('store6', 'item3', 'cust4', 85);
insert into Sales values('store6', 'item3', 'cust4', 80);
insert into Sales values('store6', 'item4', 'cust4', 75);
insert into Sales values('store6', 'item4', 'cust4', 70);
insert into Sales values('store6', 'item5', 'cust4', 65);
insert into Sales values('store6', 'item5', 'cust4', 60);

select storeID, itemID, custID, sum(price)
from Sales 
group by cube(storeID, itemID, custID )
order by storeID, itemID, custID;

select storeID, itemID, custID, sum(price)
from Sales F
group by cube(storeID, custID), itemID
order by storeID, itemID, custID;

select storeID, itemID, custID, sum(price)
from Sales F
group by rollup(storeID, itemID, custID)
order by storeID, itemID, custID;

select state, county, city, sum(price)
from Sales F, Store S
where F.storeID = S.storeID
group by rollup(state, county, city)
order by state, county, city;


