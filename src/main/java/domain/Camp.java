package domain;

public enum Camp {
    HAN("한나라"),
    CHO("초나라");

    private final String campName;

    Camp(String campName) {
        this.campName = campName;
    }

    public String getCampName() {
        return campName;
    }

    public Camp turnCamp() {
        if (this.equals(Camp.CHO)) {
            return Camp.HAN;
        }
        return Camp.CHO;
    }
}

