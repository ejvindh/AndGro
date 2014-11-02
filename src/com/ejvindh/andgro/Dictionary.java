package com.ejvindh.andgro;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dictionary {
	
	public static String[][] lookup(String search_string_orig, String shortname, 
			String gddfile, String datfile, String dict_dir, 
			int doubflag) throws IOException, java.sql.SQLException, ClassNotFoundException {
		// Hovedopgaven for denne metode er at finde ud af hvilke tabeller der skal kigges i i Gdd-databasen
		// Til slut kaldes metoden getEntries for at finde vektorerne i gdd-databasen for de indtastede søgeord
		// Og getRawEntryText bruges til at udtrække selve opslagene i dat-databasen
		
		String search_string = search_string_orig.toLowerCase()
				.replaceAll("[éèêë]", "e")
				.replaceAll("[àâ]", "a")
				.replaceAll("[ùûü]", "u")
				.replaceAll("[îï]", "i")
				.replaceAll("[ôö]", "o")
				.replaceAll("ÿ", "y")
				.replaceAll("ç", "c")
				.replaceAll("ñ", "n")
				.replaceAll("ß", "ss")
				.replaceAll("œ", "oe");
		String[][] results = new String[2][3];
		ArrayList<String> tables = new ArrayList<String>();
		tables.add("lookup");
		tables.add("reverse");
		tables.add("collocation_lookup");
		File sql_file = new File(dict_dir + "/" + gddfile ); 
		SQLiteDatabase sql_conn = SQLiteDatabase.openDatabase(
				sql_file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
		RandomAccessFile dat_conn = new RandomAccessFile(dict_dir+"/"+datfile, "r");
		
		if (doubflag > 1) { // hvis ordbogen er tovejs
			for (int d_=0; d_<2; d_++) {
				boolean d = d_==0;
				int t_count = 0;
				for (String table: tables) {
					if (table.equals("reverse") || table.equals("collocation_lookup")) {
						d=!d;
					}
					//søg ikke i reverse, hvis databasen ikke indeholder reverse
					if ((table.equals("reverse") && (doubflag==0 || doubflag==2))) {
						results[d_][t_count]="";
					} else {
						ArrayList<int[]> entries=getEntries(sql_conn, search_string, table, d);
						String entrytext=getRawEntryText(dat_conn, entries);
						results[d_][t_count]=entrytext;
					}
					t_count++;
				}
			}
		} else { // hvis ordbogen er envejs
			boolean d = true;
			int t_count = 0;
			for (String table: tables) {
				//søg ikke i reverse, hvis databasen ikke indeholder reverse
				if ((table.equals("reverse") && (doubflag==0 || doubflag==2))) {
					results[0][t_count]="";
				} else {
					ArrayList<int[]> entries=getEntries(sql_conn, search_string, table, d);
					String entrytext=getRawEntryText(dat_conn, entries);
					results[0][t_count]=entrytext;
				}
				t_count++;
			}
		}
		sql_conn.close();
		dat_conn.close();
		for (int x=0; x<=((doubflag^1)&2)/2; x++) { //ligningen giver 1, hvis envejs; ellers 2
			if (results[x][0].equals("")) {
				StringBuffer henv = new StringBuffer();
				henv.append("Opslaget er fundet i");
				if (results[x][1].compareTo("")!=0) {
					henv.append(" 'omvendt søgning'");
					if (results[x][2].compareTo("")!=0) henv.append(" &");
				}
				if (results[x][2].compareTo("")!=0) henv.append(" Eksempelsætninger");
				if (henv.toString().equals("Opslaget er fundet i")) {
					results[x][0]="Opslag ikke fundet";
				} else {
					results[x][0]=henv.toString();
				}
			}
		}
		return results;
		/* results[0][0]=fromdanish_lookup
		 * results[0][1]=fromdanish_reverse
		 * results[0][2]=fromdanish_collocation_lookup (eksempelsætninger)
		 * results[1][0]=todanish_lookup
		 * results[1][1]=todanish_reverse
		 * results[1][2]=todanish_collocation_lookup (eksempelsætninger)
		 */
	}
	
	public static ArrayList<int[]> getEntries(SQLiteDatabase sql_conn, String search_string, String table, boolean d) 
			throws SQLException, java.sql.SQLException {
		// Denne metode bruges til at finde vektorerne i gdd-databasen for de indtastede søgeord

		String[] src_terms = search_string.split(" ");
        ArrayList<String> src_terms_list = new ArrayList<String>();
		for(int i=0; i< src_terms.length; i++){
        	src_terms_list.add(src_terms[i]);
        }
        int fromDanish=0;
        if (d) fromDanish=1; else fromDanish=2;
        boolean first_term = true;
		ArrayList<Integer> entry_ids = new ArrayList<Integer>();
		ArrayList<Integer> term_entry_ids = new ArrayList<Integer>();
		for (String term : src_terms_list) {
			String selectQuery = "select * from " + table + fromDanish + " where word_ like '" + term + "'";
			Cursor rows = sql_conn.rawQuery(selectQuery, null);
			term_entry_ids.clear();
			if (rows.moveToFirst()) {
				do {
					term_entry_ids.add(rows.getInt(0));
	            } while (rows.moveToNext());
	        }
			if (first_term) {
				first_term = false;
	    		entry_ids.addAll(term_entry_ids);
	    	} else {
				ArrayList<Integer> a1 = new ArrayList<Integer>(entry_ids);
	    		entry_ids.clear();
	    		for (int a : term_entry_ids) {
	    			if (a1.contains(a)) entry_ids.add(a);
	    		}
	    	}
			rows.close();
	    }
	    ArrayList<int[]> entries = new ArrayList<int[]>();
		for (int entry_id: entry_ids) {
			String selectQuery = "select * from entries" + fromDanish + " where id_ = " + entry_id;
			Cursor rows = sql_conn.rawQuery(selectQuery, null);
			if (rows.moveToFirst()) {
	            do {
		    		entries.add(new int [] {entry_id, rows.getInt(3), rows.getInt(4)});
	            } while (rows.moveToNext());
	        }
	    }
		return entries;
	}

	public static String getRawEntryText(RandomAccessFile dat_file, ArrayList<int[]> entries) throws IOException {
		ArrayList<String> raw_entries = new ArrayList<String>();
		// Denne metode bruges til at udtrække selve opslagene i dat-databasen

		for (int i=0; i<entries.size(); i++) {
			int entry_id=entries.get(i)[0];
			int offset=entries.get(i)[1];
			int nbyte=entries.get(i)[2];
			dat_file.seek(offset);
			ArrayList<Byte> data = new ArrayList<Byte>();
			for (int n=0; n<nbyte; n++) {
			    data.add(n, dat_file.readByte());
			}
			String raw_entry = Groparser.parse_entry(data, entry_id);
			String filtered_entry = Groparser.filter_entry(raw_entry);
			raw_entries.add(filtered_entry);
		}
		StringBuffer raw_entries_collect_to_string = new StringBuffer();
		for (String r:raw_entries) {
			raw_entries_collect_to_string.append(r);
		}
		return raw_entries_collect_to_string.toString();
	}
}
