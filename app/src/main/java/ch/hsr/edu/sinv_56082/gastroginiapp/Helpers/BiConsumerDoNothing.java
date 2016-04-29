package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

/**
 * Created by tobias on 26.04.2016.
 */
public class BiConsumerDoNothing<A, B> implements BiConsumer<A, B> {
        @Override
        public void consume(A o, B b) {}
}
