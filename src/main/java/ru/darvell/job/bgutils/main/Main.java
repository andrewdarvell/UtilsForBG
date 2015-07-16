package ru.darvell.job.bgutils.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.darvell.job.bgutils.security.Securer;
import ru.darvell.job.bgutils.servlets.*;

//add resources/tmp/dbaccess.csv
//add resources/tmp/users.csv
public class Main {
    public static void main(String[] args) throws Exception{

        Securer securer = new Securer();
        LoginServlet loginServlet = new LoginServlet(securer);
        MainServlet mainServlet = new MainServlet(securer);
        AdminServlet adminServlet = new AdminServlet(securer);
        BGSessionsAdminServlet bgSessionsAdminServlet = new BGSessionsAdminServlet(securer);
        SessionKiller sessionKiller = new SessionKiller(securer);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(loginServlet), "/authform");
        context.addServlet(new ServletHolder(adminServlet), "/admin");
        context.addServlet(new ServletHolder(bgSessionsAdminServlet), "/sessions");
        context.addServlet(new ServletHolder(sessionKiller), "/kill_session");
        context.addServlet(new ServletHolder(mainServlet), "/");

        ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
        holderHome.setInitParameter("resourceBase", "src/main/resources/static");
        holderHome.setInitParameter("dirAllowed", "true");
        holderHome.setInitParameter("pathInfoOnly", "true");
        context.addServlet(holderHome,"/home/*");

        Server server = new Server(8090);
        server.setHandler(context);

        server.start();
        server.join();

    }
}
