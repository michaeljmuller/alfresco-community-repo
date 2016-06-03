package org.alfresco.web.app.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.extensions.surf.util.URLEncoder;


/**
 * HTTP Proxy Servlet
 * 
 * Provides the ability to submit a URL request via the Alfresco Server i.e.
 * the Alfresco server acts as a proxy.
 * 
 * This servlet accepts:
 * 
 * /proxy?endpoint=<endpointUrl>[&<argName>=<argValue>]*
 * 
 * Where:
 * 
 * - endpointUrl is the URL to make a request against
 * - argName is the name of a URL argument to append to the request
 * - argValue is the value of URL argument
 * 
 * E.g.:
 * 
 * /proxy?endpoint=http://www.alfresco.com&arg1=value1&arg2=value2
 * 
 * @author davidc
 */
public class HTTPProxyServlet extends HttpServlet
{
    private static final long serialVersionUID = -576405943603122206L;
    
    private static final String PARAM_ENDPOINT = "endpoint";
    
    
    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        String endpoint = null;
        StringBuilder args = new StringBuilder(32);
        
        Map<String, String[]> parameters = req.getParameterMap();
        for (Map.Entry<String, String[]> parameter : parameters.entrySet())
        {
            String[] values = parameter.getValue();
            int startIdx = 0;
            
            if (parameter.getKey().equals(PARAM_ENDPOINT) && values.length != 0)
            {
                endpoint = values[0];
                startIdx++;
            }
            
            for (int i = startIdx; i < values.length; i++)
            {
                if (args.length() != 0)
                {
                    args.append("&");
                }
                args.append(parameter.getKey()).append('=').append(URLEncoder.encode(values[i]));
            }
        }
        
        if (endpoint == null || endpoint.length() == 0)
        {
            throw new IllegalArgumentException("endpoint argument not specified");
        }
        
        String url = endpoint + ((args.length() == 0) ? "" : "?" + args.toString());
        HTTPProxy proxy = new HTTPProxy(url, res);
        proxy.service();
    }

    /**
     * Construct a "proxied" URL
     * 
     * Note: the "proxied" URL is a relative url - it is assumed that the servlet path is /proxy
     * 
     * @param url  the URL to proxy
     * @return  the "proxied" url
     */
    public static String createProxyUrl(String url)
    {
        String proxy = "/proxy";
        if (url != null && url.length() > 0)
        {
            int argIndex = url.lastIndexOf("?");
            if (argIndex == -1)
            {
                proxy += "?endpoint=" + url;
            }
            else
            {
                proxy += "?endpoint=" + url.substring(0, argIndex) + "&" + url.substring(argIndex + 1);
            }
        }
        
        return proxy;
    }
}
