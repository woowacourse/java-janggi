package domain.vo;

public class Arrangements {
    private final Arrangement hanArrangement;
    private final Arrangement choArrangement;

    public Arrangements(Arrangement hanArrangement, Arrangement choArrangement) {
        this.hanArrangement = hanArrangement;
        this.choArrangement = choArrangement;
    }

    public static Arrangements empty() {
        return new Arrangements(null, null);
    }

    public Arrangement getHan() {
        return hanArrangement;
    }

    public Arrangement getCho() {
        return choArrangement;
    }
}
