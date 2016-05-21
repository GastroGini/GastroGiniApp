package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities;


import android.widget.Button;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class HomeScreenActivityTest {

    @Test
    public void testSetup(){
        HomeScreenActivity activity = Robolectric.setupActivity(HomeScreenActivity.class);


        Button eventButton = (Button) activity.findViewById(R.id.eventButton);
        Button menuButton = (Button) activity.findViewById(R.id.menuButton);

        assertEquals(eventButton.getText().toString(), activity.getResources().getString(R.string.events));
        assertEquals(menuButton.getText().toString(), activity.getResources().getString(R.string.menu));

    }

}
