package com.ejvindh.webmarks;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class AddLinks extends Activity {
	public ArrayList<linksEntry> linkEntries;
	private EditText addedTitle;
	private EditText addedLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlinks);
        addedTitle = (EditText)findViewById(R.id.editTextTitle);
        addedLink = (EditText)findViewById(R.id.editTextLink);
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	String title = addedTitle.getText().toString();
    	String link = addedLink.getText().toString();
    	linkEntries = HandleXmlList.getLinks(this);
    	int result = 0;
    	if (link != null && !link.isEmpty() && !link.equals("")) {
    		if (!link.matches("(http://|https://).*")) {
    			link = "http://" + link;
    		}
    		linksEntry newEntry = new linksEntry();
			newEntry.link = link;
			newEntry.displayname = title;
			newEntry.visible = 0;
			linkEntries.add(newEntry);
	    	result = HandleXmlList.writeList(this, linkEntries);
    	}
    	Intent intent=new Intent();
    	if (result > 0) {
    		Toast.makeText(getApplicationContext(), "Error: List not saved", Toast.LENGTH_SHORT).show();
    		setResult(RESULT_CANCELED);
    	} else {
        	setResult(RESULT_OK, intent);
    	}
    	finish();
    }
}
