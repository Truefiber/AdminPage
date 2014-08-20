package com.gkartash.adminpage.web;

import com.gkartash.adminpage.business.Business;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by gkartashevskyy on 8/15/2014.
 */
public class ServiceServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("updatebtn") != null) {
            List<String> users = Business.getUserList();
            req.setAttribute("users", users);

        }

        if (req.getParameter("historybtn") != null) {
         //   String history =
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/main.jsp");
        dispatcher.forward(req,resp);
    }
}
