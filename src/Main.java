import java.io.IOException;

public class Main{

    public static void  main(String[] args){
        Connection con = new Connection("Henk");
        Connection con2 = new Connection("Bassie");

        Thread t1 = new Thread(con);
        Thread t2 = new Thread(con2);

        t1.start();
        t2.start();

    }

}
