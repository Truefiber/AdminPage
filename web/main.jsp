<%@ page import="org.jfree.chart.ChartFactory" %>
<%@ page import="org.jfree.chart.ChartRenderingInfo" %>
<%@ page import="org.jfree.chart.ChartUtilities" %>
<%@ page import="org.jfree.chart.JFreeChart" %>
<%@ page import="org.jfree.chart.entity.StandardEntityCollection" %>
<%@ page import="org.jfree.chart.plot.CategoryPlot" %>
<%@ page import="org.jfree.chart.plot.PlotOrientation" %>
<%@ page import="org.jfree.data.category.CategoryDataset" %>
<%@ page import="org.jfree.data.general.DatasetUtilities" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.logging.Logger" %>
<%@ page import="org.jfree.data.xy.XYSeriesCollection" %>
<%@ page import="org.jfree.chart.plot.XYPlot" %>
<%@ page import="org.jfree.chart.plot.Plot" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="US-ASCII"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %>
<
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <title>Login Success Page</title>
</head>
<body>
<%
    String userName = null;
    Logger logger = Logger.getLogger("Main");
    Cookie[] cookies = request.getCookies();
    if(cookies !=null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("user")){
                userName = cookie.getValue();
            }
        }
    }
    logger.info("Curren user: " + userName);
    if(userName == null) response.sendRedirect("login.html");
%>
<h3>Hi <%=userName %>, Login successful.</h3>
<br>

<div align="ceneter">
<table border="1">
        <%
            Map<String, Boolean> userMap = (Map<String, Boolean>) request.getAttribute("users");

            logger.info("Before loop, map " + userMap.toString());
            for (Map.Entry<String, Boolean> entry : userMap.entrySet()) {%>
                <tr>
                    <td>
                        <%
                            logger.info("Key: " + entry.getKey() + ", value: " + entry.getValue());
                            if (entry.getValue()) {
                                %><span style="color: green"><%=entry.getKey()%></span>
                         <%
                            } else {
                                %><span style="color: red"><%=entry.getKey()%></span><%

                            }

                        %>

                    </td>
                </tr>

            <%}%>

</table>

<%


    final XYSeriesCollection dataset = (XYSeriesCollection) request.getAttribute("statsdataset");


    final JFreeChart chart = ChartFactory.createXYLineChart(
            "Users", "Time", "Quantity",  dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

    final Plot plot = chart.getXYPlot();
    plot.setForegroundAlpha(0.5f);

    chart.setBackgroundPaint(new Color(249, 231, 236));

    try {
        final ChartRenderingInfo info = new ChartRenderingInfo
                (new StandardEntityCollection());

        final File file1 = new File(getServletConfig().getServletContext().getRealPath(".") + "/areachart.png");

                ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info);
    } catch (Exception e) {
        out.println(e);
    }

%>

<IMG SRC="areachart.png" WIDTH="600" HEIGHT="400" BORDER="0"
     USEMAP="#chart">



</div>

</body>
</html>