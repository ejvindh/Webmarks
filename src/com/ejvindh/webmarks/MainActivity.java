package com.ejvindh.webmarks;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {
    public String displayName;
	public String openLink;
	public ArrayList<linksEntry> linkEntries;
	public LinearLayout myLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    myLayout = (LinearLayout) findViewById(R.id.linearLayout);
		linkEntries = HandleXmlList.getLinks(this);
		buildView(myLayout, linkEntries);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_editList) {
			//Edit list of links
			Intent intentEdit = new Intent(this, EditLinks.class);
			startActivityForResult(intentEdit, 1);
		} else if (id == R.id.action_addEntry) {
			Intent intentAdd = new Intent(this, AddLinks.class);
			startActivityForResult(intentAdd, 2);
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 || requestCode == 2) {
        	// requestCode 1 == EditLinks.java
        	// requestCode 2 == AddLinks.java
			linkEntries = HandleXmlList.getLinks(this);
			buildView(myLayout, linkEntries);
		}
	}

	private void buildView(LinearLayout myLayout, ArrayList<linksEntry> linkEntries2) {
		myLayout.removeAllViews();
		for (int i = 0; i < linkEntries.size(); i++) {
		    if (linkEntries.get(i).visible == 0) {
				Button button = new Button(this);
				button.setId(i);
				button.setText(linkEntries.get(i).displayname);
				myLayout.addView(button);
				button.setOnClickListener(this);
		    }
		}
	}
	
	public void onClick(View v) {
		// Hvordan opfører knapperne sig
		String url = linkEntries.get(v.getId()).link;
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}
}
