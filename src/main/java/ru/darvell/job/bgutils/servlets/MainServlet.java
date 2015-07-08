package ru.darvell.job.bgutils.servlets;

import ru.darvell.job.bgutils.security.Securer;
import ru.darvell.job.bgutils.server.ServerUrls;
import ru.darvell.job.bgutils.utils.CookieWorker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    Securer securer;

    public MainServlet(Securer securer) {
        this.securer = securer;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        String sessionKey = CookieWorker.getSessionKey(req.getCookies());
        if (!sessionKey.equals("")){
            int role = securer.testSessionKey(sessionKey);
            if(role > 0){
                resp.sendRedirect(ServerUrls.redirectToMainForm(role));
            }
        }
        resp.sendRedirect(ServerUrls.loginUrl);
    }
}
