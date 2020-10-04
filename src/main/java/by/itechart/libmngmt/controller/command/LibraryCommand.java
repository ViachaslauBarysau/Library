package by.itechart.libmngmt.controller.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Parent class for all LibraryCommands.
 */
public abstract class LibraryCommand {
    private static final Logger LOGGER = LogManager.getLogger(LibraryCommand.class.getName());
    private static final String TARGET_FORMAT = "/WEB-INF/jsp/%s.jsp";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String CONTENT_TYPE = "application/json";
    private static final String ENCODING = "UTF-8";
    private static final int MIN_PAGE_NUMBER = 1;
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /**
     * Initializes fields context, request, and response.
     */
    public void init(
            ServletContext servletContext,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }

    public abstract void process() throws ServletException, IOException;

    protected void forward(String target) throws ServletException, IOException {
        target = String.format(TARGET_FORMAT, target);
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

    protected int setPageNumber(final int pageCount) {
        int pageNumber = MIN_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
            LOGGER.debug("Wrong page number.", e);
        }
        if (pageNumber > pageCount) {
            pageNumber = pageCount;
        } else if (pageNumber < MIN_PAGE_NUMBER) {
            pageNumber = MIN_PAGE_NUMBER;
        }
        return pageNumber;
    }

    protected void sendResponse(final List<String> errorMessages) throws IOException {
        Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
        String errors = gson.toJson(errorMessages);
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        out.print(errors);
        out.flush();
    }
}
