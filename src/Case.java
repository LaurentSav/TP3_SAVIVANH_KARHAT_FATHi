import java.awt.*;
import java.util.Random;

public class Case {

    Point p;
    boolean isAgent;
    boolean isMonster;
    boolean isPortal;
    boolean isCrevasse;
    boolean isSmellBad;
    boolean isWindy;
    boolean isKnown;

    public Case(Point p){
        this.p = p;
        isAgent = false;
        isMonster = false;
        isPortal = false;
        isCrevasse = false;
        isSmellBad = false;
        isWindy = false;
        isKnown = false;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public boolean isAgent() {
        return isAgent;
    }

    public void setAgent(boolean agent) {
        isAgent = agent;
    }

    public boolean isMonster() {
        return isMonster;
    }

    public void setMonster(boolean monster) {
        isMonster = monster;
    }

    public boolean isPortal() {
        return isPortal;
    }

    public void setPortal(boolean portal) {
        isPortal = portal;
    }

    public boolean isCrevasse() {
        return isCrevasse;
    }

    public void setCrevasse(boolean crevasse) {
        isCrevasse = crevasse;
    }

    public boolean isSmellBad() {
        return isSmellBad;
    }

    public void setSmellBad(boolean smellBad) {
        isSmellBad = smellBad;
    }

    public boolean isWindy() {
        return isWindy;
    }

    public void setWindy(boolean windy) {
        isWindy = windy;
    }

    @Override
    public String toString() {
        String affichage = "[";

        if(isAgent){
            affichage += "A";
        }else{
            affichage += " ";
        }

        if(isKnown){
            affichage += "K";
        }else{
            affichage += " ";
        }


        if(isMonster){
            affichage += "M";
        }else{
            affichage += " ";
        }

        if(isPortal){
            affichage += "P";
        }else{
            affichage += " ";
        }

        if(isCrevasse){
            affichage += "C";
        }else{
            affichage += " ";
        }

        if(isWindy){
            affichage += "W";
        }else{
            affichage += " ";
        }

        if(isSmellBad){
            affichage += "S";
        }else{
            affichage += " ";
        }
        //affichage += p.x + " " + p.y;
        affichage += "]";

        return affichage;

    }
}
