package ru.darvell.job.bgutils.security;

import ru.darvell.job.bgutils.utils.MD5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Securer {
    final String USERS_FILE = "tmp/users.csv";


    ArrayList<User> users = new ArrayList<>();
    ArrayList<SessionKey> sessionKeys = new ArrayList<>();


    public Securer() {
        loadUsersFromFile();
    }

    public void printUsers(){
        for (User user : users){
            System.out.println(user);
        }
    }

    public String addSessionKey(String roleStr){
        int randInt = new Random().nextInt();
        randInt += System.currentTimeMillis();
        String key = MD5.getMd5(String.valueOf(randInt));
        int role = -1;
        switch (roleStr){
            case "admin": role = 1;
                break;
        }
        sessionKeys.add(new SessionKey(key, role));
        return key;
    }

    public String testUser(String login, String pass){
        String role = "";
        for (User user : users){
            if (user.getLogin().equals(login) && user.getPass().equals(pass)){
                role = user.getRole();
            }
        }
        return role;
    }

    public int testSessionKey(String sessionKeyStr){
        int role = -1;
        for (SessionKey sessionKey : sessionKeys){
            if (sessionKey.getKey().equals(sessionKeyStr)){
                role = sessionKey.getRole();
            }
        }
        return role;
    }

    private void loadUsersFromFile(){
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(USERS_FILE).getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine())!= null){
                String[] userRaw = s.split(";");
                users.add(new User(userRaw[0], userRaw[1], userRaw[2]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
