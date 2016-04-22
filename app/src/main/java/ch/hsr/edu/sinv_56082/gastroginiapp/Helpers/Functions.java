package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

public class Functions {

    public interface DoIt{
        void doIt();
    }

    public interface Consumer<A>{
        void consume(A a);
    }

    public interface BiConsumer<A, B>{
        void consume(A a, B b);
    }

    public interface Supplier<A>{
        A supply();
    }

    public interface BiFunction<A,B,R>{
        R function(A a, B b);
    }

    public interface Function<A,R>{
        R function(A a);
    }
}
