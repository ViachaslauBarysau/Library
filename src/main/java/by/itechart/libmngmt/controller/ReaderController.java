package by.itechart.libmngmt.controller;

import by.itechart.libmngmt.service.BookService;
import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.service.impl.BookServiceImpl;
import by.itechart.libmngmt.service.impl.ReaderServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/rdr"})
public class ReaderController extends HttpServlet {

    private ReaderService readerService = ReaderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = null;
        if (!(req.getParameter("pattern") == null)) {
            String pattern = req.getParameter("pattern");
            List<String> list = readerService.getEmails(pattern);
            json = new Gson().toJson(list);
//        } else if (!(req.getParameter("email") == null)) {
//            String email = req.getParameter("email");
        } else {
            String email = "pavlova@gmail.com";
            String name = readerService.getNameByEmail(email);
            json = new Gson().toJson(name);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
