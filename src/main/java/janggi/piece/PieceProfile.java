package janggi.piece;

public class PieceProfile {

    private final String name;
    private final Nation nation;

    public PieceProfile(final String name, final Nation nation) {
        this.name = name;
        this.nation = nation;
    }

    public String getName() {
        return name;
    }

    public Nation getNation() {
        return nation;
    }

    public boolean isCho() {
        return Nation.isCho(this.nation);
    }

    public boolean isHan() {
        return Nation.isHan(this.nation);
    }

}
