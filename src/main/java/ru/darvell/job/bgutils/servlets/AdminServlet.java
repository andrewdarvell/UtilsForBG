package ru.darvell.job.bgutils.servlets;

import ru.darvell.job.bgutils.security.Securer;
import ru.darvell.job.bgutils.server.ServerUrls;
import ru.darvell.job.bgutils.templater.Templater;
import ru.darvell.job.bgutils.utils.CookieWorker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    Securer securer;
    int rightRole = 1;

    public AdminServlet(Securer securer) {
        this.securer = securer;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        String sessionKey = CookieWorker.getSessionKey(req.getCookies());
        System.out.println("admin open");
        if (!sessionKey.equals("")){
            int role = securer.testSessionKey(sessionKey);
            if(role > 0){
                System.out.println(role);
                Map<String, Object> pageVariables = new HashMap<>();
                resp.setContentType("text/html;charset=utf-8");
                if(role == 1) {
                    resp.getWriter().println(Templater.getPage("admin.html", pageVariables));
                }else if(role == 2){
                    resp.getWriter().println(Templater.getPage("support.html", pageVariables));
                }
                resp.setStatus(HttpServletResponse.SC_OK);
            }else{
                resp.sendRedirect(ServerUrls.loginUrl);
            }
        }else {
            resp.sendRedirect(ServerUrls.loginUrl);
        }
    }
}
