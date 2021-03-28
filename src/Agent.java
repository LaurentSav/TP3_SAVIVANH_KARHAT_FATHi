import java.awt.*;
import java.util.ArrayList;

public class Agent {

    private Point positionAgent;
    private Capteur beliefs;
    private Effecteur effecteur;



    public Agent(Environnement environnement){
        positionAgent = environnement.getCaseAgent().getP();
        effecteur = new Effecteur();
        beliefs = new Capteur(environnement.getGrille()[0][0]);
        beliefs.getKnownAdjacentCase();
    }

    public void ObserveCase(Environnement env){
        // Mise à jour des croyances de l'actuelle case de l'agent
        beliefs.ObserveCurrentCase(env.getCaseAgent());
        // Mise à jour de la frontière entre les cases non connues et connues de l'agent
        beliefs.getKnownAdjacentCase();
    }

    public Capteur getBeliefs() {
        return beliefs;
    }

    public void Bouger(Environnement env){

        ObserveCase(env);

        if(!foundPortal(env)){
            Inférence i = new Inférence();
            if(beliefs.getCaseAgent() != null){
                i.chainageAvant(beliefs.getCaseAgent(), beliefs.getGrille());
            }


            if(!checkCase(env)){

                // Choix et Execution de la prochaine action
                nextAction(env);

                // Mise à jour des croyances après exécution d'une action
                ObserveCase(env);
                if(beliefs.getCaseAgent() != null){
                    i.chainageAvant(beliefs.getCaseAgent(), beliefs.getGrille());
                }
            }
        }
    }

    public void nextAction(Environnement env){
        positionAgent = beliefs.getCaseAgent().getP();
        Case c = chooseNextCase(env);

        // Mouvement de l'agent
        effecteur.teleportation(positionAgent, c.getP(), env);

        // Mise a jour de la position de l'agent dans ses croyances
        beliefs.updateAgent(positionAgent, c.getP());
    }

    public Case chooseNextCase(Environnement env){
        Case nextCase = null;
        double probCase = 0;
        beliefs.getKnownAdjacentCase();
        for(Case c : beliefs.getAdjacentKnownCase()){
            double proba = 1;
            if(!c.isMonster && !c.isCrevasse){
                nextCase = c;
                break;
            }
            if(beliefs.isAllMonsterOrCrevasse()){
               //indepCondi(c);
                if(c.isMonster){
                    proba *= checkAdjacentSmell(c);
                }

                if(c.isCrevasse){
                    proba = checkAdjacentWind(c);
                }

                if(proba > probCase ){
                    nextCase = c;
                    probCase = proba;
                }
                System.out.println(proba);
            }
        }

        // Si la probabilité qu'il n'y a pas de Monstre sur la Case
        System.out.println(probCase);
        if(probCase < 0.4 && nextCase.isMonster){
            effecteur.Tirer(nextCase.p, env);
        }

        return nextCase;
    }


