package controller;

import controller.Account.AccountDataAction;
import controller.Account.AccountPayAction;
import controller.Account.BalanceUpAction;
import controller.Service.ServiceListAction;
import controller.User.UserAddAction;
import controller.User.UserBlockAction;
import controller.User.UserListAction;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private static Map<String, Class<? extends Action>> actions = new HashMap<>();
    static {
        actions.put("/", MainAction.class);
        actions.put("/index", MainAction.class);
        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);
        actions.put("/user/list", UserListAction.class);
        actions.put("/user/block", UserBlockAction.class);
        actions.put("/user/add", UserAddAction.class);
        actions.put("/service/list", ServiceListAction.class);
        actions.put("/account/index", AccountDataAction.class);
        actions.put("/account/pay", AccountPayAction.class);
        actions.put("/account/balance", BalanceUpAction.class);
    }

    public static Action getAction(String url) throws ServletException {
        Class<?> action = actions.get(url);
        if(action != null) {
            try {
                return (Action)action.newInstance();
            } catch(InstantiationException | IllegalAccessException | NullPointerException e) {
                throw new ServletException(e);
            }
        } else {
            return null;
        }
    }
}
