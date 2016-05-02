package ch.hsr.edu.sinv_56082.gastroginiapp.domain;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DomainRelationsTest {

    private TestDataSetup testData;

    @Before
    public void setUp(){
        testData = new TestDataSetup();
    }

    @Test
    public void testProductCategory(){
        assertEquals(testData.cat.productDescriptions().size(), 1);
    }

    @Test
    public void testProductDesctiption(){
        assertEquals(testData.pDesc.productCategory, testData.cat);
        assertEquals(testData.pDesc.products().size(), 4);
    }

    @Test
    public void testProduct(){
        assertEquals(testData.prod.productDescription, testData.pDesc);
        assertEquals(testData.prod.productList,testData.pList);

        assertEquals(testData.prod.orderPositions().size(), 1);
    }

    @Test
    public void testProductList(){
        assertEquals(testData.pList.products().size(), 4);

        assertEquals(testData.pList.events().size(), 1);
    }

    @Test
    public void testEvent(){
        assertEquals(testData.event.productList, testData.pList);
        assertEquals(testData.event.host, testData.pers);

        assertEquals(testData.event.workAssignments().size(), 1);
        assertEquals(testData.event.eventTables().size(), 5);
    }

    @Test
    public void testPerson(){
        assertEquals(testData.pers.eventsHosted().size(),1);
        assertEquals(testData.pers.workAssignments().size(),1);
        assertEquals(testData.pers.ordersCreated().size(), 1);
    }

    @Test
    public void testWorkAssignments(){
        assertEquals(testData.work.event, testData.event);
        assertEquals(testData.work.person, testData.pers);
    }

    @Test
    public void testTables(){
        assertEquals(testData.table.event, testData.event);
        assertEquals(testData.table.orders().size(), 1);
    }

    @Test
    public void testOrders(){
        assertEquals(testData.order.eventTable, testData.table);

        assertEquals(testData.order.createdBy, testData.pers);
        assertEquals(testData.order.orderPositions().size(), 1);
    }

    @Test
    public void testOrderPositions(){
        assertEquals(testData.orderPos.eventOrder, testData.order);
        assertEquals(testData.orderPos.orderState, testData.state);
        assertEquals(testData.orderPos.product, testData.prod);
    }
}
