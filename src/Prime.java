import akka.actor.*;


public class Prime extends AbstractActor {

    static public Props props(int currentPrime, int N, ActorRef manager) {
        return Props.create(Prime.class, () -> new Prime(currentPrime, N, manager));
    }

    static class End{}

    private int current;
    private int num;
    private ActorRef m;
    private ActorRef nextPrime;
    private boolean nextPrime;

    public Prime(int nowPrime, int n, ActorRef m) {
        this.current = nowPrime;
        num = n;
        this.m = m;
        this.nextPrime = null;
        nextPrime = true;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class, num -> {
                    int p = num;

                    if (p > Math.sqrt(N)) {
                        getContext().become(receiveBuilder()
                                .match(Integer.class, RemainingPrime -> {
                                   
                                    if (p % currentPrime != 0) {
                                        manager.tell(new SieveManager.NewPrime(p), ActorRef.noSender());
                                    }
                                    getContext().unbecome();
                                })
                                // last prime
                                .match(End.class, lastPrime -> {
                                    manager.tell(new End(), ActorRef.noSender());
                                    getContext().unbecome();
                                })
                                .build());
                    } else {
                        if (needNextPrime) {
                            nextPrime = getContext().actorOf(PrimeActor.props(p, N, manager));
                            manager.tell(new SieveManager.NewPrime(p), ActorRef.noSender());
                            needNextPrime = false;
                        }
                        getContext().become(receiveBuilder()
                                .match(Integer.class, AfterPrime -> {
                                    
                                    if (p % currentPrime != 0) {
                                        nextPrime.tell(num, ActorRef.noSender());
                                    }
                                    getContext().unbecome();
                                })
                                
                                .match(End.class, endOfNums -> {
                                    nextPrime.tell(new End(), ActorRef.noSender());
                                    getContext().unbecome();
                                })
                                .build());
                    }
                })
                .match(End.class, foo -> {
                    manager.tell(new End(), ActorRef.noSender());
                })
                .build();
    }
}