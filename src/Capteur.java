import java.awt.*;
import java.util.ArrayList;

public class Capteur {

    private Case[][] grille;

    // Cet attribut correspond à la frontière entre les cases connues et non connues
    private ArrayList<Case> AdjacentKnownCase;

    public Capteur(Case c){
        initAgentGrille();
        AdjacentKnownCase = new ArrayList<>();
        ObserveCurrentCase(c);
    }

    // Fonction qui crée une grille vide
    public void initAgentGrille(){
        grille = new Case[Environnement.getTaille()][Environnement.getTaille()];
        for (int i = 0; i < Environnement.getTaille(); i++) {
            for (int j = 0; j < Environnement.getTaille(); j++) {
                grille[i][j] = new Case(new Point(i,j));
            }
        }
    }

    // Fonction qui récupère les données de la case mise en paramètre
    // L'agent pourra donc 'Observer' la case
    public void ObserveCurrentCase(Case c){
        if(c.isCrevasse){
            grille[c.getP().x][c.getP().y].setCrevasse(true);
        }else{
            grille[c.getP().x][c.getP().y].setCrevasse(false);
        }
        if(c.isMonster){
            grille[c.getP().x][c.getP().y].setMonster(true);
        }else{
            grille[c.getP().x][c.getP().y].setMonster(false);
        }
        if(c.isAgent){
            grille[c.getP().x][c.getP().y].setAgent(true);
        }else{
            grille[c.getP().x][c.getP().y].setAgent(false);
        }
        if(c.isPortal){
            grille[c.getP().x][c.getP().y].setPortal(true);
        }
        if(c.isWindy){
            grille[c.getP().x][c.getP().y].setWindy(true);
        }
        if(c.isSmellBad){
            grille[c.getP().x][c.getP().y].setSmellBad(true);
        }
    }

    // Fonction qui met a jour la position de l'agent (en cas de mort)
    public void updateAgent(Point previous, Point next){
        grille[previous.x][previous.y].setKnown(true);
        grille[previous.x][previous.y].setAgent(false);
        grille[next.x][next.y].setKnown(true);
        grille[next.x][next.y].setAgent(true);
    }

    // Affichage de la grille de l'agent
    @Override
    public String toString() {
        System.out.println("Ce que l'agent voit :");
        String affichage = "";
        for (int i = 0; i < Environnement.getTaille(); i++) {
            for (int j = 0; j < Environnement.getTaille(); j++) {
                affichage += grille[i][j];
            }
            affichage += "\n";
        }
        return affichage;
    }

    public Case getCaseAgent(){
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille.length; j++) {
                if(grille[i][j].isAgent()){
                    return grille[i][j];
                }
            }
        }
        return null;
    }

    public void getKnownAdjacentCase(){
        AdjacentKnownCase = new ArrayList<>();
        // Get Case Haut
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille.length; j++) {
                if(grille[i][j].isKnown || grille[i][j].isAgent){
                    if(i > 0){
                        if(!grille[i - 1][j].isKnown()){
                            this.AdjacentKnownCase.add(grille[i - 1][j]);
                        }
                    }
                    // Get Case Bas
                    if(i < grille.length - 1){
                        if(!grille[i + 1][j].isKnown()){
                            this.AdjacentKnownCase.add(grille[i + 1][j]);
                        }
                    }
                    // Get Case Gauche
                    if(j > 0){
                        if(!grille[i][j - 1].isKnown()){
                            this.AdjacentKnownCase.add(grille[i][j - 1]);
                        }
                    }
                    // Get Case Droite
                    if(j < grille.length - 1){
                        if(!grille[i][j + 1].isKnown()){
                            this.AdjacentKnownCase.add(grille[i][j + 1]);
                        }
                    }
                }
            }
        }
    }


    // Fonction qui permet de savoir si la frontière n'a plus de case vide
    public boolean isAllMonsterOrCrevasse(){
        for(Case c : AdjacentKnownCase){
            if(!c.isMonster && !c.isCrevasse){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Case> getAdjacentKnownCase() {
        return AdjacentKnownCase;
    }

    public Case[][] getGrille() {
        return grille;
    }

    public Case[][] getCopy(){
        Case[][] copy = new Case[grille.length][grille.length];
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille.length; j++) {
                if(grille[i][j].isCrevasse){
                    copy[i][j].setCrevasse(true);
                }else{
                    copy[i][j].setCrevasse(false);
                }
                if(grille[i][j].isMonster){
                    copy[i][j].setMonster(true);
                }else{
                    copy[i][j].setMonster(false);
                }
                if(grille[i][j].isAgent){
                    copy[i][j].setAgent(true);
                }else{
                    copy[i][j].setAgent(false);
                }
                if(grille[i][j].isPortal){
                    copy[i][j].setPortal(true);
                }
                if(grille[i][j].isWindy){
                    copy[i][j].setWindy(true);
                }else{
                    copy[i][j].setWindy(false);
                }
                if(grille[i][j].isSmellBad){
                    copy[i][j].setSmellBad(true);
                }else{
                    copy[i][j].setSmellBad(false);
                }
            }
        }
        return copy;
    }
}
