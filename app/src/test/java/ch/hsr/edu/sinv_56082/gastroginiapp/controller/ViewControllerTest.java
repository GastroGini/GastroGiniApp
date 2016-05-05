package ch.hsr.edu.sinv_56082.gastroginiapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;

import static org.junit.Assert.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ViewControllerTest {

    @Test
    public void test(){
        assertTrue(true);
    }

}
