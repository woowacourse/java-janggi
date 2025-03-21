package piece;

public enum Country {
    HAN, CHO;

    public Country toggleTeam() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public static Country getDefaultTeam(){
        return HAN;
    }
}
