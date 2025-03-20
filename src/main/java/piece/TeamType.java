package piece;

public enum TeamType {
    RED, BLUE;

    public TeamType toggleTeam() {
        if (this == RED) {
            return BLUE;
        }
        return RED;
    }

    public static TeamType getDefaultTeam(){
        return RED;
    }
}
