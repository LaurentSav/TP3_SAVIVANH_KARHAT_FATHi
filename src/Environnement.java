import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class Environnement{

    private static int taille;
    private Case[][] Grille;
    private Agent agent;
    private int previousPerformance;
    private int performance;

    /* Pourcentage d'Apparition */
    public static int monsterPercentage = 10;
    public static int crevassePercentage = 10;

    public Environnement(){
        taille = 3;
        performance = 0;
        previousPerformance = 0;
        CreateNewMap(taille);
        System.out.println(this);
        agent = new Agent(this);
    }

    public void CreateNewMap(int taille){
        Grille = new Case[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Grille[i][j] = new Case(new Point(i,j));
            }
        }
        addAgent();
        addElementToMap();
    }

    public void Tirer(Point p){
        Grille[p.x][p.y].setMonster(false);
        System.out.println("L'agent a tiré!");
    }

    public void Sortir(Point p){
        if(Grille[p.x][p.y].isPortal){
            System.out.println("L'agent est sorti de la foret!");
            taille++;
            CreateNewMap(taille);
            agent = new Agent(this);
            previousPerformance = performance;
            System.out.println("Performance : " + performance);
        }
    }

    public void Mourir(){
        System.out.println("L'agent est MORT");
        previousPerformance = performance;

        // Reinitialisation de l'agent dans l'environnement
        Case c = getCaseAgent();
        c.isAgent = false;
        addAgent();

        // Reinitialisation de l'agent
        agent = new Agent(this);
    }

    public Case getCaseAgent(){
        for (int i = 0; i < Grille.length; i++) {
            for (int j = 0; j < Grille.length; j++) {
                if(Grille[i][j].isAgent()){
                    return Grille[i][j];
                }
            }
        }
        return null;
    }




    /*
     * ADD ELEMENTS TO MAP
     */

    public void addElementToMap(){

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if(i != 0 && j != 0){
                    addMonster(i,j);
                    addCrevasse(i,j);
                }
            }
        }
        addPortal();
    }



    public void addMonster(int i, int j){
        if(new Random().nextInt(100) <= monsterPercentage){
            Grille[i][j].isMonster = true;
            // Update Case Haut
            if(i > 0){
                Grille[i-1][j].isSmellBad = true;
            }
            // Update Case Bas
            if(i < taille - 1){
                Grille[i+1][j].isSmellBad = true;
            }
            // Update Case Gauche
            if(j > 0){
                Grille[i][j-1].isSmellBad = true;
            }
            // Update Case Droite
            if(j < taille - 1){
                Grille[i][j+1].isSmellBad = true;
            }
        }
    }

    public void addCrevasse(int i, int j){
        if(new Random().nextInt(100) <= crevassePercentage){
            Grille[i][j].isCrevasse = true;
            // Update Case Haut
            if(i > 0){
                Grille[i-1][j].isWindy = true;
            }
            // Update Case Bas
            if(i < taille - 1){
                Grille[i+1][j].isWindy = true;
            }
            // Update Case Gauche
            if(j > 0){
                Grille[i][j-1].isWindy = true;
            }
            // Update Case Droite
            if(j < taille - 1){
                Grille[i][j+1].isWindy = true;
            }
        }
    }

    public void addAgent(){
        Grille[0][0].isAgent = true;
    }

    public void addPortal(){
        boolean portal = false;
        while(!portal){
            int i = (int) (Math.random() * taille);
            int j = (int) (Math.random() * taille);
            if(!Grille[i][j].isCrevasse && !Grille[i][j].isMonster && i != 0 && j != 0){
                Grille[i][j].setPortal(true);
                portal = true;
            }
        }
    }

    public static int getTaille() {
        return taille;
    }

    public Case[][] getGrille() {
        return Grille;
    }

    public Agent getAgent() {
        return agent;
    }

    public void updatePerformance(int cout){
        performance += cout;
    }



    @Override
    public String toString() {
        System.out.println("L'environnement");
        String affichage = "";
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                affichage += Grille[i][j];
            }
            affichage += "\n";
        }
        return affichage;
    }
}
