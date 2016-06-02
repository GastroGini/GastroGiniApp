package ch.hsr.edu.sinv_56082.gastroginiapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "src/main/AndroidManifest.xml", packageName = "ch.hsr.edu.sinv_56082.gastroginiapp")
public class ExampleUnitTest {
    @Test
    public void test() throws Exception {
        assertEquals(1, 1);
    }
}