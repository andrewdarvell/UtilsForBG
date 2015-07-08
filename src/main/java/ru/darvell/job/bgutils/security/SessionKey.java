package ru.darvell.job.bgutils.security;

public class SessionKey {
    private String key;
    private int role;

    public SessionKey(String key, int role) {
        this.key = key;
        this.role = role;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
