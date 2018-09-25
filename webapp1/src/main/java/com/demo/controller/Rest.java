package com.demo.controller;

/*
 * Created by lep on 18-8-18.
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.context.J2EContext;
import org.springframework.http.HttpMethod;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.buji.pac4j.subject.Pac4jPrincipal;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.pac4j.cas.profile.CasProxyProfile;
import org.springframework.web.bind.annotation.ResponseBody;


import com.demo.utils.HttpProxy;
@org.springframework.stereotype.Controller
public class Rest{

/**
 * Return the username of the authenticated user (based on pac4j security).
 *
 * @return the authenticated username.
 */
    public static String getProxyTicket(final HttpServletRequest request, final HttpServletResponse response, final String url) {
            String pt = null;
    if (request != null && response != null) {
        final J2EContext context = new J2EContext(request, response);
        final ProfileManager manager = new ProfileManager(context);
        Optional<CommonProfile> optProfile = manager.get(true);

		if (optProfile.isPresent()) {
			CommonProfile profile = optProfile.get();
			if (profile instanceof CasProxyProfile) {
				CasProxyProfile casProxyProfile = (CasProxyProfile) profile;
				pt = casProxyProfile.getProxyTicketFor(url);
			}
        }
		}

		return pt;
    } 

    // @ResponseBody
    public void doRest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "https://client.lep.cn:9444/webapp2/user/users?client_name=casproxy";
		
        String ticket = null;
        String urlProxy = "https://client.lep.cn:9444/webapp2";
		ticket = getProxyTicket(req, resp, urlProxy);

        url = url.concat("&ticket=");
        url = url.concat(ticket);
        String result = HttpProxy.httpRequest(url, "", HttpMethod.GET);

        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().println(result);
        // resp.getWriter().println(get(obj, "GET"));
        // return result;
    }

    private static String get(final URL url, final String method)
            throws UnsupportedEncodingException, MalformedURLException, IOException, ProtocolException {

        final HttpURLConnection httpsConn = (HttpURLConnection) url.openConnection();
        httpsConn.setConnectTimeout(5000);
        httpsConn.setReadTimeout(5000);
        httpsConn.setRequestMethod(method.toUpperCase());
        httpsConn.setDoOutput(true);

        final int code = httpsConn.getResponseCode();
        System.out.println(" ### code: " + code);

        final InputStreamReader inputStreamReader = new InputStreamReader(httpsConn.getInputStream());
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        final StringBuilder sb = new StringBuilder();
        String inputLine = "";
        while ((inputLine = bufferedReader.readLine()) != null) {
            sb.append(inputLine);
        }

        final String body = sb.toString();

        return body;
    }
}
