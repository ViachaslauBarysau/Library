package by.itechart.libmngmt.controller;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;

@WebServlet(urlPatterns = {"/reader-card"})
public class ReaderCardServlet extends HttpServlet {

    private final ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int readerCardId = Integer.parseInt(req.getParameter("id"));
        ReaderCardDto readerCardDto = readerCardService.getReaderCard(readerCardId);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String str = gson.toJson(readerCardDto);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(str);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
