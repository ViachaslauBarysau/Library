package by.itechart.libmngmt.controller;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
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

@WebServlet(urlPatterns = {"/rdr-crd"})
public class ReaderCardController extends HttpServlet {

    ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int readerCardId = Integer.parseInt(req.getParameter("id"));
        ReaderCardDto readerCardDto = readerCardService.getReaderCard(readerCardId);


        String str = new Gson().toJson(readerCardDto);

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
