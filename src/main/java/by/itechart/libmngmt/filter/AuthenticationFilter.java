package by.itechart.libmngmt.filter;

//import com.sun.net.httpserver.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String ADMIN = "admin";
    public static final String LOGIN_JSP_URL = "/WEB-INF/jsp/login.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String login = request.getParameter(LOGIN);
        final String password = request.getParameter(PASSWORD);
        final HttpSession session = request.getSession();
        if (nonNull(session) &&
                nonNull(session.getAttribute(LOGIN)) &&
                nonNull(session.getAttribute(PASSWORD))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (nonNull(login) && login.equals(ADMIN) && nonNull(password) && password.equals(ADMIN)) {
            request.getSession().setAttribute(LOGIN, ADMIN);
            request.getSession().setAttribute(PASSWORD, ADMIN);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            request.getRequestDispatcher(LOGIN_JSP_URL).forward(request, response);
        }
    }
}
