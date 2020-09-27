package by.itechart.libmngmt.controller;

import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.service.impl.ReaderServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/reader"})
public class ReaderServlet extends HttpServlet {
    private ReaderService readerService = ReaderServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pattern = request.getParameter("pattern");
        List<String> list = readerService.getEmails(pattern);
        String jsonList = new Gson().toJson(list);
        sendResponse(jsonList, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String readerEmail = request.getParameter("email");
        String readerName = readerService.getNameByEmail(readerEmail);
        String jsonName = new Gson().toJson(readerName);
        sendResponse(jsonName, response);
    }

    private void sendResponse(String jsonString, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}
