package ru.darvell.job.bgutils.db;

public class Session {
    int id;
    String login;
    int status;
    String period;

    public Session(int id, String login, int status, String period) {
        this.id = id;
        this.login = login;
        this.status = status;
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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
