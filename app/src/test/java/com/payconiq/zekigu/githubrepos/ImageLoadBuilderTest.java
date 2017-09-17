package com.payconiq.zekigu.githubrepos;

import android.net.Uri;
import android.widget.ImageView;

import com.payconiq.zekigu.githubrepos.core.imageloader.ImageLoad;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;

import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Backbase R&D B.V on 17/09/2017.
 */

public class ImageLoadBuilderTest {

    @Test
    public void testImageloadBuilder() throws URISyntaxException {
        ImageLoad imageLoad = new ImageLoad.ImageLoadBuilder(Uri.parse(""), null).build();
        assertTrue("@testImageloadBuilder - Unable to build image load obj", imageLoad != null);
    }
}
