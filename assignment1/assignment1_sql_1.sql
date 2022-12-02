create table College(cName varchar(20), state char(2), enrollment int);

create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int);

create table Apply(sID int, cName varchar(20), major varchar(20), decision char);

insert into College values ('Stanford', 'CA', 15000);
insert into College values ('Berkeley', 'CA', 36000);
insert into College values ('MIT', 'MA', 10000);
insert into College values ('Cornell', 'NY', 21000);
insert into Student values (123, 'Amy', 3.9, 1000);
insert into Student values (234, 'Bob', 3.6, 1500);
insert into Student values (345, 'Craig', 3.5, 500);
insert into Student values (456, 'Doris', 3.9, 1000);
insert into Student values (567, 'Edward', 2.9, 2000);
insert into Student values (678, 'Fay', 3.8, 200);
insert into Student values (789, 'Gary', 3.4, 800);
insert into Student values (987, 'Helen', 3.7, 800);
insert into Student values (876, 'Irene', 3.9, 400);
insert into Student values (765, 'Jay', 2.9, 1500);
insert into Student values (654, 'Amy', 3.9, 1000);
insert into Student values (543, 'Craig', 3.4, 2000);
insert into Apply values (123, 'Stanford', 'CS', 'Y');
insert into Apply values (123, 'Stanford', 'EE', 'N');
insert into Apply values (123, 'Berkeley', 'CS', 'Y');
insert into Apply values (123, 'Cornell', 'EE', 'Y');
insert into Apply values (234, 'Berkeley', 'biology', 'N');
insert into Apply values (345, 'MIT', 'bioengineering', 'Y');
insert into Apply values (345, 'Cornell', 'bioengineering', 'N');
insert into Apply values (345, 'Cornell', 'CS', 'Y');
insert into Apply values (345, 'Cornell', 'EE', 'N');
insert into Apply values (678, 'Stanford', 'history', 'Y');
insert into Apply values (987, 'Stanford', 'CS', 'Y');
insert into Apply values (987, 'Berkeley', 'CS', 'Y');
insert into Apply values (876, 'Stanford', 'CS', 'N');
insert into Apply values (876, 'MIT', 'biology', 'Y');
insert into Apply values (876, 'MIT', 'marine biology', 'N');
insert into Apply values (765, 'Stanford', 'history', 'Y');
insert into Apply values (765, 'Cornell', 'history', 'N');
insert into Apply values (765, 'Cornell', 'psychology', 'Y');
insert into Apply values (543, 'MIT', 'CS', 'N');

select sID, sName
from Student
where GPA > 3.6;

select distinct sName, major
from Student, Apply
where Student.sID = Apply.sID;

select sName, GPA, decision
from Student, Apply
where Student.sID = Apply.sID and sizeHS < 1000 and
major = ‘CS’ and cName = ‘Stanford’;

select distinct College.cName
from College, Apply
where College.cName = Apply.cName
and enrollment > 20000
and major = ‘CS’;

select Student.sID, sName, GPA, Apply.cName, enrollment
from Student, College, Apply
where Apply.sID = Student.sID and Apply.cName = College.cName
order by GPA desc;
 
select *
from Apply
where major like ‘%bio%’;

select *
from Student, College  /* cross product */ 

select sID, sName, GPA, sizeHS,
GPA*(sizeHS/1000.0) as scaledGPA
from Student;

select S.sID, sName, GPA, A.cName, enrollment
from Student S, College C, Apply A
where A.sID = S.sID and A.cName = C.cName; 

select S1.sID, S1.sName, S1.GPA, S2.sID, S2.sName, S2.GPA
from Student S1, Student S2
where S1.GPA = S2.GPA and S1.sID < S2.sID;  /* <> */

select cName as name from College
union
select sName as name from Student; 

select cName as name from College
Union all
select sName as name from Student
order by name; 
 
select sID from Apply where major = 'CS'
intersect
select sID from Apply where major = 'EE'; 

select distinct A1.sID
from Apply A1, Apply A2
where A1.sID = A2.sID and A1.major = 'CS'
and A2.major = ‘EE’; 

select sID from Apply where major ='CS'
except
select sID from Apply where major = 'EE';

select distinct A1.sID
from Apply A1, Apply A2
where A1.sID = A2.sID and A1.major = 'CS'
and A2.major <> 'EE'; /* wrong expression */

select sID, sName
from Student
where sID in (select sID from Apply where major = 'CS');

select distinct Student.sID, sName
from Student, Apply
where Student.sID = Apply.sID and major = 'CS'; 

select sName
from Student
where sID in (select sID from Apply where major = 'CS'); 

select distinct sName
from Student, Apply
where Student.sID = Apply.sID and major = 'CS'; /* wrong expression */

select GPA, sID
from Student
where sID in (select sID from Apply where major = 'CS'); 

select Student.sID, sName
from Student
where sID in (select sID from Apply where major = 'CS') and
sID not in (select sID from Apply where major = 'EE'); 

select cName
from College C1
where not exists (select * from College C2
where C2.enrollment > C1.enrollment); 

select cName
from College S1
where  enrollment < any (select enrollment from College S2
where S2.cName <> S1.cName); 


