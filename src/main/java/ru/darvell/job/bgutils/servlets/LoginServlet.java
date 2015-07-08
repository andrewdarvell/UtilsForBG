package ru.darvell.job.bgutils.servlets;

import ru.darvell.job.bgutils.security.Securer;
import ru.darvell.job.bgutils.server.ServerUrls;
import ru.darvell.job.bgutils.templater.Templater;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private Securer securer;

    public LoginServlet(Securer securer) {
        this.securer = securer;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(Templater.getPage("loginform.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        try{
            String login = req.getParameter("login");
            String passw = req.getParameter("passw");
            String role = securer.testUser(login, passw);

            if (!role.equals("")){
                System.out.println(role);
                String sessionKey = securer.addSessionKey(role);
                //resp.setStatus(HttpServletResponse.SC_OK);
                Cookie cookie = new Cookie("sessionKey", sessionKey);
                resp.addCookie(cookie);
                resp.sendRedirect(ServerUrls.adminUrl);
            }else{
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println("ВОН!!!");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
