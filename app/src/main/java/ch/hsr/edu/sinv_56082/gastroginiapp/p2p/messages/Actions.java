package ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages;

public class Actions {

    //Send by Server
    public static final String INITIAL_DATA = "initial_data";
    public static final String AUTHENTICATE_RESULT = "authenticate_result";
    public static final String STOP_SERVER = "stop_server";

    public static final String MESSAGE_RESULT = "message_result";

    //Send by Client
    public static final String GET_INITIAL_DATA = "get_initial_data";
    public static final String AUTHENTICATE = "authenticate";
    public static final String NEW_ORDER = "new_order";
    public static final String DELETE_ORDER_POSITIONS = "delete_order_positions";
    public static final String SET_ORDER_POSITIONS_PAYED = "set_order_positions_payed";
    public static final String DISCONNECT_CLIENT = "disconnect_client";

}