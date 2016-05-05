package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

public class BiFunctionDoNothing<A, B, R> implements BiFunction<A,B,R> {
    @Override
    public R function(A a, B b) {
        return null;
    }
}
