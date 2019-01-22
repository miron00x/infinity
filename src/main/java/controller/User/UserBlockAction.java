package controller.User;

import controller.Action;
import controller.Forward;
import org.apache.log4j.Logger;
import services.ServiceException;
import services.UserService;
import util.FactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserBlockAction extends Action {
    Logger logger = Logger.getRootLogger();
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch(NumberFormatException e) {}
        if(id != null) {
            try {
                UserService service = getServiceFactory().getUserService();
                service.block(id);
                logger.info("User " + service.findById(id).getLogin() + " was blocked(or unblocked)");
            } catch(FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }
        return new Forward("/user/list.html");
    }
}
