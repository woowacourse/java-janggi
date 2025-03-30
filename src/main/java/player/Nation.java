package player;

public enum Nation {
    HAN,
    CHO;

    public Nation getDefenseNation() {
        if (this.equals(HAN)) {
            return CHO;
        }
        return HAN;
    }

    public static Nation getNationBy(String nation) {
        if (nation.equals("CHO")) {
            return CHO;
        }
        return HAN;
    }
}
