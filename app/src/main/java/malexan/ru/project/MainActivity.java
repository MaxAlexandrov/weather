package malexan.ru.project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import fragments.MapGoogle;
import google_for_tab.SlidingTabLayout;
import maxalexan.ru.project.R;

public class MainActivity extends ActionBarActivity  {

    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    MapGoogle google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] titles = {getResources().getString(R.string.city_search),getResources().getString(R.string.list_city),getResources().getString(R.string.maps_city)};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),titles,titles.length);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
         tabs.setViewPager(pager);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_tabs, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
           // Toast.makeText(getApplication(),"Данная опция находится в стадии разработка",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}