package sample;
import java.sql.*;


public class database {

    public void registration(String user, String pass, String e, String d, String p, String q, String key, String el_p, String el_y1,String el_prime) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");

            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS login (USERNAME VARCHAR, PASSWORD VARCHAR, RSA_E VARCHAR, RSA_D VARCHAR, RSA_P VARCHAR, RSA_Q VARCHAR, KEY VARCHAR, EL_P VARCHAR,EL_Y1 VARCHAR,EL_PRIME VARCHAR )");

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `login` (username , password , rsa_e , rsa_d , rsa_p,rsa_q, key, el_p, el_y1 ,el_prime ) VALUES (?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, user );
            pstmt.setString(2, pass);
            pstmt.setString(3, e);
            pstmt.setString(4, d);
            pstmt.setString(5,p );
            pstmt.setString(6,q );
            pstmt.setString(7,key);
            pstmt.setString(8,el_p);
            pstmt.setString(9,el_y1);
            pstmt.setString(10,el_prime);
            pstmt.executeUpdate();
            statement.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Something went wrong: " + ex.getMessage());
        }
    }


    public boolean loginCheck(String user,String pass){
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");

            Statement statement = conn.createStatement();

            ResultSet rs    = statement.executeQuery("SELECT COUNT(username) AS count FROM login WHERE username LIKE '" +user+"' AND password LIKE '"+pass+"'");


            if (rs.getInt("count") > 0){
                statement.close();
                conn.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return false;
    }

    public String get_E_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");


        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT RSA_E FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("RSA_E");
    }

    public String get_D_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");

        Statement statement = conn.createStatement();
        ResultSet rs    = statement.executeQuery("SELECT RSA_D FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("RSA_D");
    }

    public String get_P_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");

        Statement statement = conn.createStatement();
        ResultSet rs    = statement.executeQuery("SELECT RSA_P FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("RSA_P");
    }

    public String get_Q_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");
        Statement statement = conn.createStatement();
        ResultSet rs    = statement.executeQuery("SELECT RSA_Q FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("RSA_Q");
    }

    public String get_key_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");
        Statement statement = conn.createStatement();
        ResultSet rs    = statement.executeQuery("SELECT KEY FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("KEY");
    }

    public String get_a_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");
        Statement statement = conn.createStatement();
        ResultSet rs    = statement.executeQuery("SELECT EL_P  FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("EL_P");
    }
    public String get_y1_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");
        Statement statement = conn.createStatement();
        ResultSet rs    = statement.executeQuery("SELECT EL_Y1 FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("EL_Y1");
    }
    public String get_prim_database(String user) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\aalim\\Desktop\\4-1 exam\\SecurityPro\\File\\Data_File.db");
        Statement statement = conn.createStatement();
        ResultSet rs    = statement.executeQuery("SELECT EL_PRIME FROM login WHERE username LIKE '" +user+"'");
        return rs.getString("EL_PRIME");
    }

}