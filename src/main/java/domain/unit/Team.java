package domain.unit;

public enum Team {
    
    HAN,
    CHO,
    ;

    public Team getOpposite() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
