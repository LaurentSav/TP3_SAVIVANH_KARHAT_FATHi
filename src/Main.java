import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Environnement env = new Environnement();
        System.out.println(env.getAgent().getBeliefs());

        while(true){

            Scanner sc = new Scanner(System.in);
            System.out.println("Press Enter to order the Agent to move");
            String test = sc.nextLine();
            env.getAgent().Bouger(env);
            System.out.println(env);
            System.out.println(env.getAgent().getBeliefs());

        }


    }


}
