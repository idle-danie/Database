import java.sql.*;
import java.sql.SQLException;
import java.util.Scanner;

public class DB_Project
{

    public static void main(String[] args) throws SQLException
    {

        Connection conn;
        Statement st;
        ResultSet rs;

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "103404";

        try
        {
            Scanner scan = new Scanner(System.in);
            System.out.println("Database SQL Project: Good-shop & place recommendation service demo");

            // JDBC를 이용해 PostgreSQL 서버 및 데이터베이스 연결
            System.out.println("Connecting PostgreSQL database");
            conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();

            int user_id = 0;
            float star;
            float other_users_star;
            int end;
            String visit_route = "";
            String comment = "";

            System.out.println("이 프로그램은 착한가격업소 및 관광명소를 추천하는 서비스입니다.");
            while(true) {
                user_id += 1;
                // user_ID를 중복 없이 만드는 Trigger -> java.util.random을 사용하여 user_ID가 중복되면 trigger 작동
//                String user_random = "select * from review_starrate where rating >= %d;\n" +
//                        "create or replace function test() returns trigger as $$\n" +
//                        "begin\n" +
//                        "if exists (select * from Users where userID = New.userID) THEN\n" +
//                        "return New + 1000;\n" +
//                        "else\n" +
//                        "return New;\n" +
//                        "end if;\n" +
//                        "end;\n" +
//                        "$$\n" +
//                        "language 'plpgsql';\n" +
//                        "create trigger Random_UserID_Exists";
//                st.execute(user_random);

                System.out.println("먼저 사용하시는 분의 성함과 추천 받고자 하는 장소의 이름을 입력해주세요.");
                String location;
                int user_cost;

                System.out.print("1. Input user name: ");
                String user_name = scan.next();
                String insert_user = String.format("insert into Users values(%d, '%s');" +
                        "insert into review_comment values(%d, null, null);" +
                        "insert into review_starrate values(%d, null, null);", user_id, user_name, user_id, user_id);
                st.execute(insert_user);

                System.out.print("2. Input location.(구 단위로 입력): ");
                location = scan.next();

                System.out.print("3. Show me the money: ");
                user_cost = scan.nextInt();
                String search_location = String.format("select * from (select sname, s_info from Goodshop where address like '%%%s%%' and cost < %d \n" +
                        "order by random() limit 1) as a \n"+
                        "union \n" +
                        "select * from(select pName, address from Place where address like '%%%s%%' order by random() limit 1) as b;", location, user_cost, location);
                rs = st.executeQuery(search_location);

                System.out.println("착한 가격 업소와 추천 명소 검색 결과입니다!");
                while(rs.next()) {
                    String name_of_shop_or_place = rs.getString("sName");
                    String info = rs.getString("s_info");
                    visit_route += name_of_shop_or_place;
                    visit_route += " ";
                    System.out.println(String.format("[ %s ]", name_of_shop_or_place));
                    System.out.println(String.format(" %s", info));
                }
                System.out.print("착한 가격 업소에 대한 comment를 달아주세요: ");
                comment = scan.next();
                System.out.print(String.format("추천된 동선 [ %s]에 대한 별점을 입력해주세요: ", visit_route));
                star = scan.nextFloat();

                System.out.print("추천된 경로 외에 다른 사용자들이 다녀간 인기 경로를 확인하시겠습니까? 별점을 입력하면 해당 별점 이상의 추천경로를 알려드립니다.:");
                other_users_star = scan.nextFloat();

                String other_place = String.format("select VisitRoute, rating from review_starrate where rating >= %f;", other_users_star);
                rs = st.executeQuery(other_place);
                System.out.println("디비갱갱 사용자의 발자취,,");
                while (rs.next()) {
                    String other_visitroute = rs.getString("VisitRoute");
                    float other_rating = rs.getFloat("rating");
                    System.out.println(String.format(" %s : %.1f ", other_visitroute, other_rating));
                }


                String update_comment = String.format("update review_comment\n" +
                        "set comment = '%s' \n" +
                        "where userID = %d;", comment, user_id);
                st.execute(update_comment);
                String update_starrate = String.format("update review_starrate\n" +
                        "set VisitRoute = '%s', rating = %f\n" +
                        "where userID = %d;", visit_route, star, user_id);
                st.execute(update_starrate);

                System.out.print("다른 사용자로 진행하시겠습니까?(0 입력 시 프로그램 종료): ");
                end = scan.nextInt();
                if (end == 0) break;
            }
            System.out.println("프로그램을 종료합니다.");
        }
        catch(SQLException ex) {
            throw ex;
        }
    }
}