package com.payconiq.zekigu.githubrepos.ui.specialcase;

import static android.support.v7.widget.SearchView.*;

/**
 * Created by zekigu on 15.09.2017
 */

public class CustomQueryTextListener implements OnQueryTextListener {
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
