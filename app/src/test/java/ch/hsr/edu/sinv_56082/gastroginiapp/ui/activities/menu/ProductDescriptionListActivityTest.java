package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.ProductCategoryViewHolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ProductDescriptionListActivityTest {

    private TestDataSetup testData;

    @Before
    public void setUp(){

        testData = new TestDataSetup();
    }

    @Test
    public void testSetup(){
        ProductDescriptionListActivity activity = Robolectric.setupActivity(ProductDescriptionListActivity.class);
        activity.adapter.notifyDataSetChanged();
        assertEquals(activity.s_recycler.getAdapter().getItemCount(), 1);
    }


    @Test
    public void testAdapter(){
        ProductDescriptionListActivity activity = Robolectric.setupActivity(ProductDescriptionListActivity.class);


        RecyclerView rvParent = new RecyclerView(RuntimeEnvironment.application);
        rvParent.setLayoutManager(new LinearLayoutManager(RuntimeEnvironment.application));

        // Run test
        ProductCategoryViewHolder viewHolder = activity.adapter.onCreateViewHolder(rvParent, 0);

        activity.adapter.onBindViewHolder(viewHolder, 0);

        viewHolder.editIcon.performClick();

        assertEquals(viewHolder.menuTitle.getText().toString(), testData.cat.toString());
    }

}
