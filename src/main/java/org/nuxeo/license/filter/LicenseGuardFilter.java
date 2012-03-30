package org.nuxeo.license.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.license.api.LicenseManager;
import org.nuxeo.license.api.UserSessionValidity;
import org.nuxeo.runtime.api.Framework;

public class LicenseGuardFilter implements Filter, HttpSessionListener {

    protected final static Log log = LogFactory.getLog(LicenseGuardFilter.class);

    protected LicenseManager lm;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        lm = Framework.getLocalService(LicenseManager.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Principal principal = httpRequest.getUserPrincipal();
        HttpSession session = httpRequest.getSession(false);
        // XXX check config
        if (session != null && principal != null) {

            String sid = session.getId();
            UserSessionValidity validity = lm.validateUserSession(
                    (NuxeoPrincipal) principal, sid);
            if (UserSessionValidity.BlackListed == validity) {
                handleDuplicicatedConnection(request, response, principal, sid);
                return;
            } else if (UserSessionValidity.NoMoreSessions == validity) {
                handleNoValidLicense(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    protected void handleNoValidLicense(ServletRequest request,
            ServletResponse response) throws IOException {
        String message = "No valid license found!";
        log.warn(message);
        response.getWriter().write(message);
        // XXX redirect or whatever
    }

    protected void handleDuplicicatedConnection(ServletRequest request,
            ServletResponse response, Principal principal, String sid)
            throws IOException {
        String message = "User" + principal.getName()
                + " has a duplicated connection on Session " + sid;
        log.warn(message);
        response.getWriter().write(message);
        // XXX redirect or whatever
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // NOP
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String sid = event.getSession().getId();
        lm.releaseSession(sid);
    }
}
