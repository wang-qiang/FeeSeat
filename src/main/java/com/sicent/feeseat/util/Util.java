/**
 * 
 */
package com.sicent.feeseat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wangqiang
 * 
 */
public abstract class Util {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    /**
     * 判断是否为空
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || str.length() == 0;
    }

    /**
     * 从类路径下读取配置文件.
     * 
     * @param file
     * @return
     */
    public static Properties getProperties(String file) {
        Properties properties = new Properties();
        InputStream stream = Util.class.getClassLoader().getResourceAsStream(file);
        try {
            properties.load(stream);
        } catch (IOException e) {
            log.error("获取配置文件错误：" + e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                log.error("配置文件流关闭错误：" + e);
            }
        }

        return properties;
    }

    /**
     * 获取当前时间.
     * 
     * @return
     */
    public static String getNowString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    /**
     * 字符创数组去重.
     * 
     * @param arrs
     * @return
     */
    public static String[] removeRepeat(String[] arrs) {
        if (arrs == null || arrs.length <= 0) {
            return new String[0];
        }
        TreeSet<String> strSet = new TreeSet<String>();
        for (String str : arrs) {
            strSet.add(str);
        }
        String[] result = new String[strSet.size()];
        strSet.toArray(result);
        return result;
    }

    /**
     * 按长度拆分短信. 这儿只是取出0到最大长度的字符串.
     * 
     * @param message
     * @param maxLen
     * @return
     */
    public static String splitMessageByByte(String strValue, int maxLen) {
        String strResult = "";
        char[] charStr = strValue.toCharArray();
        for (char c : charStr) {
            if ((c + "").getBytes().length > 1) {
                maxLen -= 2;
            } else {
                maxLen--;
            }
            if (maxLen < 0) {
                break;
            } else {
                strResult += c;
            }
        }
        return strResult;
    }

    /**
     * 递归拆分短信
     * 
     * @param str
     *            待拆分字符串
     * @param len
     *            拆分长度
     * @param backStr
     */
    public static void splitStr(String str, int len, List<String> backStr) {
        String strTemp = splitMessageByByte(str, len);
        if (strTemp.length() < str.length()) {
            backStr.add(strTemp);
            str = str.substring(strTemp.length(), str.length());
            splitStr(str, len, backStr);
        } else {
            backStr.add(strTemp);
        }
    }

    /**
     * 获取客户端ip地址
     * 
     * @param request
     * @return
     */
    public static String getUserIp(HttpServletRequest request) {
        String ip = null;
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        return ip;
    }

    /**
     * 获取服务器IP地址
     * 
     * @return
     */
    public static String getHostIp() {
        InetAddress addr;
        String ip = "";
        try {
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();// 获得本机IP
        } catch (UnknownHostException e) {
            log.error("获取本机ip失败" + e.getMessage());
        }
        return ip;
    }

    /**
     * 判断当前用户是否为本机访问
     * 
     * @param request
     * @return
     */
    public static boolean isActiveIp(HttpServletRequest request) {

        String userIp = getUserIp(request);
        String hostIp = getHostIp();

        return ("0:0:0:0:0:0:0:1".equals(userIp) || "localhost".equals(userIp) || (Util
                .isEmpty(hostIp) && hostIp.equals(userIp)));
    }

    /**
     * ip合法性检查.
     * 
     * @param ip
     * @return
     */
    public static boolean isRightPhoneNum(String ip) {
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

    /**
     * 手机号码验证.
     * 
     * @param mobile
     * @return
     */
    public static boolean isPhoneNum(String mobile) {
        String regex = "^(13|14|15|16|18|19)\\d{9}$";
        return mobile.matches(regex);
    }

    /**
     * 向指定URL发送GET方法的请求.
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数是 name1=value1&name2=value2 的形式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error(e2.getMessage());
            }
        }
        return result;
    }

    /**
     * 发送邮件.
     * 
     * @param title
     *            邮件标题
     * @param content
     *            邮件内容
     * @param acceptors
     *            邮件接收人
     * @return
     */
    public static String sendEmail(String url, String title, String content, String acceptors) {
        try {
            title = URLEncoder.encode(title, "utf-8");
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("url编码失败" + e.getMessage());
        }
        String param = "title=" + title + "&message=" + content + "&address=" + acceptors;
        return sendGet(url, param);
    }

    /**
     * 获取指定时间对应的毫秒数.
     * 
     * @param time
     *            "HH:mm:ss"
     * @return
     */
    public static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置返回响应头.
     * 
     * @param response
     * @param contextType
     * @param charset
     */
    public static void setHeaderType(HttpServletResponse response, String contextType,
            String charset) {
        // text/html
        // text/plain
        // application/xml
        contextType = Util.isEmpty(contextType) ? "text/html" : contextType;
        charset = Util.isEmpty(charset) ? "utf-8" : charset;

        response.setContentType(contextType);
        response.setCharacterEncoding(charset);

        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    /**
     * 判断短信内容是否含有任何一个关键字.
     * 
     * @return
     */
    public static boolean isExistKeyWord(String msgContent, String[] keyWords) {
        if (isEmpty(msgContent)) {
            return false;
        }
        if (null == keyWords || keyWords.length <= 0) {
            return false;
        }
        for (String key : keyWords) {
            if (msgContent.contains(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取扩展名，没有获取到则使用默认扩展名<br>
     * 当文件中没有点（.）的时候，表示获取不到文件后缀
     * 
     * @param filePath
     *            源文件名
     * @param defExt
     *            默认扩展名
     * @return
     */
    public static String getExtension(String filePath, String defExt) {
        if (!isEmpty(filePath)) {
            int i = filePath.lastIndexOf('.');

            if ((i > -1) && (i < (filePath.length() - 1))) {
                return filePath.substring(i + 1);
            }
        }
        return defExt;
    }

    /**
     * 获取文件所在路径
     * 
     * @param filePath
     *            源文件地址
     * @return
     */
    public static String getFilePathOnly(String filePath) {
        if (!isEmpty(filePath)) {
            int lastDot = filePath.lastIndexOf('.');

            if ((lastDot > -1) && (lastDot < (filePath.length()))) {
                return filePath.substring(0, lastDot);
            }
        }
        return filePath;
    }

    /**
     * 获取文件名及后缀
     * 
     * @param filePath
     *            源文件地址
     * @return
     */
    public static String getFileNameAndExt(String filePath) {

        if (!isEmpty(filePath)) {
            int lastSlash = filePath.lastIndexOf("\\");

            if (lastSlash == -1) {
                lastSlash = filePath.lastIndexOf("/");
            }

            if (lastSlash > -1) {
                return filePath.substring(lastSlash + 1);
            }
        }

        return filePath;
    }

    /**
     * 获取扩展名前免得部分，最后路径后面的部分
     * 
     * @param filePath
     * @return
     */
    public static String getFileNameOnly(String filePath) {
        if (!isEmpty(filePath)) {
            int lastDot = filePath.lastIndexOf('.');
            int lastSlash = filePath.lastIndexOf("\\");

            if (lastSlash == -1) {
                lastSlash = filePath.lastIndexOf("/");
            }

            if (lastSlash == -1) {
                lastSlash = 0;
            } else {
                lastSlash += 1;
            }

            if ((lastDot > -1) && (lastDot < (filePath.length())) && (lastDot > lastSlash)) {
                return filePath.substring(lastSlash, lastDot);
            }
        }
        return filePath;
    }

    public static void main(String[] args) {
        Properties properties = getProperties("config/config.properties");

        System.out.println(properties.getProperty("image_upload_type"));

    }
}
