package com.sicent.feeseat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AuthValid {

    private static final Logger logger = LoggerFactory.getLogger(AuthValid.class);

    public static void main(String[] args) {
        boolean service = validateUser("username", "xxxx");
        System.out.println(service);
    }

    /**
     * 用户验证，配置基本soap
     * 
     * @param userName
     * @param pwd
     * @return
     */
    public static boolean validateUser(String userName, String pwd) {
        StringBuffer soapXml = new StringBuffer();
        soapXml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soapXml.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soapXml.append("<soap:Body>");
        soapXml.append("<checkLogin xmlns=\"http://tempuri.org/\">");
        soapXml.append("<userName>" + userName + "</userName>");
        soapXml.append(" <pwd>" + pwd + "</pwd>");
        soapXml.append("</checkLogin>");
        soapXml.append("</soap:Body>");
        soapXml.append("</soap:Envelope>");
        return getServiceResult("http://10.34.38.226:7634/DomainLoginService.asmx",
                soapXml.toString(), "http://tempuri.org/checkLogin");

    }

    /**
     * 
     * @param soapUrl
     *            WebService的地址 http://10.34.38.226:7634/DomainLoginService.asmx
     * @param xml2Send
     *            调用的WebService方法的xml字符串
     * @param soapAction
     *            WebService方法的命名空间，一定要有，否则调用失败，假如你在C#
     *            WebServices中使用了方法默认的命名空间的话，就使用http://tempuri.org，否则要与C#中定义的一致，后面紧跟调用的方法名。
     *            比如：http://tempuri.org/checkLogin，其中checkLogin是C# WebServices的方法名。
     * @throws IOException
     */
    private static boolean getServiceResult(String soapUrl, String xml2Send, String soapAction) {
        // Create the connection where we're going to send the file.
        URL url;
        BufferedReader in = null;
        try {
            url = new URL(soapUrl);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;

            // Change xml to byte[], so we can set the HTTP Cotent-Length
            // property. (See complete e-mail below for more on this.)

            byte[] b = xml2Send.getBytes();

            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", soapAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            // Set connect timeout
            httpConn.setConnectTimeout(10000);

            // Everything's set up; send the XML that was read in to b.
            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();

            // Read the response and write it to standard out.
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            in = new BufferedReader(isr);

            String inputLine;
            StringBuffer result = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }

            return (result.indexOf("<checkLoginResult>true</checkLoginResult>") != -1);

        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return false;

    }

}
