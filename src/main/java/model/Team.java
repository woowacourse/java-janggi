package model;

public enum Team {
    CHO,
    HAN;

    public Team nextTurn() {
        if(this==CHO)return HAN;
        return CHO;
    }
}
