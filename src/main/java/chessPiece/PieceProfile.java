package chessPiece;

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
}
