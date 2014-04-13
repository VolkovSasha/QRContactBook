package com.qrcontactbook;

import java.util.ArrayList;
import java.util.List;

import com.qrcontactbook.R.menu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends ActionBarActivity  {
    
    private int mState = 0;
    ViewPager viewPager = null;
    
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.contact_base_tel_page);
	                
	        LayoutInflater inflater = LayoutInflater.from(this);
	        List<View> pages = new ArrayList<View>();
	      
	        
	        View page = inflater.inflate(R.layout.contact_base_tel_page, null);
	        final String[] base = new String[] {
	        	    "Tro", "Trolo", "Trololo",		    
	        	  };
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,	
	        		android.R.layout.simple_list_item_1, base);
	        ListView lv = (ListView) page.findViewById(R.id.listViewPageBaseTel);
	   
	        lv.setAdapter(adapter);
	        pages.add(lv);
	        
	        
	        View  page1 = inflater.inflate(R.layout.contact_base_page, null);
	        final String[] baseOne = new String[] {
	        	    "Tro", "Trolo", "Trololo", "Trol1", "Bla",
	        	    "Blabla", "trwe", "qer", "qwre", "Qwer",
	        	    "Qwerty", "Mla", "Trtata"		    
	        	  };
	        ArrayAdapter<String> adapterOne = new ArrayAdapter<String>(this,
	        		android.R.layout.simple_list_item_1, baseOne);
	        ListView lvOne = (ListView) page1.findViewById(R.id.listViewPageBase);
	   
	        lvOne.setAdapter(adapterOne);
	        pages.add(lvOne);
	       
	      

	        
	        MyPagerAdapter pagerAdapter = new MyPagerAdapter(pages);
	        viewPager = new ViewPager(this);
	        viewPager.setAdapter(pagerAdapter);
	        viewPager.setCurrentItem(0);    
	        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPageSelected(int arg0) {
					menuChange();
				}
	        	
	        });
	        
	        setContentView(viewPager);
	    }
	 
	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void menuChange() {
		 mState = viewPager.getCurrentItem();
		 this.supportInvalidateOptionsMenu();
	 }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	if(mState == 0)
    		getMenuInflater().inflate(R.menu.menu_one, menu);
    	else
    		getMenuInflater().inflate(R.menu.menu_two, menu);
        return true;
    }
    

}
