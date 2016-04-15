package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class TestActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
