package controller;

import entities.Role;
import entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class SecurityFilter implements Filter {
    private static final Map<String, Set<Role>> permissions = new HashMap<>();

    static {
        Set<Role> all = new HashSet<>();
        all.addAll(Arrays.asList(Role.values()));
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMINISTRATOR);
        Set<Role> user = new HashSet<>();
        user.add(Role.USER);

        permissions.put("/logout", all);
        permissions.put("/user/list", all);
        permissions.put("/user/save", all);
        permissions.put("/user/edit", all);
        permissions.put("/user/add", admin);

        permissions.put("/user/delete", admin);

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse)servletResponse;
        String url = httpReq.getRequestURI();
        String context = httpReq.getContextPath();
        int postfixIndex = url.lastIndexOf(".html");
        if(postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        Set<Role> roles = permissions.get(url);
        if(roles != null) {
            HttpSession session = httpReq.getSession(false);
            if(session != null) {
                User user = (User)session.getAttribute("currentUser");
                if(user != null && roles.contains(user.getRole())) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        httpResp.sendRedirect(context + "/login.html?message=login.message.access.denied");
    }

    @Override
    public void destroy() {

    }
}
