import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class control extends AbstractActor {

    static public Props props() {
        return Props.create(control.class, () -> new control());
    }

    static class NewPrime {
        public int newPrime;

        public NewPrime(int newPrime) {
            this.newPrime = newPrime;
        }
    }

    private int N;
    private long timeIn, timeOut;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Sieve.Start.class, start -> {
                    N = start.num;
                    System.out.println("N = " + N);

                    start = System.nanoTime();

                    // Create first prime
                    ActorRef p2 = getContext().actorOf(PrimeActor.props(2, N, self()));
                    self().tell(new NewPrime(2), ActorRef.noSender());

                    for (int i = 3; i < N; i++) {
                        p2.tell(i, ActorRef.noSender());
                    }

                    p2.tell(new PrimeActor.End(), ActorRef.noSender());
                })
                .match(NewPrime.class, foo -> {
                    System.out.println(foo.newPrime + " is prime.");
                })
                .match(Prime.End.class, foo -> {
                  
         System.out.printf("Time for Actors: %.5f (ms)",  (System.nanoTime() - start) * 1e-6);
                })
                .build();
    }
}