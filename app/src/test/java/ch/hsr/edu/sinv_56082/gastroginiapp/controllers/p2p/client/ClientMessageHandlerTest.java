package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.BuildConfig;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.TestDataSetup;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.ConnectionMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.MessageAction;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.initial_data.InitialDataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.serialization.Serializer;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ClientMessageHandlerTest {

    private ConnectionMessage message;
    private TestDataSetup testData;
    private String uuid;
    private P2pClient p2pClient;

    @Before
    public void setUp(){
        testData = new TestDataSetup();
        uuid = testData.event.getUuid().toString();
        List<Product> products = testData.event.productList.products();
        List<EventTable> tables1 = testData.event.eventTables();
        InitialDataMessage msg = new InitialDataMessage(testData.event, products, tables1);
        message = new ConnectionMessage("unwichtig", Serializer.get().toJson(new DataMessage(MessageAction.INITIAL_DATA, msg)));

        ConnectionController controller = ConnectionController.getInstance();
        p2pClient = new P2pClient(new P2pHandler(App.getApp(), new DoNothing()), App.getApp(),controller);

    }

    @Test
    public void testInitMessage(){

        p2pClient.setInitialized(false);
        p2pClient.handleMessages(message);

        assertNotNull(new ViewController<>(Event.class).get(uuid));
    }

}
