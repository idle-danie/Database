//201821053 Kim Hyung Jun
//Database assignment 2

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
            
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            System.out.println("Query 1");
            // executing query 1 & display the result by using println
            // select all from student
            String query1 = "select * from Student;";
            
            rs = stmt.executeQuery(query1);
            
            while(rs.next()) {
                int sid = rs.getInt("sID");
                String sname = rs.getString("sName");
                float gpa = rs.getFloat("GPA");
                int sizehs = rs.getInt("sizeHs");
                System.out.println("sID = " + sid + " || sName = " + sname + " || GPA = " + gpa + " || sizeHs = " + sizehs);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            System.out.println("Query 2");
            // select all from college
            String query2 = "select * from College;";
            rs = stmt.executeQuery(query2);
            
            while(rs.next()) {
                String cname = rs.getString("cName");
                String state = rs.getString("state");
                int enrollment = rs.getInt("enrollment");
                System.out.println("cName = " + cname + " || state = " + state + " || enrollment = " + enrollment);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            System.out.println("Query 3");
            // select all from apply
            String query3 = "select * from Apply";
            rs = stmt.executeQuery(query3);
            while(rs.next()) {
                int sid = rs.getInt("sID");
                String cname = rs.getString("cName");
                String major = rs.getString("major");
                String decision = rs.getString("decision");
                System.out.println("cName = " + cname + " || major = " + major + " || decision = " + decision);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            System.out.println("Query 4");
            String query4 = "select distinct cName\n" 
            				+"from Apply A1\n" 
            				+"where 6 > (select count(*) from Apply A2 where A2.cName = A1.cName);";
            
            rs = stmt.executeQuery(query4);
            
            while(rs.next()) {
                String cname = rs.getString("cName");
                System.out.println("cName= " + cname);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            System.out.println("Query 5");
            String query5 = "select cName, major, min(GPA), max(GPA) from Student, Apply where Student.sID = Apply.sID group by cName, major having min(GPA) > 3.0 order by cName, major";
            rs = stmt.executeQuery(query5);
            while(rs.next()) {
                String cname = rs.getString("cName");
                String major = rs.getString("major");
                float min = rs.getFloat("min");
                float max = rs.getFloat("max");
                System.out.println("cname = " + cname + " || major = " + major + " || min(GPA) = " + min + " || max(GPA) = " + max);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("Query 6");
            // user should input data of major and college name in query 6
            System.out.println("Enter major which student that you want to find applied.");
            String major_query6 = scan.next();
            System.out.println("Enter cName which student that you want to find applied. ");
            String cName_query6 = scan.next();
            String query6 = "select sName, GPA from Student natural join Apply where major = '"+ major_query6 +"' and cName = '" + cName_query6 + "'";
            rs = stmt.executeQuery(query6);
            while(rs.next()) {
                String sName = rs.getString("sName");
                float GPA = rs.getFloat("GPA");
                System.out.println("sName = " + sName + " / GPA = " + GPA);
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();

            System.out.println("Query 7");
            // user should input data of major (APPLIED OR NOT APPLIED) name in query 7
            System.out.println("Enter major which student that you want to find applied.");
            String major1 = scan.next();
            System.out.println("Enter major which student that you want to find didn't apply.");
            String major2 = scan.next();
            String query7 = "select sID, sName from Student where sID = any (select sID from Apply where major =  '"+ major1 + "') and not sID = any (select sID from Apply where major = '" + major2 + "')";
            rs = stmt.executeQuery(query7);
            while (rs.next()) {
                int sID = rs.getInt("sID");
                String sName = rs.getString("sName");
                System.out.println("sID = " + sID + " || sName = " + sName);
            }
        }
        catch(SQLException ex) {
            throw ex;
        }
    }
}
