public enum Dynasty {
    HAN("한나라"),
    CHO("초나라");

    private final String name;

    Dynasty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
