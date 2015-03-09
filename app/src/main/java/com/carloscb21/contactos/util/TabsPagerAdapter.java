package com.carloscb21.contactos.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.carloscb21.contactos.CrearContactosFragment;
import com.carloscb21.contactos.ListaContactosFragment;

/**
 * Created by Administrador on 09/03/2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{
    public TabsPagerAdapter(FragmentManager fm){

        super (fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new CrearContactosFragment();
            case 1: return new ListaContactosFragment();
        }
        return null;
    }
}
