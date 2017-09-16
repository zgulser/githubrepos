package com.payconiq.zekigu.githubrepos.ui.errorhandler;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by zekigu on 15.09.2017.
 *
 * Desc: An abstraction/contract to handle error messages with 3 different methods
 */
public interface IUIErrorHandler {
    void showDialog(String pTitle, String pMessage, ArrayList<Button> pButtonList,
                    boolean pButtonsHorizontal, boolean pCustonView);
    void showToast(String pMessage, boolean pLong, boolean pCustonView);
    void showSnackBar(View pParent, String pMessage, boolean pLong, boolean pCustomView);
}
