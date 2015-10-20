package com.ejvindh.webmarks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

class linksEntry {
	public String displayname;
	public String link;
	public int visible;
}

public class HandleXmlList {
	private static String currentTag = "-";
	private static String internalFile = "LinkList.xml";
	private static ArrayList<linksEntry> linksEntries;
	
	public static ArrayList<linksEntry> getLinks(Context context) {
		linksEntries = new ArrayList<linksEntry>();
		
		int succes = 0;
		File internalXml = new File(context.getFilesDir(), internalFile);
		if (!internalXml.exists()) {
			//If there is no internal list already, check for xml in raw folder
			succes = getXmlFromFile(context.getResources().openRawResource(R.raw.links));
			if (succes == 0) {
				@SuppressWarnings("unused")
				int result = writeList(context, linksEntries);
			}
		} else {
			//Get the latest xml-list of links
			try {
				succes = getXmlFromFile(context.openFileInput(internalFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				succes = 1;
			}
		}
		if (succes >0) {
			Toast.makeText(context, "Error retrieving linklist", Toast.LENGTH_SHORT).show();
		}
		return linksEntries;
	}

	private static int getXmlFromFile(InputStream inputStream) {
		int succes=0;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			InputStream stream = inputStream;
			xpp.setInput(stream, null);
			int eventType = xpp.getEventType();
			String currentName = "";
			String currentLink = "";
			int currentVisible = 0;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				linksEntry currentEntry = new linksEntry();
				if (eventType == XmlPullParser.START_TAG) {
					String tmpName = xpp.getName();
					if (!tmpName.equals("entry") && !tmpName.equals("entries")) {
						currentTag = tmpName;
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					currentTag = "-";
					if (xpp.getName().equals("entry") && !currentLink.isEmpty()) {
						if (currentName.isEmpty()) {
							currentName = "NoName";
						}
						currentEntry.link = currentLink;
						currentEntry.displayname = currentName;
						currentEntry.visible = currentVisible;
						linksEntries.add(currentEntry);
						currentName = "";
						currentLink = "";
						currentVisible = 0;
					}
				} else if (eventType == XmlPullParser.TEXT) {
					String tmpText = xpp.getText();
					if (currentTag.equals("link")) {
						currentLink = tmpText;
					} else if (currentTag.equals("displayname")) {
						currentName = tmpText;
					} else if (currentTag.equals("visible")) {
						currentVisible = Integer.parseInt(tmpText);
					}
				}
				eventType = xpp.next();
			}
		} catch (NotFoundException e) {
			Log.d("parse_links", e.getMessage());
			succes = 1;
		} catch (XmlPullParserException e) {
			Log.d("parse_links", e.getMessage());
			succes = 2;
		} catch (IOException e) {
			Log.d("parse_links", e.getMessage());
			succes = 3;
		}
		return succes;
	}

	public static int writeList(Context context, ArrayList<linksEntry> changedList) {
		//http://stackoverflow.com/questions/11687074/create-xml-file-and-save-it-in-internal-storage-android
		//TODO De to hentninger er næsten identisk i kode, bør lægges ud i egen funktion
		int result = 0;
		FileOutputStream fos;       
	    try {
	    	fos = context.openFileOutput(internalFile, Context.MODE_PRIVATE);
		    XmlSerializer serializer = Xml.newSerializer();
		    serializer.setOutput(fos, "UTF-8");
		    serializer.startDocument(null, Boolean.valueOf(true));
		    serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
		    serializer.startTag(null, "entries");
		    for (int i = 0; i < changedList.size(); i++) {
		    	serializer.startTag(null, "entry");
			    	serializer.startTag(null, "displayname");
			    	serializer.text(changedList.get(i).displayname);
			    	serializer.endTag(null, "displayname");
			    	serializer.startTag(null, "link");
			    	serializer.text(changedList.get(i).link);
			    	serializer.endTag(null, "link");
			    	serializer.startTag(null, "visible");
			    	serializer.text(String.valueOf(changedList.get(i).visible));
			    	serializer.endTag(null, "visible");
		    	serializer.endTag(null, "entry");
		    }
	    	serializer.endTag(null, "entries");
	    	serializer.endDocument();
			serializer.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			result = 1;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result = 2;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			result = 3;
		} catch (IOException e) {
			e.printStackTrace();
			result = 4;
		}
		return result;
	}
}
