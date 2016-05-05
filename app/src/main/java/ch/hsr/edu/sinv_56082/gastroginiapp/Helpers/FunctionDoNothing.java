package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

public class FunctionDoNothing<A, R> implements Function<A,R> {
    @Override
    public R function(A a) {
        return null;
    }
}
