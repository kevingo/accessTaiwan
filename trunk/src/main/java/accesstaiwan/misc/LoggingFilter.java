package accesstaiwan.misc;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

public final class LoggingFilter implements Filter 
{
   private FilterConfig filterConfig = null;
   protected static Logger logger = Logger.getLogger("LoggingFilter");

   public void doFilter(ServletRequest request, ServletResponse response,
     FilterChain chain)
     throws IOException, ServletException 
   {

     long start = System.currentTimeMillis();
     String address =   request.getRemoteAddr();
     String file = ((HttpServletRequest) request).getRequestURI();
     
     logger.debug("User access! " +      
             " User IP: " + address +      
             ", Resource: " + file + 
             ", Currrent Time" +  getDateTime()
     );     
     
     chain.doFilter(request, response);

    
   }

   public void destroy() { }

   public void init(FilterConfig filterConfig) {
     this.filterConfig = filterConfig;
   }
   
   public String getDateTime() {
	   SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	   Date date = new Date();
	   String strDate = sdFormat.format(date);
	   System.out.println(strDate);
	   return strDate;
   }
}