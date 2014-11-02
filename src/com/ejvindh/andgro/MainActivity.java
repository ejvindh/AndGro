package com.ejvindh.andgro;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener {

	public final static String EXTRA_MESSAGE = "com.ejvindh.andgro.MESSAGE";
	static final int CREATE_REQUEST_CODE = 0;
	private EditText src_txt;
	private Button src;
	private CheckBox todan;
	private ImageView todan_im;
	private CheckBox fromdan;
	private ImageView fromdan_im;
	private ToggleButton eks;
	private ToggleButton rev;
	private Spinner dictnr;
	private WebView show_search_result;
	private Boolean stay_on_flag = false;
	Menu menu;
	private String dict_dir;
	private int lang_sel;
	private int turnaroundFlag = 0;
	private ArrayAdapter<String> adapter;
	private ArrayList<String[]> dictionaries;
	private String[][] results = new String[2][3];
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		src = (Button) findViewById(R.id.src_button);
		src.setOnClickListener(this);
		todan = (CheckBox) findViewById(R.id.checkBox_todan);
		todan.setOnClickListener(this);
		todan_im = (ImageView) findViewById(R.id.imageView_todan);
		fromdan = (CheckBox) findViewById(R.id.checkBox_fromdan);
		fromdan.setOnClickListener(this);
		fromdan_im = (ImageView) findViewById(R.id.imageView_fromdan);
		eks = (ToggleButton) findViewById(R.id.toggleButton_eks);
		eks.setOnClickListener(this);
		rev = (ToggleButton) findViewById(R.id.toggleButton_rev);
		rev.setOnClickListener(this);
		show_search_result = (WebView) findViewById(R.id.webView_src_result);
		show_search_result.getSettings().setBuiltInZoomControls(true);
		show_search_result.getSettings().setDisplayZoomControls(false);
	    
		if (savedInstanceState == null) {
			//Ved første kørsel af onCreate 
			SharedPreferences settings = getSharedPreferences("AndGro_settings", 0);
			//Hent gemte settings, og hvis de ikke findes, så loade default
			dict_dir = settings.getString("dict_dir_settings", Environment.
				getExternalStorageDirectory().toString());
			lang_sel = settings.getInt("dictnr_settings", 0);
			fromdan.setChecked(settings.getBoolean("fromdan_settings", true));
			todan.setChecked(settings.getBoolean("todan_settings", true));
			eks.setChecked(settings.getBoolean("eks_settings", true));
			rev.setChecked(settings.getBoolean("rev_settings", true));
			for (int x=0; x<2; x++) {
				for (int y=0; y<3; y++) {
					results[x][y]="";
				}
			}
		} else {
			//Hvis telefonen er blevet drejet portrait/horisontal el. lign, læses variable ind igen
			turnaroundFlag = 2;
			dict_dir = savedInstanceState.getString("dict_dir");
			lang_sel = savedInstanceState.getInt("lang_sel");
			dict_dir = savedInstanceState.getString("dict_dir");
			fromdan.setChecked(savedInstanceState.getBoolean("fromdan"));
			todan.setChecked(savedInstanceState.getBoolean("todan"));
			eks.setChecked(savedInstanceState.getBoolean("eks"));
			rev.setChecked(savedInstanceState.getBoolean("rev"));

			stay_on_flag = savedInstanceState.getBoolean("stay_on");
			if (stay_on_flag) {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			} else {
				getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
			for (int x=0; x<2; x++) {
				for (int y=0; y<3; y++) {
					results[x][y] = savedInstanceState.getString("result"+x+y);
				}
			}
		}
		dictionaries = Get_books.parse_dictionaries(this, dict_dir);
		createSpinner(dictionaries, lang_sel);
		
		//Når der vælges nyt sprog
		dictnr.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				//ved skift af sprog
		    	set_views(dictnr.getSelectedItemPosition());
		    	if (turnaroundFlag > 0) {
		    		//Af en eller anden grund kommer vi to gange ind i denne listener, når enheden
		    		//drejes. For ikke at få nulstillet indholdet af Webview, er det derfor nødv.
		    		//at lave denne bremse på det, der står i else-delen
		    		turnaroundFlag--;
		    	} else {
		    		for (int x=0; x<2; x++) {
		    			for (int y=0; y<3; y++) {
		    				results[x][y]="";
		    			}
		    		}
		    	}
		    	show_results();
		    }
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }
		});

		src_txt = (EditText) findViewById(R.id.src_txt_widget);
		//Hold øje med om der tastes "afslut" på tastaturet
		src_txt.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		          if (!dictionaries.isEmpty()) {
		        	  get_results();
		        	  show_results();
		          }
		          return true;
		        }
		        return false;
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState (Bundle outState) {
		//B.la. hvis der skiftes fra landscape til portrait -- så skal alle variabler gemmes
		// (genindlæses i onCreate)
	    super.onSaveInstanceState(outState);
	    outState.putString("dict_dir", dict_dir);
	    outState.putInt("lang_sel", lang_sel);
	    outState.putBoolean("fromdan", fromdan.isChecked());
	    outState.putBoolean("todan", todan.isChecked());
	    outState.putBoolean("eks", eks.isChecked());
	    outState.putBoolean("rev", rev.isChecked());

	    MenuItem s_o_s = menu.findItem(R.id.stay_on_setting);
	    outState.putBoolean("stay_on", s_o_s.isChecked());

	    for (int x=0; x<2; x++) {
			for (int y=0; y<3; y++) {
				outState.putString("result"+x+y, results[x][y]);
			}
		}
	}
	
	private void createSpinner(ArrayList<String[]> dictionaries2, int lang_sel) {
		//Fyld indhold i Comboboxen
		String[] diction = new String[dictionaries.size()];
		if (!dictionaries.isEmpty()) {
			for (int i = 0; i < dictionaries.size(); i++) {
				diction[i]=dictionaries.get(i)[5];
			}
		}
		adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, diction);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dictnr = (Spinner) findViewById(R.id.select_lang);
		dictnr.setAdapter(adapter);
		if (lang_sel >= dictionaries.size()) {
			lang_sel = 0;
		}
		dictnr.setSelection(lang_sel);
	}

	private void set_views(int selectedItemPosition) {
		//Hvilke Checkboxes skal vises. Hvad skal greyes, osv.
		if (!dictionaries.isEmpty()) {
			if (!fromdan.isEnabled()) {
				src_txt.setEnabled(true);
				dictnr.setEnabled(true);
				src.setEnabled(true);
				fromdan.setEnabled(true);
				todan.setEnabled(true);
				eks.setEnabled(true);
				rev.setEnabled(true);
				show_search_result.loadDataWithBaseURL("fake-url", "<html></html>", "text/html", "UTF-8", null);
				show_search_result.loadUrl("about:blank");
			}
			int lang_sel = selectedItemPosition;
			int doubflag = Integer.parseInt(dictionaries.get(lang_sel)[4]);
			String flag_name_fromdan = "da"+dictionaries.get(lang_sel)[0];
			String flag_name_todan = dictionaries.get(lang_sel)[0]+"da";
			Resources res = getResources();
			int resourceId = res.getIdentifier(
					flag_name_fromdan, "drawable", getPackageName() );
			fromdan_im.setImageResource( resourceId );
			if (doubflag < 2) {
				todan.setVisibility(4);
				todan_im.setVisibility(4);
			} else {
				todan.setVisibility(0);
				todan_im.setVisibility(0);
				resourceId = res.getIdentifier(
						flag_name_todan, "drawable", getPackageName() );
				todan_im.setImageResource( resourceId );
			}
			if ((doubflag&1)==0) {
				rev.setVisibility(4);
			} else {
				rev.setVisibility(0);
			}
		} else {
			src_txt.setEnabled(false);
			dictnr.setEnabled(false);
			src.setEnabled(false);
			fromdan.setEnabled(false);
			todan.setEnabled(false);
			eks.setEnabled(false);
			rev.setEnabled(false);
			show_search_result.loadData("Der blev ikke fundet nogen ordbøger." +
					"<br><br><small><small>Ordbøgerne skal lægges ind i en mappe på enheden. Har du " +
					"allerede gjort dette, skal du klikke på menu-knappen, og søge efter " +
					"dem på enheden.<br><br>Denne ordbogslæser er kun kompatibel med " +
					"Gyldendals ordbøger i download-udgaven. Disse købes på " +
					"http://ordbog.gyldendal.dk<br><br>Når du har købt programmet, skal du " +
					"installere det (på en PC eller Mac) og så kan du hente database-filerne " +
					"inde i '%Program Files%Gyldendal/Røde Ordbøger/data'. Hver ordbog består " +
					"af en *.gdd og en *.dat-fil.<br><br>Eks: For at få adgang til den tyske " +
					"ordbog skal du således uploade filerne Tyskordbog.gdd og Tyskordbog.dat</small></small>"
					, "text/html; charset=utf-8", "UTF-8");
		}
	}

	private void get_results() {
		//Så skal der søges i ordbogsdatabaserne
		for (int x=0; x<2; x++) {
    		for (int y=0; y<3; y++) {
    			results[x][y]="";
    		}
    	}
		try {
			results = Dictionary.lookup(src_txt.getText().toString(), dictionaries.get(dictnr.getSelectedItemPosition())[0], 
					dictionaries.get(dictnr.getSelectedItemPosition())[2], dictionaries.get(dictnr.getSelectedItemPosition())[3], 
					dict_dir, Integer.parseInt(dictionaries.get(dictnr.getSelectedItemPosition())[4]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void show_results() {
		//Visning af opslaget i webview
		int doub = Integer.parseInt(dictionaries.get(dictnr.getSelectedItemPosition())[4]);
		boolean fromDanish = fromdan.isChecked();
		boolean toDanish = todan.isChecked();
		boolean eks_flag = eks.isChecked();
		boolean rev_flag = rev.isChecked();
		String lang_longname = dictionaries.get(dictnr.getSelectedItemPosition())[1];
		
		ArrayList<String> opsamling = new ArrayList<String>();
		opsamling.add("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' " +
				"'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns=" +
				"'http://www.w3.org/1999/xhtml' xml:lang='da' lang='da'>" +
				"<head><title>Opslag</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head>" +
				"<body><small><small>");
		if (results!=null) {
			if (doub==0 || doub==2) rev_flag=false;
			if (fromDanish) {
				if (results[0][0].compareTo("")!=0) {
					if (doub>1) {
						opsamling.add("<h1>Dansk - " + lang_longname + "<br/><br/></h1>");
					} else {
						opsamling.add("<h1>" + lang_longname + "<br/><br/></h1>");
					}
					opsamling.add(results[0][0]);
					opsamling.add("<br/>");
				}
				if(rev_flag && results[0][1].compareTo("")!=0) {
					opsamling.add("<h2>Forekomster i 'omvendt søgning'<br/></h2>");
					opsamling.add(results[0][1]);
					opsamling.add("<br/>");
				}
				if(eks_flag && results[0][2].compareTo("")!=0) {
					opsamling.add("<h2>Eksempelsætninger<br/></h2>");
					opsamling.add(results[0][2]);
					opsamling.add("<br/><br/>");
				}
			}
			if (toDanish && doub>1) {
				if (results[1][0].compareTo("")!=0) {
					opsamling.add("<h1>" + lang_longname + "-Dansk<br/><br/></h1>");
					opsamling.add(results[1][0]);
					opsamling.add("<br/>");
				}
				if(rev_flag && results[1][1].compareTo("")!=0) {
					opsamling.add("<h2>Forekomster i 'omvendt søgning'<br/></h2>");
					opsamling.add(results[1][1]);
					opsamling.add("<br/>");
				}
				if(eks_flag && results[1][2].compareTo("")!=0) {
					opsamling.add("<h2>Eksempelsætninger<br/></h2>");
					opsamling.add(results[1][2]);
				}
			}
		}
		opsamling.add("</small></small></body></html>");
		StringBuffer t_ = new StringBuffer();
		t_.setLength(0);
		for (String t:opsamling) {
			t_.append(t);
		}
		
		show_search_result.loadDataWithBaseURL("fake-url", "<html></html>", "text/html", "UTF-8", null);
		show_search_result.loadData(t_.toString(), "text/html; charset=utf-8", "UTF-8");
	}

	
	
	@Override
	protected void onStop() {
		super.onStop();
		//Gem settings
		SharedPreferences settings = getSharedPreferences("AndGro_settings", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("dictnr_settings", dictnr.getSelectedItemPosition());
		editor.putBoolean("todan_settings", todan.isChecked());
		editor.putBoolean("fromdan_settings", fromdan.isChecked());
		editor.putBoolean("eks_settings", eks.isChecked());
		editor.putBoolean("rev_settings", rev.isChecked());
		editor.putString("dict_dir_settings", dict_dir);
		editor.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Danne settings-menuen
		this.menu = menu;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mymenu, menu);
		menu.findItem(R.id.stay_on_setting).setChecked(stay_on_flag);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId()== R.id.find_dict) {
			//Finde mappe med ordbogsfiler
			Intent intent = new Intent(this, BrowseFiles.class);
			intent.putExtra(EXTRA_MESSAGE, dict_dir);
			startActivityForResult(intent, CREATE_REQUEST_CODE);
		}
		if (item.getItemId()== R.id.stay_on_setting) {
			//Forhindre at apparatet går i standby
			item.setChecked(!item.isChecked());
			if (item.isChecked()) {
				new AlertDialog.Builder(this)
				 .setIcon(R.drawable.ic_launcher)
				 .setTitle("Kan være hård ved batteriet!")
				 .setPositiveButton("OK", 
						 new DialogInterface.OnClickListener() {
					 @Override
					 
					 public void onClick(DialogInterface dialog, int which) {
						 // Auto-generated method stub
					 }
					 }).show();
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			} else {
				getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Her returneres biblioteket for ordbøgerne, hvis brugeren har været i "settings"-aktiviteten
		if (requestCode == CREATE_REQUEST_CODE) {
		      if (resultCode == RESULT_OK) {
		    	dict_dir = data.getStringExtra("RESULT_STRING");
		        dictionaries = Get_books.parse_dictionaries(this, dict_dir);
		        fromdan.setChecked(true);
		        todan.setChecked(true);
		        eks.setChecked(true);
		        rev.setChecked(true);
		        dictnr.setSelection(0);
		        createSpinner(dictionaries, dictnr.getSelectedItemPosition());
		        set_views(dictnr.getSelectedItemPosition());
		      }
		   }
		}

	@Override
	public void onClick(View v) {
		//Hvis søgeknappen trykkes
		if (v.getId() == R.id.src_button) {
			if (!dictionaries.isEmpty()) {
	        	  get_results();
	        }
		}
		show_results();
	}
}
