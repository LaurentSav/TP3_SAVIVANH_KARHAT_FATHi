public class Rule {

    // Contenu de la règle (contient la consequence et la/les conditions
    private String contenu;
    private boolean marquer;

    // Consequence de la règle
    private String consequence;
    // Condition de la règle
    private String condition;

    public Rule (String contenu){
        this.contenu = contenu;
        marquer = false;

        //Get Condition
        String[] SplitContent = contenu.split("Si");
        String[] SplitContent1 = SplitContent[1].split("Alors");
        condition = SplitContent1[0].trim();
        consequence = SplitContent1[1].trim();


    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public boolean isMarquer() {
        return marquer;
    }

    public void setMarquer(boolean marquer) {
        this.marquer = marquer;
    }
}
