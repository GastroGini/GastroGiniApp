package ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages;

public enum MessageAction {

    //Send by Server
    INITIAL_DATA("initial_data"),
    AUTHENTICATE_RESULT("authenticate_result"),
    STOP_SERVER("stop_server"),

    MESSAGE_RESULT("message_result"),

    //Send by Client
    GET_INITIAL_DATA("get_initial_data"),
    AUTHENTICATE("authenticate"),
    NEW_ORDER ("new_order"),
    DELETE_ORDER_POSITIONS ("delete_order_positions"),
    SET_ORDER_POSITIONS_PAYED("set_order_positions_payed"),
    DISCONNECT_CLIENT("disconnect_client");



    private final String name;

    MessageAction(String name) {
        this.name = name;
    }
}