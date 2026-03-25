package janggi.domain;

public enum Side {
    CHO("초"),
    HAN("한"),
    ;

    private final String displayName;

    Side(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
