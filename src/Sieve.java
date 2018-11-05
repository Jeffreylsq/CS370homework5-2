import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;


public class Sieve {
    static class Start {
        public int num;

        public Start(int num) {
            this.num = num;
        }
    }
    public static void main(String[] args) {
        final ActorSystem sieveSystem = ActorSystem.create("SieveOfEratosthenes");
        final int N = 100;

        ActorRef sManager = sieveSystem.actorOf(control.props(), "Manager");

        try {
            sManager.tell(new Start(N), ActorRef.noSender());
            System.out.println("Press Enter to End Program");
            System.in.read();
        } catch (IOException e) {

        } finally {
            sieveSystem.terminate();
        }
    }
}