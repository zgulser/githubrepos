package com.payconiq.zekigu.githubrepos.core.service;

import com.payconiq.zekigu.githubrepos.core.utils.HttpConstants;

/**
 * Created by zekigu on 15.09.2017.
 */
public interface HttpRequestContract {
    void request(HttpConstants.HttpRequestTypes pType);
}
