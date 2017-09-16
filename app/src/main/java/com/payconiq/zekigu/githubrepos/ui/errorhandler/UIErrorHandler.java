package com.payconiq.zekigu.githubrepos.ui.errorhandler;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by zekigu on 15.09.2017
 */
public class UIErrorHandler implements IUIErrorHandler {

    @Override
    public void showDialog(String pTitle, String pMessage, ArrayList<Button> pButtonList, boolean pButtonsHorizontal, boolean pCustonView) {
        // needs to implemented
    }

    @Override
    public void showToast(String pMessage, boolean pLong, boolean pCustonView) {
        // needs to implemented
    }

    @Override
    public void showSnackBar(View pParent, String pMessage, boolean pLong, boolean pCustomView) {
        final Snackbar sb = Snackbar.make(pParent, pMessage, Snackbar.LENGTH_INDEFINITE);
        sb.setAction("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.dismiss();
            }
        }).show();
    }
}
