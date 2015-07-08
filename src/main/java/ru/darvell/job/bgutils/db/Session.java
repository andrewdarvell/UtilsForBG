package ru.darvell.job.bgutils.db;

public class Session {
    int id;
    String login;
    int status;

    public Session(int id, String login, int status) {
        this.id = id;
        this.login = login;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", status=" + status +
                '}';
    }
}
