import java.awt.*;
import java.util.ArrayList;

public class Effecteur {

    /*
    * Nous n'utilisons pas Haut, Bas, Gauche, Droite
    * Car comme indiqué dans la réunion zoom, il est préférable d'utiliser la téléportation
     */

    public void Haut(Point p, Environnement env){
        if(p.x > 0){
            int x = p.x - 1;
            env.getGrille()[p.x][p.y].setAgent(false);
            env.getGrille()[p.x][p.y].setKnown(true);
            env.getGrille()[x][p.y].setAgent(true);
            env.getGrille()[x][p.y].setKnown(true);

        }
    }

    public void Bas(Point p, Environnement env){
        if(p.x < Environnement.getTaille() - 1){
            int x = p.x + 1;
            env.getGrille()[p.x][p.y].setAgent(false);
            env.getGrille()[p.x][p.y].setKnown(true);
            env.getGrille()[x][p.y].setAgent(true);
            env.getGrille()[x][p.y].setKnown(true);
        }
    }

    public void Gauche(Point p, Environnement env){
        if(p.y > 0){
            int y = p.y - 1;
            env.getGrille()[p.x][p.y].setAgent(false);
            env.getGrille()[p.x][p.y].setKnown(true);
            env.getGrille()[p.x][y].setAgent(true);
            env.getGrille()[p.x][y].setKnown(true);
        }
    }

    public void Droite(Point p, Environnement env){
        if(p.y < Environnement.getTaille() - 1){
            int y = p.y + 1;
            env.getGrille()[p.x][p.y].setAgent(false);
            env.getGrille()[p.x][p.y].setKnown(true);
            env.getGrille()[p.x][y].setAgent(true);
            env.getGrille()[p.x][y].setKnown(true);
        }
    }


    // Téléportation de l'agent à un point mis en paramètre
    // Mise à jour de la position de l'agent dans l'environnement
    public void teleportation(Point positionAgent, Point nextPosition, Environnement env){
        env.getGrille()[positionAgent.x][positionAgent.y].setAgent(false);
        env.getGrille()[nextPosition.x][nextPosition.y].setAgent(true);

        // Mise a jour de la performance de l'agent dans l'environnement
        env.updatePerformance(-1);

        // Position de l'agent après la téléportation
        System.out.println("Nouvelle position: " + nextPosition.x +  "_" + nextPosition.y);
    }

    // Fonction qui permet à l
    public void Tirer(Point p, Environnement env){

        env.Tirer(p);
        env.updatePerformance(-10);
    }

    public void Sortir(Point p, Environnement env){
        env.updatePerformance(10 * Environnement.getTaille());
        env.Sortir(p);
    }

    public void Mourir(Environnement env) {
        env.updatePerformance(-10 * Environnement.getTaille());
        env.Mourir();
    }

    public void doAction(Point p, String action, Environnement env){
        switch (action){
            case "Gauche":
                Gauche(p, env);
                break;
            case "Droite":
                Droite(p, env);
                break;
            case "Haut":
                Haut(p, env);
                break;
            case "Bas":
                Bas(p, env);
                break;
            default:
                break;
        }
    }



}
