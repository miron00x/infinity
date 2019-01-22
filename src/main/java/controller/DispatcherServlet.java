package controller;

import util.MainServiceFactoryImpl;
import util.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        process(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        process(httpServletRequest, httpServletResponse);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String context = req.getContextPath();
        int postfixIndex = url.lastIndexOf(".html");
        if(postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        Action action = ActionFactory.getAction(url);
        Forward forward = null;
        if(action != null) {
            try(ServiceFactory factory = getServiceFactory()) {
                action.setServiceFactory(factory);
                forward = action.execute(req, resp);
            } catch(Exception e) {
                throw new ServletException(e);
            }
        }
        if(forward != null && forward.isRedirect()) {
            resp.sendRedirect(context + forward.getUrl());
        } else {
            if(forward != null && forward.getUrl() != null) {
                url = forward.getUrl();
            }
            req.getRequestDispatcher("/WEB-INF/jsp" + url + ".jsp").forward(req, resp);
        }
    }

    public ServiceFactory getServiceFactory() {
        return new MainServiceFactoryImpl();
    }
}
