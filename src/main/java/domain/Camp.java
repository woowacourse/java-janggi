package domain;

public enum Camp {
    HAN("한나라"),
    CHO("초나라"),
    NONE("중립");

    private final String campName;

    Camp(String campName) {
        this.campName = campName;
    }

    public String getCampName() {
        return campName;
    }
}

