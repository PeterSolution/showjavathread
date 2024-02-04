import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    static final Object lock=new Object();

    public static void main(String[] args) {
        ExecutorService exe= Executors.newCachedThreadPool();
        ExecutorService exe2=Executors.newCachedThreadPool();
        klasa1 k1=new klasa1();
        exe.submit(()->{
            int a=0;
            while (a<=100/2){
                synchronized (lock){
                    k1.dodaj(a);
                }
                a++;
            }
        });
        exe2.submit(()->{
           int b=51;
           while (b<=100){
               synchronized (lock){
                   k1.dodaj(b);

               }
               b++;
           }
        });
        try {
            if (!exe.awaitTermination(5, TimeUnit.SECONDS)) {
                exe2.shutdownNow();
            }
        } catch (InterruptedException e) {
            exe2.shutdownNow();
        }
        try {
            if (!exe.awaitTermination(5, TimeUnit.SECONDS)) {
                exe.shutdownNow();
            }
        } catch (InterruptedException e) {
            exe.shutdownNow();
        }

        System.out.printf(k1.zwoc()+"\n");

    }
}