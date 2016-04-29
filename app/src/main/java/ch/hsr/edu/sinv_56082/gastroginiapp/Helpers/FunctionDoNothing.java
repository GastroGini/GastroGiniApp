package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

/**
 * Created by tobias on 26.04.2016.
 */
public class FunctionDoNothing<A, R> implements Function<A,R> {
    @Override
    public R function(A a) {
        return null;
    }
}
