package core.fonts;

import com.vaadin.server.FontIcon;

public enum PowerGlassIcons implements FontIcon {
	PIE_CHART_ICON(0xe99a)
   ,LINE_CHART_ICON(0xe99b)
   ,BAR_CHART_ICON(0xe99c)
   ,COMMAND_ICON(0xea4e)
   ,GITHUB_ICON(0xeab3)
   ,CONTACT_ICON(0xe944)
   ,TEXT_FILE(0xe922)
   ,GEARS(0xe995)
   ,USERS(0xe972)
	;
	/*$___$*/ private 	int    	codepoint;
	/*@___@*//*___*/	PowerGlassIcons(int codepoint)	{/*___*/ this.codepoint	= codepoint;}
	@Override public 	String 	getMIMEType()  					{return  "";/*FontIcon.class.getSimpleName()+"there is no support for mime type");*/}
	@Override public 	String 	getFontFamily()					{return  "power-glass-icons";}
	@Override public 	int    	getCodepoint() 					{return  codepoint;}
	@Override public 	String 	getHtml()	   					{return  "<span class=\"v-icon\" style=\"font-family:"+getFontFamily()+";\"&#x"+Integer.toHexString(codepoint)+";</span>";}
}