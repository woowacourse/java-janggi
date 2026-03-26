package domain;

public enum MaSang {
    HAN_P1(1, 2, Country.HAN),
    HAN_P2(1, 3, Country.HAN),
    HAN_P3(1, 7, Country.HAN),
    HAN_P4(1, 8, Country.HAN),

    CHO_P1(10, 2, Country.CHO),
    CHO_P2(10, 3, Country.CHO),
    CHO_P3(10, 7, Country.CHO),
    CHO_P4(10, 8, Country.CHO);

    private final int x;
    private final int y;
    private final Country country;

    MaSang(int x, int y, Country country) {
        this.x = x;
        this.y = y;
        this.country = country;
    }

    public Position getPosition() {
        return new Position(this.x, this.y);
    }

    public Country getCountry() {
        return country;
    }

    public int getIndex() {
        return this.ordinal();
    }
}