    public double checkAdjacentSmell(Case c){

        int i = c.getP().x;
        int j = c.getP().y;

        boolean caseVide = false;
        double probaMonstre = 1;
        // Case Haut
        if(i > 0){
            if(beliefs.getGrille()[i-1][j].isSmellBad){
               probaMonstre *= checkAdjacentMonster(beliefs.getGrille()[i-1][j]);
            }
            if(beliefs.getGrille()[i-1][j].isKnown && !beliefs.getGrille()[i-1][j].isSmellBad){
                caseVide = true;
            }
        }
        // Case Bas
        if(i < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i+1][j].isSmellBad){
                probaMonstre *= checkAdjacentMonster(beliefs.getGrille()[i+1][j]);
            }
            if(beliefs.getGrille()[i+1][j].isKnown && !beliefs.getGrille()[i+1][j].isSmellBad){
                caseVide = true;
            }
        }
        // Case Gauche
        if(j > 0){
            if(beliefs.getGrille()[i][j-1].isSmellBad){
                probaMonstre *= checkAdjacentMonster(beliefs.getGrille()[i][j-1]);
            }
            if(beliefs.getGrille()[i][j-1].isKnown && !beliefs.getGrille()[i][j-1].isSmellBad){
                caseVide = true;
            }
        }
        // Case Droite
        if(j < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i][j+1].isSmellBad){
                probaMonstre *= checkAdjacentMonster(beliefs.getGrille()[i][j+1]);
            }
            if(beliefs.getGrille()[i][j+1].isKnown && !beliefs.getGrille()[i][j+1].isSmellBad){
                caseVide = true;
            }
        }

        // Si caseVide alors ce n'est pas possible qu'il y est un monstre sur la case C
        if(caseVide){
            return 1;
        }

        return probaMonstre;
    }

    public double checkAdjacentMonster(Case c){

        int i = c.getP().x;
        int j = c.getP().y;

        double nbdeMonstre = 0;
        double probaMonstre = 0;

        // Case Haut
        if(i > 0){
            if(beliefs.getGrille()[i-1][j].isMonster){
                nbdeMonstre++;
            }
        }

        // Case Bas
        if(i < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i+1][j].isMonster){
                nbdeMonstre++;
            }
        }

        // Case Gauche
        if(j > 0){
            if(beliefs.getGrille()[i][j-1].isMonster){
                nbdeMonstre++;
            }
        }

        // Case Droite
        if(j < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i][j+1].isMonster){
                nbdeMonstre++;
            }
        }

        probaMonstre = 1/nbdeMonstre;

        if(probaMonstre == 1){
            probaMonstre = 0;
        }

        return probaMonstre;
    }
    public double checkAdjacentCrevasse(Case c){

        int i = c.getP().x;
        int j = c.getP().y;

        double nbdeCrevasse = 0;
        double probaCrevasse = 0;

        // Case Haut
        if(i > 0){
            if(beliefs.getGrille()[i-1][j].isCrevasse){
                nbdeCrevasse++;
            }
        }

        // Case Bas
        if(i < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i+1][j].isCrevasse){
                nbdeCrevasse++;
            }
        }

        // Case Gauche
        if(j > 0){
            if(beliefs.getGrille()[i][j-1].isCrevasse){
                nbdeCrevasse++;
            }
        }

        // Case Droite
        if(j < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i][j+1].isCrevasse){
                nbdeCrevasse++;
            }
        }

        probaCrevasse = 1/nbdeCrevasse;
        if(probaCrevasse == 1){
            probaCrevasse = 0;
        }

        return probaCrevasse;
    }



    public double checkAdjacentWind(Case c){

        int i = c.getP().x;
        int j = c.getP().y;

        boolean caseVide = false;
        double probaCrevasse = 1;

        // Case Haut
        if(i > 0){
            if(beliefs.getGrille()[i-1][j].isWindy){
                probaCrevasse *= checkAdjacentCrevasse(beliefs.getGrille()[i-1][j]);
            }
            if(beliefs.getGrille()[i-1][j].isKnown && !beliefs.getGrille()[i-1][j].isWindy){
                caseVide = true;
            }
        }
        // Case Bas
        if(i < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i+1][j].isWindy){
                probaCrevasse *= checkAdjacentCrevasse(beliefs.getGrille()[i+1][j]);
            }
            if(beliefs.getGrille()[i+1][j].isKnown && !beliefs.getGrille()[i+1][j].isWindy){
                caseVide = true;
            }
        }
        // Case Gauche
        if(j > 0){
            if(beliefs.getGrille()[i][j-1].isWindy){
                probaCrevasse *= checkAdjacentCrevasse(beliefs.getGrille()[i][j-1]);
            }
            if(beliefs.getGrille()[i][j-1].isKnown && !beliefs.getGrille()[i][j-1].isWindy){
                caseVide = true;
            }
        }
        // Case Droite
        if(j < beliefs.getGrille().length - 1){
            if(beliefs.getGrille()[i][j+1].isWindy){
                probaCrevasse *= checkAdjacentCrevasse(beliefs.getGrille()[i][j+1]);
            }
            if(beliefs.getGrille()[i][j+1].isKnown && !beliefs.getGrille()[i][j+1].isWindy){
                caseVide = true;
            }
        }

        // Si caseVide alors ce n'est pas possible qu'il y est un monstre sur la case C
        if(caseVide){
            // On reinitialise la probablité de vivre à 100, car cela n'est pas possible de mourir sur cette case
            return 1;
        }

        return probaCrevasse;
    }


    /*
    * Cette fonction represente le désire de notre Agent, c'est a dire trouvé le portail dans la forêt
    * Si le portail a été trouvé, alors
    *
     */
    public boolean foundPortal(Environnement env){
        if(beliefs.getCaseAgent().isPortal){
            effecteur.Sortir(beliefs.getCaseAgent().getP(), env);
            return true;
        }
        return false;
    }

    /*
    * L'agent regarde s'il est bien mort sur une case monstre ou une case crevasse
     */
    public boolean checkCase(Environnement env){
        if(beliefs.getCaseAgent().isCrevasse() || beliefs.getCaseAgent().isMonster()){
            effecteur.Mourir(env);
            return true;
        }
        return false;
    }

}
