package org.lining.undertow.servlet;


import org.lining.undertow.logs.LogPortal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description:
 * date 2018/1/25
 *
 * @author lining1
 * @version 1.0.0
 */
public class MessageServlet extends HttpServlet {

    private static final String DEFAILT_PATTERN = "yyyy-MM-dd_HH-mm-ss";

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        SimpleDateFormat format = new SimpleDateFormat(DEFAILT_PATTERN);
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        writer.write(time);
        writer.close();
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        BufferedReader br = req.getReader();
        String json = br.readLine();
        PrintWriter out = resp.getWriter();
        out.write(json);
        out.flush();
        out.close();
        writeFile(json);
    }

    private void writeFile(String json){
        LogPortal.log2File(json);
    }


}
