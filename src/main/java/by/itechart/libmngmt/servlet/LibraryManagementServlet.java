package by.itechart.libmngmt.servlet;

import by.itechart.libmngmt.servlet.command.LibraryCommandFactory;
import by.itechart.libmngmt.servlet.command.LibraryCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/lib-app"})
public class LibraryManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LibraryCommand command = LibraryCommandFactory.getCommand(req);
        command.init(getServletContext(), req, resp);
        command.process();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LibraryCommand command = LibraryCommandFactory.getCommand(req);
        command.init(getServletContext(), req, resp);
        command.process();
    }

}
