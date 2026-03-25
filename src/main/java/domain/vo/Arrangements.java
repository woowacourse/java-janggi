package domain.vo;

public class Arrangements {
    private final Arrangement hanArrangement;
    private final Arrangement choArrangement;

    public Arrangements(Arrangement hanArrangement, Arrangement choArrangement) {
        this.hanArrangement = hanArrangement;
        this.choArrangement = choArrangement;
    }

    public Arrangement getHan() {
        return hanArrangement;
    }

    public Arrangement getCho() {
        return choArrangement;
    }
}
