package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization.ModelHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.Actions;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.initial_data.InitialDataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.new_event_order.NewEventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.order_positions.OrderPositionsHolder;

public class P2pServer {

    private static final String TAG = "SERVER::";
    private final App app;
    private ServerSocketHandler serverService;
    private Event runningEvent;
    private Handler handler;

    public WifiP2pDnsSdServiceInfo wifiP2pService;



    private Map<String, ConnectedDevice> connectedDevices = new HashMap<>();

    public P2pServer(Event event){
        TransferEvent dto = new TransferEvent(event.getUuid(), event.name, event.startTime);
        app = App.getApp();
        runningEvent = event;
        messageHandlers = new HashMap<>();

        initGroup();
        removeLocalService();
        registerMessageHandlers();
        registerMessageHandler();
        try {
            serverService = new ServerSocketHandler(handler, app.p2p.macAddress);
            serverService.start();
        } catch (IOException e) {
            Log.e(TAG, "P2pServer: failed to create server socket", e);
        }
        setLocalService(dto);

    }


    public void initGroup() {
        if(!app.p2p.isWifiP2pEnabled()) return;
        app.p2p.wifiP2pManager.cancelConnect(app.p2p.wifiP2pChannel, null);

        app.p2p.wifiP2pManager.createGroup(app.p2p.wifiP2pChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: Created Group");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failed to create group");
            }
        });
    }

    private void registerMessageHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: recieved"+msg+P2pHandler.SET_MESSAGE_HANDLER+P2pHandler.RECIEVED_MESSAGE);
                if(P2pHandler.SET_MESSAGE_HANDLER == msg.what){
                    ConnectedDevice clientDevice = (ConnectedDevice) msg.obj;

                    connectedDevices.put(clientDevice.device, clientDevice);
                    //clientDevice.handler.write("Server hello");
                    return true;
                }else if (P2pHandler.RECIEVED_MESSAGE == msg.what){
                    ConnectionMessage connectionMessage = (ConnectionMessage) msg.obj;

                    Log.d(TAG, "handleMessage: " + connectionMessage.content);
                    handleServerMessages(connectionMessage);

                    return true;
                }else if (P2pHandler.DISCONNECTED == msg.what){
                    connectedDevices.get(((ConnectionMessage) msg.obj).fromAddress).connectionState = ConnectionController.ConnectionState.RECONNECTING;
                }
                return false;
            }
        });
    }


    public void removeLocalService(){
        if (app.p2p.isWifiP2pEnabled())
            app.p2p.wifiP2pManager.clearLocalServices(app.p2p.wifiP2pChannel, null);
    }

    public void setLocalService(final TransferEvent eventName) {
        if (app.p2p.isWifiP2pEnabled()) {
            Map<String, String> record = new HashMap<>();
            record.put(P2pHandler.TXTRECORD_PROP_AVAILABLE, "visible");
            //record.put(app.p2p.EVENT_INFO+"name", eventName.name);
            //record.put(app.p2p.EVENT_INFO+"uuid", eventName.uuid.toString());
            //record.put(app.p2p.EVENT_INFO+"date", eventName.startDate.toString());

            app.p2p.wifiP2pManager.clearLocalServices(app.p2p.wifiP2pChannel, null);

            wifiP2pService = WifiP2pDnsSdServiceInfo.newInstance(P2pHandler.SERVICE_INSTANCE + eventName.name, P2pHandler.SERVICE_REG_TYPE, record);
            app.p2p.wifiP2pManager.addLocalService(app.p2p.wifiP2pChannel, wifiP2pService, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: added Local Service: " + P2pHandler.SERVICE_INSTANCE + eventName);
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "onFailure: failed to add Service" + P2pHandler.SERVICE_INSTANCE + eventName);
                }
            });
        }
    }


    private Map<String, MessageObject> messageHandlers;


    private void handleServerMessages(ConnectionMessage message) { //TODO duplicated code
        DataMessage dataMessage = new Gson().fromJson(message.content, DataMessage.class);
        MessageObject messager = messageHandlers.get(dataMessage.action);
        if (messager != null) messager.handleMessage(new Gson().fromJson(message.content, messager.type), message.fromAddress);
    }

    private void sendMessage(String to, DataMessage message){
        connectedDevices.get(to).handler.write(new Gson().toJson(message));
    }

    private void registerMessageHandlers() {
        messageHandlers.put(Actions.GET_INITIAL_DATA, new MessageObject<Object>(Object.class){
            @Override
            public void handleMessage(Object object, String from) {
                List<Product> products = runningEvent.productList.products();
                List<EventTable> tables = runningEvent.eventTables();
                InitialDataMessage msg = new InitialDataMessage(runningEvent, products, tables);
                sendMessage(from, new DataMessage(Actions.INITIAL_DATA, msg));
            }
        });

        messageHandlers.put(Actions.STOP_SERVER, new MessageObject(Object.class) {
            @Override
            public void handleMessage(Object object, String fromAddress) {
                ConnectedDevice device = connectedDevices.get(fromAddress);
                device.connectionState = ConnectionController.ConnectionState.DISCONNECTED;
                device.handler.terminate();
                connectedDevices.remove(fromAddress);
            }
        });

        messageHandlers.put(Actions.NEW_ORDER, new MessageObject<NewEventOrder>(NewEventOrder.class){
            @Override
            public void handleMessage(final NewEventOrder neo, String fromAddress) {
                final EventOrder newOrder = new ModelHolder<>(EventOrder.class).setModel(neo.order).updateOrSave(new Consumer<EventOrder>() {
                    @Override
                    public void consume(EventOrder order) {
                        order.createdBy = new ViewController<>(Person.class).get(neo.order.createdBy.getUuid());
                        order.eventTable = new ViewController<>(EventTable.class).get(neo.order.eventTable.getUuid());
                        order.orderTime = neo.order.orderTime;
                    }
                }).getModel();

                for (final OrderPosition orderPosDTO: neo.orderPositions){
                    saveOrderPos(orderPosDTO);
                }
            }
        });

        messageHandlers.put(Actions.DELETE_ORDER_POSITIONS, new MessageObject<OrderPositionsHolder>(OrderPositionsHolder.class){
            @Override
            public void handleMessage(OrderPositionsHolder holder, String fromAddress) {
                final ViewController<OrderPosition> controller = new ViewController<>(OrderPosition.class);
                for (OrderPosition orderPosition: holder.orderPositions){
                    new ModelHolder<>(OrderPosition.class).setModel(orderPosition).updateOrSave(new Consumer<OrderPosition>() {
                        @Override
                        public void consume(OrderPosition orderPosition) {
                            controller.delete(orderPosition);
                        }
                    });
                }
            }
        });

        messageHandlers.put(Actions.SET_ORDER_POSITIONS_PAYED, new MessageObject<OrderPositionsHolder>(OrderPositionsHolder.class){
            @Override
            public void handleMessage(OrderPositionsHolder holder, String fromAddress) {
                for (OrderPosition orderPosition: holder.orderPositions){
                    saveOrderPos(orderPosition);
                }
            }
        });

    }

    private void saveOrderPos(final OrderPosition orderPosDTO) {
        new ModelHolder<>(OrderPosition.class).setModel(orderPosDTO).updateOrSave(new Consumer<OrderPosition>() {
            @Override
            public void consume(OrderPosition orderPosition) {
                orderPosition.orderState = orderPosDTO.orderState;
                orderPosition.eventOrder = new ViewController<>(EventOrder.class).get(orderPosDTO.eventOrder.getUuid());
                orderPosition.payTime = orderPosDTO.payTime;
                orderPosition.product = new ViewController<>(Product.class).get(orderPosDTO.product.getUuid());
            }
        });
    }

    public void sendStop() {
        for (ConnectedDevice device : connectedDevices.values()){
            sendMessage(device.device, new DataMessage(Actions.STOP_SERVER, new Object()));
            device.handler.terminate();
        }
        serverService.terminate();
        removeLocalService();
    }
}
