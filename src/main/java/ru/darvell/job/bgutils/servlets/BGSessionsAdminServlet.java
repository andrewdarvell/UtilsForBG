package ru.darvell.job.bgutils.servlets;

import org.joda.time.DateTime;
import ru.darvell.job.bgutils.db.DataBase;
import ru.darvell.job.bgutils.db.Session;
import ru.darvell.job.bgutils.security.Securer;
import ru.darvell.job.bgutils.server.ServerUrls;
import ru.darvell.job.bgutils.templater.Templater;
import ru.darvell.job.bgutils.utils.CookieWorker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BGSessionsAdminServlet extends HttpServlet {

    Securer securer;

    public BGSessionsAdminServlet(Securer securer) {
        this.securer = securer;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        String sessionKey = CookieWorker.getSessionKey(req.getCookies());
        System.out.println("sessions open");
        if (!sessionKey.equals("")) {
            System.out.println(sessionKey);
            int role = securer.testSessionKey(sessionKey);
            if(role == 1){
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("periods", genPeriods(10));
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println(Templater.getPage("bgsessionsadmin.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }else{
            resp.sendRedirect(ServerUrls.loginUrl);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        String sessionKey = CookieWorker.getSessionKey(req.getCookies());
        System.out.println("sessions open");
        if (!sessionKey.equals("")) {
            int role = securer.testSessionKey(sessionKey);
            if (role == 1){
                String sessionLogin = req.getParameter("sessionLogin");

                String period = req.getParameter("period");

                ArrayList<Session> sessions = new DataBase().getSessions(sessionLogin, period);

                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("periods", genPeriods(10));
                pageVariables.put("sessions", sessions);
                pageVariables.put("sessionLogin", sessionLogin);
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println(Templater.getPage("bgsessionsadmin.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);

                for(Session session : sessions){
                    System.out.println(session);
                }
            }
        }else{
            resp.sendRedirect(ServerUrls.loginUrl);
        }
    }

    ArrayList<Map<String, Integer>> genPeriods(int countMonth){

        DateTime dateTime = new DateTime();
        Map<String, Integer> map = new HashMap<>();
        map.put("year", dateTime.getYear());
        map.put("month", dateTime.getMonthOfYear());
        map.put("default", 1);
        ArrayList<Map<String, Integer>> result = new ArrayList<>();
        result.add(map);

        int i = 1;
        while (i <= countMonth){
            DateTime munusMonth = dateTime.minusMonths(i);
            Map<String, Integer> mapNd = new HashMap<>();
            mapNd.put("year", munusMonth.getYear());
            mapNd.put("month", munusMonth.getMonthOfYear());
            mapNd.put("default", 0);
            result.add(mapNd);
            i++;
        }
        return result;
    }
}
