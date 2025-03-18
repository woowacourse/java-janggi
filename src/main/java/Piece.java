public class Piece {
    private final Dynasty dynasty;
    private final String type;
    private Piece(final Dynasty dynasty, String type) {
        this.dynasty = dynasty;
        this.type = type;
    }

    public static Piece createFromHan(String type) {
        return new Piece(Dynasty.HAN, type);
    }

    public static Piece createFromCho(String type) {
        return new Piece(Dynasty.CHO, type);
    }

    public Dynasty getDynasty() {
        return dynasty;
    }
}
