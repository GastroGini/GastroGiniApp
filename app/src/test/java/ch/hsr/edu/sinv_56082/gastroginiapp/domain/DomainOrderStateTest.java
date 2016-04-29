package ch.hsr.edu.sinv_56082.gastroginiapp.domain;


import com.activeandroid.query.Select;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainOrderStateTest {

    OrderState test1, test2;

    @Before
    public void setUp(){
        test1 = new OrderState("Test1", "swfadsf");
        test2 = new OrderState("Test2", "wegfw");
        test1.save();
        test2.save();
    }

    @Test
    public void testQueryWithSelectByName(){
        OrderState query = new Select().from(OrderState.class).where("name=?", test1.name).executeSingle();
        assertEquals(query, test1);
    }

    @Test
    public void testCreate() {
        OrderState orderState = new OrderState("TGWAE", "wg");
        orderState.save();

        OrderState query = OrderState.load(OrderState.class, orderState.getId());
        assertEquals(orderState, query);
    }


}
