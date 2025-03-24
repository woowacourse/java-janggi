package janggi.piece;

public enum Nation {
    HAN,
    CHO;

    public static boolean isCho(final Nation nation) {
        return CHO.equals(nation);
    }

    public static boolean isHan(final Nation nation) {
        return HAN.equals(nation);
    }

    public boolean isSameNation(final Nation nation) {
        return this.equals(nation);
    }
}
