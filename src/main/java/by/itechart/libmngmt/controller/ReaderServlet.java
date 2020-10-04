package by.itechart.libmngmt.controller;

import by.itechart.libmngmt.service.ReaderService;
import by.itechart.libmngmt.service.impl.ReaderServiceImpl;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Used for processing requests with URL patter "/reader".
 */
@WebServlet(urlPatterns = {"/reader"})
public class ReaderServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";
    private ReaderService readerService = ReaderServiceImpl.getInstance();

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String pattern = request.getParameter("pattern");
        List<String> list = readerService.getEmails(pattern);
        sendResponse(list, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String readerEmail = request.getParameter("email");
        String readerName = readerService.getNameByEmail(readerEmail);
        sendResponse(readerName, response);
    }

    private void sendResponse(final Object object, final HttpServletResponse response) throws IOException {
        String jsonObject = new Gson().toJson(object);
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        out.print(jsonObject);
        out.flush();
    }
}
