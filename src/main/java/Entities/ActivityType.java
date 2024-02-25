package Entities;

public enum ActivityType {
    LECONS_D_EQUITATION("Leçons d'équitation"),
    RANDONNEES("Randonnées"),
    ATELIERS_DE_SOINS_AUX_CHEVAUX("Ateliers de soins aux chevaux"),
    THERAPIE_ASSISTEE_PAR_LES_CHEVAUX("Thérapie assistée par les chevaux"),
    PROMENADES_EN_PONEY("Promenades en poney"),
    CLINIQUES_DE_FORMATION_DES_CHEVAUX("Cliniques de formation des chevaux"),
    EVENEMENTS_COMPETITIFS("Événements compétitifs"),
    PENSION_POUR_CHEVAUX("Pension pour chevaux"),
    FETES_EN_PONEY("Fêtes en poney"),
    PROGRAMMES_EDUCATIFS("Programmes éducatifs");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public static ActivityType valueOfIgnoreCase(String name) {
        for (ActivityType type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }

}
