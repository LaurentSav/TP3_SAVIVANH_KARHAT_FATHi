import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Inférence {

    /*
    * Pour notre système expert, nous utilisons le chainage avant
    * Notre base de fait est la case où se trouve l'agent ainsi que la grille de l'agent (où se trouve toutes les cases connues de l'agent)
    * et on met à jour cette base de fait en appliquant les règles du fichier .txt
     */

    public void chainageAvant(Case caseAgent, Case[][] grille){

        // Chargement des règles
        ArrayList<Rule> listRule = readRules();
        while(!allRuleMarked(listRule)){
            // Marquage des règles contradictoire
            contradictionRules(caseAgent, listRule);
            ArrayList<Rule> UnmarkedList = chooseUnmarkedRule(listRule);
            if(!UnmarkedList.isEmpty()){
                Rule r = UnmarkedList.get(0);
                // Application de la règle
                applicationRegle(r, grille, caseAgent);
                // Marquage de la règle
                r.setMarquer(true);
            }
        }
    }

    // Fonction qui permet de savoir si toute les règles ont été marquées.
    public boolean allRuleMarked(ArrayList<Rule> listRule){
        for(Rule r : listRule){
            if(!r.isMarquer()){
                return false;
            }
        }
        return true;
    }


    // Fonction qui permet de mettre toute les règles non marqué dans une liste
    public ArrayList<Rule> chooseUnmarkedRule(ArrayList<Rule> listRule){
        ArrayList<Rule> listUnmarked = new ArrayList<>();
        for (Rule r : listRule){
            if(!r.isMarquer()){
                listUnmarked.add(r);
            }
        }
        return listUnmarked;
    }

    // Fonction qui marque les règles contradictoires
    public void contradictionRules(Case caseAgent, ArrayList<Rule> listRule){
        for(Rule r : listRule){
            if(!r.isMarquer()){
                // On marque toutes les règles et les règles non contradictoires, on les démarque
                r.setMarquer(true);

                // On compare la base de fait (la case de l'agent) et la règle
                if(caseAgent.isSmellBad() && r.getCondition().contains("Odeur")){
                    r.setMarquer(false);
                }
                if(caseAgent.isWindy() && r.getCondition().contains("Vent")){
                    r.setMarquer(false);
                }
            }
        }
    }

    /*
    *   Application des règles en fonction de mot-clés
    *
     */

    public void applicationRegle(Rule r, Case[][] Grille, Case caseAgent){
        int x = caseAgent.getP().x;
        int y = caseAgent.getP().y;

        if(r.getConsequence().contains("Monstre_Bas")){
            if(x < Grille.length - 1 && !Grille[x + 1][y].isKnown){
                Grille[caseAgent.getP().x + 1][caseAgent.getP().y].isMonster = true;
            }
        }
        if(r.getConsequence().contains("Monstre_Haut")){
            if(x > 0 && !Grille[x - 1][y].isKnown){
                Grille[caseAgent.getP().x - 1][caseAgent.getP().y].isMonster = true;
            }
        }
        if(r.getConsequence().contains("Monstre_Gauche")){
            if(y > 0 && !Grille[x][y - 1].isKnown){
                Grille[caseAgent.getP().x][caseAgent.getP().y - 1].isMonster = true;
            }
        }
        if(r.getConsequence().contains("Monstre_Droit")){
            if(y < Grille.length - 1 && !Grille[x][y + 1].isKnown){
                Grille[caseAgent.getP().x][caseAgent.getP().y + 1].isMonster = true;
            }
        }


        if(r.getConsequence().contains("Crevasse_Bas")){
            if(x < Grille.length - 1 && !Grille[x + 1][y].isKnown){
                Grille[caseAgent.getP().x + 1][caseAgent.getP().y].isCrevasse = true;
            }
        }
        if(r.getConsequence().contains("Crevasse_Haut")){
            if(x > 0 && !Grille[x - 1][y].isKnown){
                Grille[caseAgent.getP().x - 1][caseAgent.getP().y].isCrevasse = true;
            }
        }
        if(r.getConsequence().contains("Crevasse_Gauche")){
            if(y > 0 && !Grille[x][y - 1].isKnown){
                Grille[caseAgent.getP().x][caseAgent.getP().y - 1].isCrevasse = true;
            }
        }
        if(r.getConsequence().contains("Crevasse_Droit")){
            if(y < Grille.length - 1 && !Grille[x][y + 1].isKnown){
                Grille[caseAgent.getP().x][caseAgent.getP().y + 1].isCrevasse = true;
            }
        }

    }



    /*
    * Lecture du fichier 'Rules.txt'
    *
     */
    public ArrayList<Rule> readRules(){

        ArrayList<Rule> listRules = new ArrayList<>();
        File f = new File("Rules.txt");
        try {
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                Rule r = new Rule(sc.nextLine());
                listRules.add(r);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listRules;
    }

}
