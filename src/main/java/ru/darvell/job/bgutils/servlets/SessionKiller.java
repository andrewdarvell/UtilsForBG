package ru.darvell.job.bgutils.servlets;

import ru.darvell.job.bgutils.db.DataBase;
import ru.darvell.job.bgutils.security.Securer;
import ru.darvell.job.bgutils.server.ServerUrls;
import ru.darvell.job.bgutils.utils.CookieWorker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionKiller extends HttpServlet {

    Securer securer;

    public SessionKiller(Securer securer) {
        this.securer = securer;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {

        String sessionKey = CookieWorker.getSessionKey(req.getCookies());
        System.out.println("sessions open");
        if (!sessionKey.equals("")) {
            int role = securer.testSessionKey(sessionKey);
            if (role == 1){
                String idStr = req.getParameter("sessionId");
                int id = Integer.valueOf(idStr);
                new DataBase().killSession(id);
                System.out.println(id);
            }
        }else{
            resp.sendRedirect(ServerUrls.loginUrl);
        }
    }
}
