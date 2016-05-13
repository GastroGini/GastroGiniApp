package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.server;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectedDevice;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.MessageAction;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.MessageObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.initial_data.InitialDataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.new_event_order.NewEventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.order_positions.OrderPositionsHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.serialization.ModelHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

public class ServerMessageHandler extends MessageHandler {


    private final P2pServer server;
    private final Event runningEvent;

    ServerMessageHandler(P2pServer server, Event runningEvent){
        this.server = server;
        this.runningEvent = runningEvent;
    }


    @Override
    public void registerMessages() {
        messageHandlers.put(MessageAction.GET_INITIAL_DATA, new MessageObject<Object>(Object.class){
            @Override
            public void handleMessage(Object object, String from) {
                List<Product> products = runningEvent.productList.products();
                List<EventTable> tables = runningEvent.eventTables();
                InitialDataMessage msg = new InitialDataMessage(runningEvent, products, tables);
                server.sendMessage(from, new DataMessage(MessageAction.INITIAL_DATA, msg));
            }
        });

        messageHandlers.put(MessageAction.DISCONNECT_CLIENT, new MessageObject(Object.class) {
            @Override
            public void handleMessage(Object object, String fromAddress) {
                ConnectedDevice device = server.connectedDevices.get(fromAddress);
                device.connectionState = ConnectionState.DISCONNECTED;
                device.handler.terminate();
                server.connectedDevices.remove(fromAddress);
            }
        });

        messageHandlers.put(MessageAction.NEW_ORDER, new MessageObject<NewEventOrder>(NewEventOrder.class){
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
                server.orderPositionListener.doIt();
            }
        });

        messageHandlers.put(MessageAction.DELETE_ORDER_POSITIONS, new MessageObject<OrderPositionsHolder>(OrderPositionsHolder.class){
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
                server.orderPositionListener.doIt();
            }
        });

        messageHandlers.put(MessageAction.SET_ORDER_POSITIONS_PAYED, new MessageObject<OrderPositionsHolder>(OrderPositionsHolder.class){
            @Override
            public void handleMessage(OrderPositionsHolder holder, String fromAddress) {
                for (OrderPosition orderPosition: holder.orderPositions){
                    saveOrderPos(orderPosition);
                }
                server.orderPositionListener.doIt();
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
}


