package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageAction;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.order_positions.OrderPositionsHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.serialization.Serializer;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ServerMessageHandlerTest {

    private TestDataSetup testData;
    private String uuid;
    private ConnectionMessage messagePaid;
    private P2pServer p2pServer;

    @Before
    public void setUp(){
        testData = new TestDataSetup();
        uuid = testData.orderPos.getUuid().toString();

        testData.orderPos.orderState = OrderState.STATE_PAYED;
        testData.orderPos.save();

        OrderPositionsHolder holder = new OrderPositionsHolder(testData.order.orderPositions());

        messagePaid = new ConnectionMessage("unwichtig", Serializer.get().toJson(new DataMessage(MessageAction.SET_ORDER_POSITIONS_PAYED, holder)));

        p2pServer = new P2pServer(testData.event, new P2pHandler(App.getApp(), new DoNothing()));


        testData.orderPos.orderState = OrderState.STATE_OPEN;
        testData.orderPos.save();
    }

    @Test
    public void testOrdersPaid(){



        p2pServer.handleMessages(messagePaid);

        assertEquals(new ViewController<>(OrderPosition.class).get(uuid).orderState, OrderState.STATE_PAYED);

    }

}
