package controller;

import entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MainAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session != null) {
            User user = (User)session.getAttribute("currentUser");
            if(user != null) {
                switch(user.getRole()) {
                    case ADMINISTRATOR:
                        return new Forward("/user/list.html");
                    case USER:
                        return new Forward("/service/list.html");
                    default:
                        return new Forward("/login.html");
                }
            } else {
                return new Forward("/login.html");
            }
        } else {
            return new Forward("/login.html");
        }
    }
}
