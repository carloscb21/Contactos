package com.carloscb21.contactos;


//prueba de carlos

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.carloscb21.contactos.util.DatabaseHelper;
import com.carloscb21.contactos.util.TabsPagerAdapter;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
// ARRAY ADAPTER su funcion es integrar es nuestro ArrayList que habiamos creado para poder representar dentro del layout que habiamos creado previamente, el tab2

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> implements ActionBar.TabListener, ViewPager.OnPageChangeListener{

    //control de fichas(tabs)
    private ViewPager viewPager;
    private TabsPagerAdapter adapter;
    private ActionBar actionBar;
    //titulo de las fichas
    private String[] titulos = {"Crear Contacto", "Lista_ Contactos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarTabs();
    }

    private void inicializarTabs() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        adapter = new TabsPagerAdapter(getFragmentManager());

        viewPager.setAdapter(adapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //agregando fichas(tabs)
        for (String nombre:titulos){
            ActionBar.Tab tab = actionBar.newTab().setText(nombre);
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
