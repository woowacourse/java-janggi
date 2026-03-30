package domain;

public enum Camp {
    HAN("한"),
    CHO("초");

    private final String campName;

    Camp(String campName) {
        this.campName = campName;
    }

    public String getCampName() {
        return campName;
    }
}

