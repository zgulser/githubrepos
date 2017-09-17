package com.payconiq.zekigu.githubrepos.core.utils;

/**
 * Created by zekigu on 15.09.2017.
 */
public class HttpConstants {
    public static String RETRIEVE_REPO_REQUEST_URL = "https://api.github.com/users/JakeWharton/repos?page=%d&per_page=%d";
    public static final int HTTP_REQUEST_TIMEOUT = 30 * 1000; // in seconds
    public enum HttpRequestTypes  { RETRIEVE_REPOS }
}
