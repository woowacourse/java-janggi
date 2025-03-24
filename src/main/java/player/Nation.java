package player;

import piece.Cha;

public enum Nation {
    HAN,
    CHO;

    public Nation getDefenseNation() {
        if (this.equals(HAN)) {
            return CHO;
        }
        return HAN;
    }
}
