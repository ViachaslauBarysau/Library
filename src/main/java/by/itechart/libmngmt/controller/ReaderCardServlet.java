package by.itechart.libmngmt.controller;

import by.itechart.libmngmt.dto.ReaderCardDto;
import by.itechart.libmngmt.service.ReaderCardService;
import by.itechart.libmngmt.service.impl.ReaderCardServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/reader-card"})
public class ReaderCardServlet extends HttpServlet {
    private ReaderCardService readerCardService = ReaderCardServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int readerCardId = Integer.parseInt(request.getParameter("id"));
        ReaderCardDto readerCardDto = readerCardService.getReaderCard(readerCardId);
        sendResponse(readerCardDto, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int bookId = Integer.parseInt(request.getParameter("bookid"));
        List<ReaderCardDto> readerCardDtoList = readerCardService.getActiveReaderCards(bookId);
        sendResponse(readerCardDtoList, response);
    }

    private void sendResponse(Object object, HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonString = gson.toJson(object);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }
}
