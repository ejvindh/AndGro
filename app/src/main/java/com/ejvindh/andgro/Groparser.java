package com.ejvindh.andgro;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Groparser {
	
	private static byte[] KEY = {
		(byte) 0xBA, (byte) 0x59, (byte) 0xD9, (byte) 0xC2, (byte) 0x32, (byte) 0xB7, (byte) 0x21, (byte) 0x78, (byte) 0xB5, (byte) 0x86, (byte) 0x0C, (byte) 0x8C,
		(byte) 0xA7, (byte) 0x3E, (byte) 0xA5, (byte) 0x12, (byte) 0xA2, (byte) 0xA4, (byte) 0x4B, (byte) 0x95, (byte) 0xE0, (byte) 0x31, (byte) 0xBD, (byte) 0x9E,
		(byte) 0x4D, (byte) 0x86, (byte) 0x45, (byte) 0xCE, (byte) 0x17, (byte) 0xD5, (byte) 0x5D, (byte) 0x7D, (byte) 0x08, (byte) 0xC0, (byte) 0x52, (byte) 0x40,
		(byte) 0xA3, (byte) 0x6E, (byte) 0x86, (byte) 0x1B, (byte) 0xD4, (byte) 0xAC, (byte) 0xBA, (byte) 0xC4, (byte) 0x5B, (byte) 0x2B, (byte) 0xC4, (byte) 0xE1,
		(byte) 0x84, (byte) 0x12, (byte) 0x19, (byte) 0x91, (byte) 0x88, (byte) 0xB1, (byte) 0xEC, (byte) 0x5A, (byte) 0x52, (byte) 0x61, (byte) 0x39, (byte) 0x25,
		(byte) 0xA8, (byte) 0x98, (byte) 0x07, (byte) 0x26, (byte) 0x35, (byte) 0x64, (byte) 0x5D, (byte) 0xA4, (byte) 0x98, (byte) 0x32, (byte) 0xDB, (byte) 0x57,
		(byte) 0x57, (byte) 0x5A, (byte) 0xCC, (byte) 0xDD, (byte) 0x2A, (byte) 0x67, (byte) 0xE0, (byte) 0x11, (byte) 0x65, (byte) 0xC9, (byte) 0x61, (byte) 0x47,
		(byte) 0x62, (byte) 0x79, (byte) 0x60, (byte) 0x6E, (byte) 0x22, (byte) 0x27, (byte) 0x17, (byte) 0x86, (byte) 0x67, (byte) 0x29, (byte) 0x72, (byte) 0x59,
		(byte) 0x72, (byte) 0xB8, (byte) 0xDB, (byte) 0x14, (byte) 0x2D, (byte) 0x3A, (byte) 0x53, (byte) 0x72, (byte) 0x36, (byte) 0x4C, (byte) 0xC8, (byte) 0xED,
		(byte) 0xC6, (byte) 0x2E, (byte) 0xEA, (byte) 0xE4, (byte) 0xBD, (byte) 0x23, (byte) 0x3D, (byte) 0x16, (byte) 0x0D, (byte) 0x53, (byte) 0x3C, (byte) 0x13,
		(byte) 0xE0, (byte) 0x50, (byte) 0xC7, (byte) 0xBD, (byte) 0x3C, (byte) 0xB7, (byte) 0x92, (byte) 0x57, (byte) 0xEE, (byte) 0xD6, (byte) 0x14, (byte) 0xD5,
		(byte) 0x5D, (byte) 0xBE, (byte) 0x3B, (byte) 0x9E, (byte) 0x4D, (byte) 0xEE, (byte) 0x4D, (byte) 0x63, (byte) 0x13, (byte) 0x05, (byte) 0x29, (byte) 0xCD,
		(byte) 0x7D, (byte) 0x34, (byte) 0xD9, (byte) 0x2A, (byte) 0x10, (byte) 0xAE, (byte) 0xBB, (byte) 0xA7, (byte) 0x3B, (byte) 0x2A, (byte) 0x26, (byte) 0x20,
		(byte) 0x79, (byte) 0x4C, (byte) 0x47, (byte) 0x2B, (byte) 0x0C, (byte) 0x65, (byte) 0x75, (byte) 0x09, (byte) 0xB4, (byte) 0xC3, (byte) 0x36, (byte) 0x75,
		(byte) 0x87, (byte) 0x25, (byte) 0x61, (byte) 0xA1, (byte) 0xA3, (byte) 0xB4, (byte) 0x44, (byte) 0x68, (byte) 0xDE, (byte) 0xDD, (byte) 0x45, (byte) 0x0C,
		(byte) 0xB8, (byte) 0xED, (byte) 0x8E, (byte) 0xC1, (byte) 0x2E, (byte) 0x4B, (byte) 0x5C, (byte) 0x4E, (byte) 0x15, (byte) 0x93, (byte) 0x8B, (byte) 0x46,
		(byte) 0xC3, (byte) 0x53, (byte) 0x79, (byte) 0x02, (byte) 0x74, (byte) 0x8D, (byte) 0x2C, (byte) 0x7B, (byte) 0x6A, (byte) 0x25, (byte) 0x09, (byte) 0x31,
		(byte) 0x9E, (byte) 0xBE, (byte) 0xAB, (byte) 0x40, (byte) 0x38, (byte) 0x04, (byte) 0x98, (byte) 0x87, (byte) 0xD1, (byte) 0x40, (byte) 0x36, (byte) 0xC4,
		(byte) 0xDD, (byte) 0xCC, (byte) 0x9E, (byte) 0x53, (byte) 0x03, (byte) 0x98, (byte) 0xC1, (byte) 0x7A, (byte) 0xE8, (byte) 0x98, (byte) 0xB2, (byte) 0x1C,
		(byte) 0x29, (byte) 0x6D, (byte) 0x53, (byte) 0xC2, (byte) 0x26, (byte) 0x1B, (byte) 0xE7, (byte) 0x64, (byte) 0x2C, (byte) 0x45, (byte) 0xEE, (byte) 0xAC,
		(byte) 0x98, (byte) 0x0A, (byte) 0xB3, (byte) 0x8A, (byte) 0xBE, (byte) 0xA0, (byte) 0x77, (byte) 0xDB, (byte) 0x66, (byte) 0x65, (byte) 0x0A, (byte) 0xB7,
		(byte) 0x25, (byte) 0x6E, (byte) 0xCB, (byte) 0xD2, (byte) 0xD8, (byte) 0x4B, (byte) 0x32, (byte) 0x6D, (byte) 0xD5, (byte) 0xE0, (byte) 0xB6, (byte) 0xBA,
		(byte) 0xE7, (byte) 0xE8, (byte) 0x84, (byte) 0xCE, (byte) 0xC7, (byte) 0x76, (byte) 0xC9, (byte) 0xC0, (byte) 0x07, (byte) 0x1D, (byte) 0x21, (byte) 0x83,
		(byte) 0x07, (byte) 0x69, (byte) 0xAA, (byte) 0xBA, (byte) 0x9A, (byte) 0xE4, (byte) 0xC5, (byte) 0x99, (byte) 0xB4, (byte) 0xEA, (byte) 0x90, (byte) 0x14,
		(byte) 0x7E, (byte) 0xE3, (byte) 0x5C, (byte) 0x7D, (byte) 0xEA, (byte) 0x70, (byte) 0xC2, (byte) 0x41, (byte) 0xBB, (byte) 0xB1, (byte) 0x97, (byte) 0x39,
		(byte) 0xD6, (byte) 0x2C, (byte) 0x1D, (byte) 0x80, (byte) 0x62, (byte) 0x1A, (byte) 0xA7, (byte) 0x5C, (byte) 0x31, (byte) 0x51, (byte) 0xC9, (byte) 0xB8,
		(byte) 0x0D, (byte) 0xEC, (byte) 0x30, (byte) 0xA0, (byte) 0xA5, (byte) 0x5D, (byte) 0x99, (byte) 0xB1, (byte) 0x17, (byte) 0x9A, (byte) 0x08, (byte) 0x53,
		(byte) 0x6E, (byte) 0xC8, (byte) 0x21, (byte) 0xB4, (byte) 0xA8, (byte) 0xC2, (byte) 0xDA, (byte) 0xB5, (byte) 0x71, (byte) 0xE5, (byte) 0x27, (byte) 0x28,
		(byte) 0x44, (byte) 0xE4, (byte) 0x01, (byte) 0x87, (byte) 0x7A, (byte) 0x63, (byte) 0x22, (byte) 0xC9, (byte) 0x81, (byte) 0x31, (byte) 0xC8, (byte) 0x26,
		(byte) 0xB3, (byte) 0x3E, (byte) 0x8E, (byte) 0x1D, (byte) 0xC4, (byte) 0x3C, (byte) 0x27, (byte) 0x38, (byte) 0x04, (byte) 0x92, (byte) 0x37, (byte) 0x35,
		(byte) 0x35, (byte) 0xC0, (byte) 0x31, (byte) 0xAA, (byte) 0x18, (byte) 0x8B, (byte) 0xC9, (byte) 0xE8, (byte) 0x98, (byte) 0x6D, (byte) 0xD0, (byte) 0x71,
		(byte) 0xC9, (byte) 0x08, (byte) 0xDE, (byte) 0x23, (byte) 0x0D, (byte) 0xA2, (byte) 0x20, (byte) 0x9A, (byte) 0x87, (byte) 0xA8, (byte) 0xD6, (byte) 0xB6,
		(byte) 0x51, (byte) 0x8A, (byte) 0x05, (byte) 0x63, (byte) 0xDB, (byte) 0x39, (byte) 0x02, (byte) 0xB2, (byte) 0x64, (byte) 0xBE, (byte) 0xDB, (byte) 0x58,
		(byte) 0xDA, (byte) 0x46, (byte) 0x4D, (byte) 0x25, (byte) 0xD9, (byte) 0x3C, (byte) 0x66, (byte) 0x29, (byte) 0x66, (byte) 0x36, (byte) 0xC0, (byte) 0xA5,
		(byte) 0xA8, (byte) 0x28, (byte) 0xAB, (byte) 0x78, (byte) 0xA0, (byte) 0xC5,
};
	
static String parse_entry(ArrayList<Byte> entry_data, int entry_id) throws UnsupportedEncodingException {
	int key_offset =((entry_id + 0x170A8) * 1103) % 414;
	ArrayList<String> entry_data_chr = new ArrayList<>();
	for (int i=0; i<entry_data.size(); i++) {
		int encrypt = (entry_data.get(i) ^ KEY[(i + key_offset) % 414]) & 0xff;
		char c = (char) encrypt;
		entry_data_chr.add(i, Character.toString(c));
	}
	StringBuilder raw_entry = new StringBuilder();
	for (int j=0; j<entry_data_chr.size(); j++) raw_entry.append(entry_data_chr.get(j));
	String s = raw_entry.toString();
	return new String(s.getBytes("ISO-8859-1"), "UTF-8");
}

static String filter_entry(String raw_entry) {
	String raw_split = split_to_string(raw_entry);
	//System.out.println("\n\n" + raw_split);
	StringBuffer txt = new StringBuffer(raw_split);
	int first_div=txt.indexOf("<div>");
	if (first_div == -1) first_div=txt.indexOf(".") + 1;// ellers giver uk-politikken og dk-hansen fejl
	if (first_div == -1) first_div=0; // for at undgå at næste linie giver fejl, hvis pejlemærkerne ikke er fundet
	txt.delete(0, first_div);
	txt.replace(0,txt.length(), sub(txt, "h3", "h4"));
	txt.replace(0,txt.length(), sub(txt, "h2", "h3"));
	txt.replace(0,txt.length(), sub(txt, "<span>", "")); // xxxxevt. erstatte med <div>
	txt.replace(0,txt.length(), sub(txt, "</span>", "")); // xxxxevt. erstatte med </div>
	txt.replace(0,txt.length(), sub(txt, "inflect>", "table>"));
	txt.replace(0,txt.length(), sub(txt, "<ih>", "<td>"));
	txt.replace(0,txt.length(), sub(txt, "<ih colspan=\"1\">", "<td colspan=\"1\"><br/>"));
	txt.replace(0,txt.length(), sub(txt, "<ih colspan=\"2\">", "<td colspan=\"2\"><br/>"));
	txt.replace(0,txt.length(), sub(txt, "<ih colspan=\"3\">", "<td colspan=\"3\"><br/>"));
	txt.replace(0,txt.length(), sub(txt, "<fx>", "- fx:"));
	txt.replace(0,txt.length(), sub(txt, "â¢", "-"));
	txt.replace(0,txt.length(), sub(txt, "p1>", "i>"));
	txt.replace(0,txt.length(), sub(txt, "p2>", "i>"));
	txt.replace(0,txt.length(), sub(txt, "p3>", "i>"));
	txt.replace(0,txt.length(), sub(txt, "f1>", "i>"));
	txt.replace(0,txt.length(), sub(txt, "<m.>", "m."));
	txt.replace(0,txt.length(), sub(txt, "<f.>", "f."));
	txt.replace(0,txt.length(), sub(txt, "<n.>", "n."));
	txt.replace(0,txt.length(), sub(txt, "<pl.>", "pl."));
	txt.replace(0,txt.length(), sub(txt, "<syn>", "<b><br/><br/>synonymer: </b>"));
	txt.replace(0,txt.length(), sub(txt, "<syn2>", "<b><br/><br/>synonymer: </b>"));
	txt.replace(0,txt.length(), sub(txt, "<etym>", "<b><br/><br/>etymologi: </b>"));
	txt.replace(0,txt.length(), sub(txt, "<b>positiv</b>", "<b><u>Positiv:</u></b>"));
	txt.replace(0,txt.length(), sub(txt, "<b>komparativ</b>", "<b><u>Komparativ:</u></b>"));
	txt.replace(0,txt.length(), sub(txt, "<b>superlativ</b>", "<b><u>Superlativ:</u></b>"));
	txt.replace(0,txt.length(), sub(txt, "<i>sb. fk.</i>", "<i> sb. fk. </i>"));
	txt.replace(0,txt.length(), sub(txt, "<i>sb. itk.</i>", "<i> sb. itk. </i>"));
	txt.replace(0,txt.length(), sub(txt, "<i>adj.</i>", "<i> adj. </i>"));
	txt.replace(0,txt.length(), sub(txt, "<i>adv.</i>", "<i> adv. </i>"));
	txt.replace(0,txt.length(), sub(txt, "<i>vb.</i>", "<i> vb. </i>"));
	txt.replace(0,txt.length(), sub(txt, "<i>proprium</i>", "<i> proprium </i>"));
	txt.replace(0,txt.length(), sub(txt, "<od>", "")); 
		//nogle gange bærer <od> også en </font> (fx ved orddelinger) -- men ikke altid...
	txt.replace(0,txt.length(), sub(txt, "[LYD]", ""));
	txt.replace(0,txt.length(), sub(txt, "<a href=\"expand://", "<sup>{"));
	txt.replace(0,txt.length(), sub(txt, "\">[INFO]</a>", "}</sup>"));
	return txt.toString();
}

private static String sub(StringBuffer org_data, String oldstring, String newstring) {
	StringBuilder resulting_data;
	resulting_data = new StringBuilder(org_data.toString());
	int start = 0;
	while ((resulting_data.indexOf(oldstring, start)) != -1) {
		start = resulting_data.toString().indexOf(oldstring);
		int end = start+oldstring.length();
		resulting_data.replace(start, end, newstring);
	}
	return resulting_data.toString();
}

private static String split_to_string(String data) {
	String[] temp = data.trim().split("\0", -2);
	
	StringBuilder temp2 = new StringBuilder();
	for(String i : temp) temp2.append(i).append(" ");
	return temp2.toString();   
}


}
