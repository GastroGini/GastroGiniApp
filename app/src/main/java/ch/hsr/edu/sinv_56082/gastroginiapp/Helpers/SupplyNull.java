package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

public class SupplyNull<A> implements Supplier<A> {
    @Override
    public A supply() {
        return null;
    }
}
