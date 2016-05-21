package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;


import android.app.Activity;
import android.widget.Button;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.Robolectric;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MenuMainTest {
    Button menuCardButton;
    Activity activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(MenuMain.class);
        menuCardButton = (Button) activity.findViewById(R.id.menuCardButton);

    }

    @Test
    public void goToMenuCardTest() {

        menuCardButton.performClick();

        ShadowApplication application = shadowOf(RuntimeEnvironment.application);
        assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
    }
}
