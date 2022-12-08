//201821053 Kim Hyung Jun
//Database assignment 3

package postgresql;
import java.sql.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws SQLException
    {

    	String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "postgres";
    	
        final Connection con;
        final Statement stmt;
        ResultSet rs;

        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.println("SQL Programming Test");
            System.out.println("Connecting PostgreSQL database");
            
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            // connecting to PostgreSql database using JDBC 

            System.out.println("Creating College, Student, Apply relations");
            final String tableCreate = "create table College(cName varchar(20), state char(2), enrollment int);" +
                    				   "create table Student(sID int, sName varchar(20), GPA numeric(2,1), sizeHS int);" +
                    				   "create table Apply(sID int, cName varchar(20), major varchar(20), decision char);";
            
            stmt.execute(tableCreate); // creating 3 tables
            
            System.out.println("Inserting tuples to College, Student, Apply relations");
            final String insertData = 
            		"insert into College values ('Stanford', 'CA', 15000); "
            		+ "insert into College values ('Berkeley', 'CA', 36000); "
            		+ "insert into College values ('MIT', 'MA', 10000); "
            		+ "insert into College values ('Cornell', 'NY', 21000);\n" +
                    "insert into Student values (123, 'Amy', 3.9, 1000); "
                    + "insert into Student values (234, 'Bob', 3.6, 1500); "
                    + "insert into Student values (345, 'Craig', 3.5, 500); "
                    + "insert into Student values (456, 'Doris', 3.9, 1000); "
                    + "insert into Student values (567, 'Edward', 2.9, 2000); "
                    + "insert into Student values (678, 'Fay', 3.8, 200); "
                    + "insert into Student values (789, 'Gary', 3.4, 800); "
                    + "insert into Student values (987, 'Helen', 3.7, 800); "
                    + "insert into Student values (876, 'Irene', 3.9, 400); "
                    + "insert into Student values (765, 'Jay', 2.9, 1500); "
                    + "insert into Student values (654, 'Amy', 3.9, 1000); "
                    + "insert into Student values (543, 'Craig', 3.4, 2000);\n" +
                    "insert into Apply values (123, 'Stanford', 'CS', 'Y');\n" +
                    "insert into Apply values (123, 'Stanford', 'EE', 'N');\n" +
                    "insert into Apply values (123, 'Berkeley', 'CS', 'Y');\n" +
                    "insert into Apply values (123, 'Cornell', 'EE', 'Y');\n" +
                    "insert into Apply values (234, 'Berkeley', 'biology', 'N'); "
                    + "insert into Apply values (345, 'MIT', 'bioengineering', 'Y'); "
                    + "insert into Apply values (345, 'Cornell', 'bioengineering', 'N'); "
                    + "insert into Apply values (345, 'Cornell', 'CS', 'Y');\n" +
                    "insert into Apply values (345, 'Cornell', 'EE', 'N');\n" +
                    "insert into Apply values (678, 'Stanford', 'history', 'Y'); "
                    + "insert into Apply values (987, 'Stanford', 'CS', 'Y');\n" +
                    "insert into Apply values (987, 'Berkeley', 'CS', 'Y');\n" +
                    "insert into Apply values (876, 'Stanford', 'CS', 'N');\n" +
                    "insert into Apply values (876, 'MIT', 'biology', 'Y');\n" +
                    "insert into Apply values (876, 'MIT', 'marine biology', 'N'); "
                    + "insert into Apply values (765, 'Stanford', 'history', 'Y'); "
                    + "insert into Apply values (765, 'Cornell', 'history', 'N'); "
                    + "insert into Apply values (765, 'Cornell', 'psychology', 'Y'); "
                    + "insert into Apply values (543, 'MIT', 'CS', 'N');";
            stmt.execute(insertData); // inserting data 
            System.out.println("Trigger Test1");
       
                     
            String trigger1 = "create or replace FUNCTION r2() returns TRIGGER as $$\r\n"
            		+ "begin \r\n"
            		+ "delete from Apply where sID = Old.sID;\r\n"
            		+ "return old;\r\n"
            		+ "end;\r\n"
            		+ "$$ LANGUAGE 'plpgsql';\r\n"
            		+ "\r\n"
            		+ "CREATE TRIGGER r2\r\n"
            		+ "after delete on Student\r\n"
            		+ "for each row \r\n"
            		+ "execute procedure r2();";
            stmt.execute(trigger1);
            
            String deleteTest1 = "delete from Student where GPA < 3.5;";
            stmt.execute(deleteTest1);
            
            String query1 = "select * from Student order by sID;";
            
            rs = stmt.executeQuery(query1);
            System.out.println("   sID sName GPA sizeHs");
            int seq = 1;
            while(rs.next()) {
                int sid = rs.getInt("sID");
                String sname = rs.getString("sName");
                float gpa = rs.getFloat("GPA");
                int sizehs = rs.getInt("sizeHs");
                
                System.out.println(seq+ "||" + sid + "||" + sname + "||" + gpa + "||" + sizehs);
                seq+=1;
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            
           
            String query2 = "select * from Apply order by sID, cName, major;";
            rs = stmt.executeQuery(query2);
            
            seq = 1;
            System.out.println("   sID  cName    major  decision");
            while(rs.next()) { 
                int sID = rs.getInt("sID");
                String cName = rs.getString("cName");
                String major = rs.getString("major");
                String decision = rs.getString("decision");
                System.out.println(seq + "||" + sID + "||" + cName + "||" + major + " ||" + decision);
                seq+=1;
            }
            

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
            System.out.println("Trigger test2");
            String trigger2 = "create or replace function r4() returns trigger as $$\r\n"
            		+ "BEGIN\r\n"
            		+ "IF exists (select * from College where cName = New.cName) THEN\r\n"
            		+ "return null;\r\n"
            		+ "else \r\n"
            		+ "return New;\r\n"
            		+ "end if;\r\n"
            		+ "end;\r\n"
            		+ "$$ LANGUAGE 'plpgsql';\r\n"
            		+ "\r\n"
            		+ "\r\n"
            		+ "create TRIGGER r4\r\n"
            		+ "before insert on College\r\n"
            		+ "for each row\r\n"
            		+ "execute procedure r4();";
            stmt.execute(trigger2);

            String insertTest1 = "insert into College values ('UCLA', 'CA', 20000);\r\n"
            		+ "insert into College values ('MIT', 'MI', 10000);";
            stmt.execute(insertTest1);
            
            
            String query3 = "select * from College order by cName;";
            rs = stmt.executeQuery(query3);
            seq = 1;
            System.out.println("   cName    state enrollment");
            while(rs.next()) {           
                String cname = rs.getString("cName");
                String state = rs.getString("state");
                int enrollment = rs.getInt("enrollment");
                System.out.println(seq+ "||" + cname + "||" + state + "||" + enrollment);
                seq+=1;
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("View Test1");
            String makeView = "create view CSEE AS\r\n"
            		+ "select sID, cName, major\r\n"
            		+ "from Apply\r\n"
            		+ "where major = 'CS' or major = 'EE';";
            stmt.execute(makeView);
            
            		
            
            String query4 = "select * from CSEE order by sID, cName, major;";
            rs = stmt.executeQuery(query4);
            seq=1;
            System.out.println("   sID   cName  major");
            while(rs.next()) {
                int sID = rs.getInt("sID");
                String cname = rs.getString("cName");
                String major = rs.getString("major");
                System.out.println(seq+ "||" + sID + "||" + cname + "||" + major);
                seq+=1;
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("View test 2");
            
            String trigger3 = "create or replace function viewtest() returns trigger as $$\r\n"
            		+ "begin\r\n"
            		+ "if New.major = 'CS' or New.major = 'EE' THEN\r\n"
            		+ "insert into apply values (New.sID, New.cName, New.major, null);\r\n"
            		+ "return NEW;\r\n"
            		+ "ELSE\r\n"
            		+ "return NEW;\r\n"
            		+ "end if;\r\n"
            		+ "end;\r\n"
            		+ "$$ language 'plpgsql';\r\n"
            		+ "\r\n"
            		+ "create trigger CSEEinsert\r\n"
            		+ "instead of insert on CSEE\r\n"
            		+ "for each row\r\n"
            		+ "execute procedure viewtest();";
            stmt.execute(trigger3);
            
            String insertTest2 = "insert into CSEE VALUES (333, 'UCLA', 'biology');";
            stmt.execute(insertTest2);
            
          
            String query5 = "select * from CSEE order by sID, cName, major;";
            rs = stmt.executeQuery(query5);
            seq = 1;
            System.out.println("   sID   cName   major");
            while(rs.next()) {
                int sID = rs.getInt("sID");
                String cname = rs.getString("cName");
                String major = rs.getString("major");        
                System.out.println(seq + "||" + sID + "||" + cname + "||" + major);
                seq+=1;
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
        
            String query6 = "select * from apply order by sID, cName, major;";
            rs = stmt.executeQuery(query6);
            seq = 1;
            System.out.println("   sID   cName  major decision");
            while(rs.next()) {
            	int sID = rs.getInt("sID");
                String cname = rs.getString("cName");
                String major = rs.getString("major");              
                String decision = rs.getString("decision");
                System.out.println(seq+ "||" + sID + "||" + cname + "||" + major + "||" + decision);
                seq+=1;
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
            String insertTest3 = "insert into CSEE values (333, 'UCLA', 'CS');";
            stmt.execute(insertTest3);            
            
            
            String query7 = "select * from CSEE order by sID, cName, major;";
            rs = stmt.executeQuery(query7);
            seq = 1;
            System.out.println("   sID  cName  major");
            while (rs.next()) {
                int sID = rs.getInt("sID");
                String cname = rs.getString("cName");
                String major = rs.getString("major");
                System.out.println(seq+ "||" + sID + "||" + cname + "||" + major);  
                seq+=1;
            }
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
                      
            String query8 = "select * from Apply order by sID, cName, major;";
            rs = stmt.executeQuery(query8);
            seq = 1;      
            System.out.println("   sID  cName   major");
            while (rs.next()) {
                int sID = rs.getInt("sID");
                String cname = rs.getString("cName");
                String major = rs.getString("major");
                System.out.println(seq+ "||" + sID + "||" + cname + "||" + major);  
                seq+=1;
            }
            
        }
        catch(SQLException ex) {
            throw ex;
        }
    }
}
