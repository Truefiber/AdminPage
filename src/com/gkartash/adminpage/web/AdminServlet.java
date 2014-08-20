package com.gkartash.adminpage.web;

import com.gkartash.adminpage.business.Business;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by gkartashevskyy on 8/15/2014.
 */
public class AdminServlet extends HttpServlet {

    static Logger logger = Logger.getLogger(AdminServlet.class.getCanonicalName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.info("get Post");
        String login = req.getParameter("user");
        String pass = req.getParameter("pwd");
        if (Business.isAdmin(login, pass)) {
            Cookie loginCookie = new Cookie("user", login);
            loginCookie.setMaxAge(20 * 60);
            req.setAttribute("users", Business.getMapOfUsers());
            req.setAttribute("statsdataset", Business.getUserStats());
            resp.addCookie(loginCookie);
            RequestDispatcher rd = req.getRequestDispatcher("main.jsp");
            rd.forward(req, resp);

        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out= resp.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("get doGet");
    }
}
