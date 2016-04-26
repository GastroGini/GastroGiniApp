package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

/**
 * Created by tobias on 26.04.2016.
 */
public class SupplyNull<A> implements Supplier<A> {
    @Override
    public A supply() {
        return null;
    }
}
