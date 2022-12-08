//201821053 Kim Hyung Jun
//Database assignment 4

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

            
            System.out.println("Recursive test 1");
       
                     
            String recursive1 = "with RECURSIVE\r\n"
            		+ "	Ancestor(a,d) as (select parent as a, child as d from parentof\r\n"
            		+ "				  union\r\n"
            		+ "				  select Ancestor.a, ParentOf.child as d\r\n"
            		+ "				  from Ancestor, ParentOf\r\n"
            		+ "				  where Ancestor.d = ParentOf.parent)\r\n"
            		+ "	select a from Ancestor where d = 'Frank'\r\n"
            		+ "	order by a;";
            rs = stmt.executeQuery(recursive1);
            int seq = 1;
            System.out.println("    a");
            System.out.println("---------");
            while(rs.next()) {
                
                String a = rs.getString("a");
                
                System.out.println(seq+ " " + a);
                seq+=1;
            }
            
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("Recursive test 2");
                     
            String recursive2 = "with RECURSIVE\r\n"
            		+ "	Superior as (select * from Manager\r\n"
            		+ "				 union\r\n"
            		+ "				 select S.mID, M.eID\r\n"
            		+ "				 from Superior S, Manager M\r\n"
            		+ "				 where S.eID = M.mID)\r\n"
            		+ "	select sum(salary)\r\n"
            		+ "	from Employee\r\n"
            		+ "	where ID in\r\n"
            		+ "	(select mgrID from Project where name = 'X'\r\n"
            		+ "	union\r\n"
            		+ "	select eID from Project, Superior\r\n"
            		+ "	where Project.name = 'X' AND Project.mgrID = Superior.mID)";
            rs = stmt.executeQuery(recursive2);
            seq = 1;
            System.out.println("  sum ");
            System.out.println("-------");
            while(rs.next()) {
                
                int sum = rs.getInt("sum");
                
                System.out.println(seq+ " " + sum);
                seq+=1;
            }
            
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("Recursive test 3");
            
            String recursive3 = "with recursive\r\n"
            		+ "	FromA(dest,total) as\r\n"
            		+ "		(select dest, cost as total from Flight where orig = 'A'\r\n"
            		+ "		 union\r\n"
            		+ "		 select F.dest, cost+total as total\r\n"
            		+ "		 from FromA FA, Flight F\r\n"
            		+ "		 where FA.dest = F.orig)\r\n"
            		+ "select * from FromA\r\n"
            		+ "order by total;";
            
            rs = stmt.executeQuery(recursive3);
            System.out.println("  dest  total ");
            System.out.println("---------------");
            seq = 1;
            while(rs.next()) {
                
                String dest = rs.getString("dest");               
                int total = rs.getInt("total");
                
                System.out.printf("%d %4s %5d \n", seq, dest, total);
                seq+=1;
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
            System.out.println("Recursive Test 4");
            
            String recursive4 = "with recursive\r\n"
            		+ "	FromA(dest,total) as\r\n"
            		+ "		(select dest, cost as total from Flight where orig = 'A'\r\n"
            		+ "		 union\r\n"
            		+ "		 select F.dest, cost+total as total\r\n"
            		+ "		 from FromA FA, Flight F\r\n"
            		+ "		 where FA.dest = F.orig)\r\n"
            		+ "select min(total) from FromA where dest = 'B';";
            rs = stmt.executeQuery(recursive4);
            
            seq = 1;
            System.out.println("  min ");
            System.out.println("-------");
            while(rs.next()) { 
                int min = rs.getInt("min");
                
                System.out.println(seq + " " + min);
                seq+=1;
            }
            

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
            System.out.println("OLAP test 1");
            String olap1 = "select storeID, itemID, custID, sum(price)\r\n"
            		+ "from Sales \r\n"
            		+ "group by cube(storeID, itemID, custID )\r\n"
            		+ "order by storeID, itemID, custID;";
            rs = stmt.executeQuery(olap1);       
                             
            seq = 1;
            System.out.println("   storeid itemid custid   sum ");
            System.out.println("-------------------------------");
            while(rs.next()) {           
                String storeid = rs.getString("storeid");
                String itemid = rs.getString("itemid");
                String custid = rs.getString("custid");
                int sum = rs.getInt("sum");
                System.out.printf("%2d  %6s%7s  %5s %5d \n", seq, storeid, itemid, custid, sum);
                seq+=1;
            }

            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
            System.out.println("OLAP test 2");
            String olap2 = "select storeID, itemID, custID, sum(price)\r\n"
            		+ "from Sales F\r\n"
            		+ "group by cube(storeID, custID), itemID\r\n"
            		+ "order by storeID, itemID, custID;";
            rs = stmt.executeQuery(olap2);
            
            seq = 1;
            System.out.println("   storeid itemid custid   sum ");
            System.out.println("-------------------------------");
            while(rs.next()) {           
                String storeid = rs.getString("storeid");
                String itemid = rs.getString("itemid");
                String custid = rs.getString("custid");
                int sum = rs.getInt("sum");
                System.out.printf("%2d  %6s%7s  %5s %5d \n", seq, storeid, itemid, custid, sum);
                seq+=1;
            }
            
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            
            System.out.println("OLAP test 3");
                     
            String olap3 = "select storeID, itemID, custID, sum(price)\r\n"
            		+ "from Sales F\r\n"
            		+ "group by rollup(storeID, itemID, custID)\r\n"
            		+ "order by storeID, itemID, custID;";
            rs = stmt.executeQuery(olap3);
            seq=1;
            System.out.println("   storeid itemid custid   sum ");
            System.out.println("-------------------------------");
            while(rs.next()) {
            	String storeid = rs.getString("storeid");
                String itemid = rs.getString("itemid");
                String custid = rs.getString("custid");
                int sum = rs.getInt("sum");
                System.out.printf("%2d  %6s%7s  %5s %5d \n", seq, storeid, itemid, custid, sum);
                seq+=1;
            }
            
            System.out.println("Continue? (Enter 1 for continue)");
            scan.nextLine();
            System.out.println("OLAP test 4");
            
            String olap4 = "select state, county, city, sum(price)\r\n"
            		+ "from Sales F, Store S\r\n"
            		+ "where F.storeID = S.storeID\r\n"
            		+ "group by rollup(state, county, city)\r\n"
            		+ "order by state, county, city;";
            rs = stmt.executeQuery(olap4);                                             
            
            seq = 1;
            System.out.println("    state     county           city     sum ");
            System.out.println("---------------------------------------------");
            while(rs.next()) {               
                String state = rs.getString("state");
                String county = rs.getString("county");
                String city = rs.getString("city");
                int sum = rs.getInt("sum");
                System.out.printf("%2d%5s%15s%15s%6d \n", seq, state, county, city, sum);
                seq+=1;
            }                      
            
        }
        catch(SQLException ex) {
            throw ex;
        }
    }
}