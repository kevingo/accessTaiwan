package accesstaiwan.misc;

import javax.swing.text.html.HTMLEditorKit;

public class ParserGetter extends HTMLEditorKit {
	  
    //purely to make this methods public
    public HTMLEditorKit.Parser getParser() {
        return super.getParser();
    }
}