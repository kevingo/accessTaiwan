package accesstaiwan.misc;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTMLEditorKit;
  
public class ParseHTML extends HTMLEditorKit.ParserCallback {
  
    private boolean inHeader=false;
    public ParseHTML() {}
  
    // 將 Parse HTML 後的資料印出
    public void handleText(char[] text, int position) {
       if (inHeader) {
    	   String str = String.valueOf(text);
    	   if(str.contains(":"))     		   
    		   System.out.print("時間  : ");
    	   else
    		   System.out.print("車次  : ");
    	   if(str.length()==0)
    		   str = "NO";    		   
           System.out.println(text);
       }
    }
  
    // Parse HTML Start Tag
    public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
    	    	
        if (tag == HTML.Tag.TD) {
        	int attrCount = attributes.getAttributeCount();
        	
        	if(attributes.getAttribute(Attribute.TITLE)!=null || attributes.getAttribute(Attribute.CLASS)!=null) {
        		this.inHeader = true;
        		//System.out.println("handleTag : ");
				System.out.println(attributes.toString().split("=")[1]);
        	}        
    	}
        
    }
  
    // Parse HTML End Tag
    public void handleEndTag(HTML.Tag tag, int position) {
        if (tag == HTML.Tag.TD) {
            inHeader = false;
        }
    }
  
    public static void main(String[] args) {
  
        ParserGetter kit = new ParserGetter();
        HTMLEditorKit.Parser parser = kit.getParser();
        
        HTMLEditorKit.ParserCallback callback = new ParseHTML();
  
        try {
            URL u = new URL("http://www.thsrc.com.tw/tc/ticket/tic_time_pop_summary.asp?sdate=2012/02/10");
            InputStream in = u.openStream();
            InputStreamReader r = new InputStreamReader(in, "UTF-8");
            
            parser.parse(r, callback, true);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}