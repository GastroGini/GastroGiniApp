package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

public class ConsumerDoNothing<A> implements Consumer<A> {
        @Override
        public void consume(A o) {}
}
