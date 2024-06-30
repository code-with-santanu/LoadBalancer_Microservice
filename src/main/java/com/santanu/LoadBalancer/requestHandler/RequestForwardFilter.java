package com.santanu.LoadBalancer.requestHandler;


import com.santanu.LoadBalancer.model.Server;
import com.santanu.LoadBalancer.model.ServerPool;
import com.santanu.LoadBalancer.service.ServiceProviderImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

// It accepts every incoming request and filter the request which doFilter() method actually does.
@Component
public class RequestForwardFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Cast the servlet request to HttpServletRequest for processing
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI(); // Extract the request URI from the request url
        System.out.println(LocalDateTime.now() + " URI: " + requestURI);
        if (requestURI.startsWith("/lb")) {
            requestURI = requestURI.substring(3);
            System.out.println("ProcessedURI: " + requestURI);

            String queryString = httpRequest.getQueryString(); // Extract the queryString from the request url
            System.out.println("QueryString: " + queryString);

            // Construct the target URl based on the selected server by server-selector algorithm
            ServiceProviderImpl serviceProvider = new ServiceProviderImpl(new ServerPool());
            Server selectedServer = serviceProvider.getTargetServer();
            String targetServerURL = selectedServer.getServerURL();
    //        String targetServerURL = "https:/";
            String targetURL = targetServerURL + requestURI + (queryString != null ? "?" + queryString : "");
            System.out.println("TargetUrl: " + targetURL);

            try {
                // A HttpURLConnection is created to the target URL by openConnection() method
                // The request method (GET, POST, etc.) is set to match the original request.
                HttpURLConnection connection = (HttpURLConnection) new URL(targetURL).openConnection();
                connection.setRequestMethod(httpRequest.getMethod());
                System.out.println(LocalDateTime.now() + " Connection object created...");

                // update the selected serverLoad
                serviceProvider.updateServerLoad(selectedServer.getId());

                // Copy request headers
                httpRequest.getHeaderNames().asIterator().forEachRemaining(headerName -> {
                    connection.setRequestProperty(headerName, httpRequest.getHeader(headerName));
                });
                System.out.println(LocalDateTime.now() + " Headers are copied from the original request...");

                // Forward request body if applicable
                if ("POST".equalsIgnoreCase(httpRequest.getMethod()) || "PUT".equalsIgnoreCase(httpRequest.getMethod()) || "DELETE".equalsIgnoreCase(httpRequest.getMethod())) {
                    connection.setDoOutput(true);
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = httpRequest.getInputStream().readAllBytes();
                        os.write(input, 0, input.length);
                    }
                }

                // Get response from target server
                int responseCode = connection.getResponseCode();
                httpResponse.setStatus(responseCode);
                connection.getHeaderFields().forEach((key, value) -> {
                    if (key != null && value != null) {
                        httpResponse.setHeader(key, String.join(",", value));
                    }
                });
                System.out.println(LocalDateTime.now() + " Received response from server...");

                try (InputStream inpStream = connection.getInputStream()) {
                    byte[] responseBody = inpStream.readAllBytes();
                    httpResponse.getOutputStream().write(responseBody);
                } catch (IOException e) {
                    try (InputStream es = connection.getErrorStream()) {
                        if (es != null) {
                            byte[] errorBody = es.readAllBytes();
                            httpResponse.getOutputStream().write(errorBody);
                        }
                    }
                }
                System.out.println(LocalDateTime.now() + " Returned the response...");
            } catch (IOException e) {
                // If forwarding fails, log the error
                e.printStackTrace();

            }
        }
        else {
            System.out.println(LocalDateTime.now() + " Passing through: " + requestURI); // Log the non-forwarded request
            // If the request does not match the pattern, continue the filter chain
            try{
                chain.doFilter(httpRequest, httpResponse);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void destroy() {
        // Cleanup code if necessary
    }

}

