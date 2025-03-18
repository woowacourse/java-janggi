public class Piece {
    private final Dynasty dynasty;

    private Piece(final Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    public static Piece createFromHan() {
        return new Piece(Dynasty.HAN);
    }

    public static Piece createFromCho() {
        return new Piece(Dynasty.CHO);
    }

    public Dynasty getDynasty() {
        return dynasty;
    }
}
