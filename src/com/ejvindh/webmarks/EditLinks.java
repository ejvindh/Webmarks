package com.ejvindh.webmarks;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditLinks extends Activity {
	public ArrayList<linksEntry> linkEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlinks);
	    LinearLayout myLayout = (LinearLayout) findViewById(R.id.linearLayout);

		linkEntries = HandleXmlList.getLinks(this);
		buildView(myLayout);
    }

    private void buildView(final LinearLayout myLayout) {
    	myLayout.removeAllViews();
		for (int i = 0; i < linkEntries.size(); i++) {
			LinearLayout linearLocal = new LinearLayout(this);
			linearLocal.setOrientation(LinearLayout.VERTICAL);
			linearLocal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
				LinearLayout linearLocalHorisontal = new LinearLayout(this);
				linearLocalHorisontal.setOrientation(LinearLayout.HORIZONTAL);
				linearLocalHorisontal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					final Button button = new Button(this);
					button.setId(i);
					button.setTextSize(12);
					button.setMinHeight(0);
					button.setMinWidth(0);
					setButtonLayout(button, linkEntries.get(i).visible);
					button.setOnClickListener(new OnClickListener() {

					    @Override
					    public void onClick(View arg0) {
					        int id = arg0.getId();
					        linksEntry tmpLinksEntry = new linksEntry();
					        int tmpID = linkEntries.get(id).visible;
					        tmpID++;
					        if (tmpID>2) tmpID=0;
					        tmpLinksEntry.visible = tmpID;
					        linkEntries.set(id, tmpLinksEntry);
					        setButtonLayout(button, linkEntries.get(id).visible);
					    }
					});
					LinearLayout.LayoutParams lparams1 = 
			        		new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2);
					linearLocalHorisontal.addView(button, lparams1);
			
					TextView titleView = new TextView(this);
					titleView.setId(i);
			        titleView.setTextAppearance(this, android.R.attr.textAppearanceMedium);
			        titleView.setText("Title:");
					LinearLayout.LayoutParams lparams2 = 
			        		new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
			        linearLocalHorisontal.addView(titleView, lparams2);
			        
			        EditText linkTitle = new EditText(this);
			        linkTitle.setId(i+linkEntries.size());
			        linkTitle.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			        linkTitle.setMaxLines(1);
			        linkTitle.setText(linkEntries.get(i).displayname);
					LinearLayout.LayoutParams lparams3 = 
			        		new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 24);
			        linearLocalHorisontal.addView(linkTitle, lparams3);
			        
			        final ImageButton buttonUp = new ImageButton(this);
					buttonUp.setId(i + linkEntries.size()*3);
					buttonUp.setImageResource(R.drawable.up2);
					buttonUp.setScaleType(ImageButton.ScaleType.FIT_XY);
					buttonUp.setPadding(0, 20, 5, 0);
					buttonUp.setBackgroundResource(0);
					if (i==0) {
						buttonUp.setVisibility(View.INVISIBLE);
					}
					buttonUp.setOnClickListener(new OnClickListener() {
					    @Override
					    public void onClick(View arg0) {
					        int id = arg0.getId() - linkEntries.size()*3;
					        linksEntry clickedEntry = new linksEntry();
					        linksEntry prevEntry = new linksEntry();
					        clickedEntry.visible = linkEntries.get(id).visible;
				        	clickedEntry.link = linkEntries.get(id).link;
				        	clickedEntry.displayname = linkEntries.get(id).displayname;
				        	prevEntry.visible = linkEntries.get(id-1).visible;
				        	prevEntry.link = linkEntries.get(id-1).link;
				        	prevEntry.displayname = linkEntries.get(id-1).displayname;
				        	linkEntries.set(id, prevEntry);
				        	linkEntries.set(id-1, clickedEntry);
					        buildView(myLayout);
					    }
					});
					LinearLayout.LayoutParams lparams5 = 
			        		new LinearLayout.LayoutParams(60, 60, 1);
					linearLocalHorisontal.addView(buttonUp, lparams5);
			        
		        linearLocal.addView(linearLocalHorisontal);
		        
				LinearLayout linearLocalHorisontal2 = new LinearLayout(this);
				linearLocalHorisontal2.setOrientation(LinearLayout.HORIZONTAL);
				linearLocalHorisontal2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		        
			        EditText linkAddress = new EditText(this);
			        linkAddress.setId(i + linkEntries.size()*2);
			        linkAddress.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
			        linkAddress.setMaxLines(1);
			        linkAddress.setText(linkEntries.get(i).link);
			        LinearLayout.LayoutParams lparams4 = 
			        		new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 27);
			        linearLocalHorisontal2.addView(linkAddress, lparams4);
			        
			        final ImageButton buttonDown = new ImageButton(this);
					buttonDown.setId(i + linkEntries.size()*4);
					buttonDown.setImageResource(R.drawable.down2);
					buttonDown.setScaleType(ImageButton.ScaleType.FIT_XY);
					buttonDown.setPadding(0, 0, 5, 20);
					buttonDown.setBackgroundResource(0);
					if (i==linkEntries.size()-1) {
						buttonDown.setVisibility(View.INVISIBLE);
					}
					buttonDown.setOnClickListener(new OnClickListener() {
					    @Override
					    public void onClick(View arg0) {
					        int id = arg0.getId() - linkEntries.size()*4;
					        linksEntry clickedEntry = new linksEntry();
					        linksEntry nextEntry = new linksEntry();
					        clickedEntry.visible = linkEntries.get(id).visible;
				        	clickedEntry.link = linkEntries.get(id).link;
				        	clickedEntry.displayname = linkEntries.get(id).displayname;
				        	nextEntry.visible = linkEntries.get(id+1).visible;
				        	nextEntry.link = linkEntries.get(id+1).link;
				        	nextEntry.displayname = linkEntries.get(id+1).displayname;
				        	linkEntries.set(id, nextEntry);
				        	linkEntries.set(id+1, clickedEntry);
					        buildView(myLayout);
					    }
					});
			        LinearLayout.LayoutParams lparams6 = 
			        		new LinearLayout.LayoutParams(60, 60, 1);
			        linearLocalHorisontal2.addView(buttonDown, lparams6);
					
				linearLocal.addView(linearLocalHorisontal2);
		        
	        myLayout.addView(linearLocal);
		}
		
	}

	private void setButtonLayout(Button button, int visible) {
		String buttonTxt = "";
		String buttonColour = "";
		if (visible == 0) {
			buttonTxt = "Visible";
			buttonColour = "#000000";
		} else if (visible ==1) {
			buttonTxt = "Hidden";
			buttonColour = "#808080";
		} else if (visible == 2) {
			buttonTxt = "Delete";
			buttonColour = "#FF0000";
		}
		button.setText(buttonTxt);
		button.setTextColor(Color.parseColor(buttonColour));
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	ArrayList<linksEntry> linkEntriesResult = getResult();
    	int result = HandleXmlList.writeList(this, linkEntriesResult);
    	Intent intent=new Intent();
    	if (result > 0) {
    		Toast.makeText(getApplicationContext(), "Error: List not saved", Toast.LENGTH_SHORT).show();
    		setResult(RESULT_CANCELED);
    	} else {
        	setResult(RESULT_OK, intent);
    	}
    	finish();
    }

	private ArrayList<linksEntry> getResult() {
		ArrayList<linksEntry> linkEntriesResult = new ArrayList<linksEntry>();
		for (int i = 0; i < linkEntries.size(); i++) {
			EditText etName = (EditText) findViewById(i+linkEntries.size());
	        EditText etLink = (EditText) findViewById(i+linkEntries.size()*2);
	        String sName = etName.getText().toString();
	        String sLink = etLink.getText().toString();
			if (linkEntries.get(i).visible < 2 && sLink.length() > 1) {
				linksEntry tmpResult = new linksEntry();
	    		if (!sLink.matches("(http://|https://).*")) {
	    			sLink = "http://" + sLink;
	    		}
				tmpResult.link = sLink;
				tmpResult.displayname = sName;
				tmpResult.visible = linkEntries.get(i).visible;
				linkEntriesResult.add(tmpResult);
			}
		}
		return linkEntriesResult;
	}
}
