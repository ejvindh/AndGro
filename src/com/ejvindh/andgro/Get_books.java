package com.ejvindh.andgro;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class Get_books {
	/* dictionaries.get(i)[0] = shortname
	   * dictionaries.get(i)[1] = [long]name
	   * dictionaries.get(i)[2] = gddfile
	   * dictionaries.get(i)[3] = datfile
	   * dictionaries.get(i)[4] = doubflag
	   * 	0 => envejs, uden reverse (storenda, stordaen, ret, frem, dansk, syn)
	   * 	1 => envejs, med reverse (vel næppe realistisk)
	   * 	2 => tovejs, uden reverse (no, it, fag)
	   * 	3 => tovejs, med reverse (en, ty, fr, sv, es)
	   * dictionaries.get(i)[5] = listname   */
	
	
	private static final String SHORT_NAME = "shortname";
	private static final String NAME = "name";
	private static final String GDD_FILE = "gddfile";
	private static final String DAT_FILE = "datfile";
	private static final String DOUB_FLAG = "doubflag";
	private static final String LIST_NAME = "listname";
	private static String currentTag = null;
	private static String dictionary[] = new String[6];
	private static String dictionary_dir;
	
	public static ArrayList<String[]> parse_dictionaries(Context context, String dict_dir) {
		
		ArrayList<String[]> dictionaries = new ArrayList<String[]>();
		dictionary_dir = dict_dir;
		
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			InputStream stream = context.getResources().openRawResource(R.raw.books);
			xpp.setInput(stream, null);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					handleStartTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					currentTag = null;
					if (xpp.getName().equals("book")) {
						File gddfile = new File(dictionary_dir + "/" + dictionary[2] );
						File datfile = new File(dictionary_dir + "/" + dictionary[3] );
						if (gddfile.exists() && datfile.exists()) {
							dictionaries.add(new String [] {dictionary[0], dictionary[1], 
									dictionary[2], dictionary[3], dictionary[4], dictionary[5]});
						}
					}
				} else if (eventType == XmlPullParser.TEXT) {
					handleText(xpp.getText());
				}
				eventType = xpp.next();
			}
			
		} catch (NotFoundException e) {
			Log.d("parse_dictionaries", e.getMessage());
		} catch (XmlPullParserException e) {
			Log.d("parse_dictionaries", e.getMessage());
		} catch (IOException e) {
			Log.d("parse_dictionaries", e.getMessage());
		}
		return dictionaries;
	}
	
	
	private static void handleStartTag(String name) {
		if (name.equals("books") || name.equals("book")) {
			return;
		}
		else {
			currentTag = name;
		}
	}
	
	private static void handleText(String text) {
		String xmlText = text;
		if (dictionary != null && currentTag != null) {
			if (currentTag.equals(SHORT_NAME)) {
				dictionary[0]= xmlText;
			} 
			else if (currentTag.equals(NAME)) {
				dictionary[1]= xmlText;
			}
			else if (currentTag.equals(GDD_FILE)) {
				dictionary[2]= xmlText;
			}
			else if (currentTag.equals(DAT_FILE)) {
				dictionary[3]= xmlText;
			}
			else if (currentTag.equals(DOUB_FLAG)) {
				dictionary[4]= xmlText;
			}
			else if (currentTag.equals(LIST_NAME)) {
				dictionary[5]= xmlText;
			}
		}
	}
}
