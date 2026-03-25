public enum Side {
    CHO,
    HAN,
    ;

    public boolean isSameAs(Side other) {
        return this.equals(other);
    }
}
