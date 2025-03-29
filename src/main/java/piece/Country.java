package piece;

public enum Country {
    Cho,
    Han;

    public Country reverseCountry() {
        if (this == Cho) {
            return Han;
        }
        return Cho;
    }
}
