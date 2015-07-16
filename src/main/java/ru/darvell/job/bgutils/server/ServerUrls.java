package ru.darvell.job.bgutils.server;

public class ServerUrls {
    public static final String loginUrl = "http://10.0.1.253:8090/authform";
    public static final String adminUrl = "http://10.0.1.253:8090/admin";
    public static final String supportUrl = "http://10.0.1.253:8090/support";

    public static String redirectToMainForm(int role){
        if (role == 1){
            return adminUrl;
        }
        return null;
    }
}
