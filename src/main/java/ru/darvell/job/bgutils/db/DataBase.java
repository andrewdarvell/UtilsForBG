package ru.darvell.job.bgutils.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBase {

    private final static String DB_ACCESS_FILE = "tmp/dbaccess.csv";

    Connection connectToDB(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            Map<String, String> params = loadDBAccessFromFile();
            connection = DriverManager.getConnection(params.get("conString"),
                                                        params.get("login"),
                                                        params.get("pass"));
            connection.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public ArrayList<Session> getSessions(String login){

        String sql ="SELECT * \n" +
                    "FROM log_session_8_201507 \n" +
                    "WHERE login_name = ? \n" +
                    "AND status = 0";
        Connection conn = connectToDB();
        ArrayList<Session> result = new ArrayList<>();
        if (conn != null){
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, login);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Session session = new Session(rs.getInt("id"),
                                                rs.getString("login_name"),
                                                rs.getInt("status"));
                    result.add(session);
                }
                rs.close();
                ps.close();
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public void killSession(int id){
        String sql = "UPDATE log_session_8_201507 \n" +
                     "SET status = 1 \n" +
                     "WHERE id = ?";
        Connection conn = connectToDB();
        if (conn != null){
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
                conn.commit();
                ps.close();
                conn.close();
            }catch (Exception e){
                e.toString();
            }
        }
    }

    private Map<String, String> loadDBAccessFromFile(){
        Map<String, String> result = new HashMap<>();
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(DB_ACCESS_FILE).getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;

            while ((s = br.readLine())!= null){
                String[] userRaw = s.split(";");
                result.put("conString", userRaw[0]);
                result.put("login", userRaw[1]);
                result.put("pass", userRaw[2]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
