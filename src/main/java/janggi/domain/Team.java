package janggi.domain;

public enum Team {
    HAN("한"),
    CHO("초"),
    ;

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public static Team from(String name) {
        if (name.equals(HAN.name)) {
            return HAN;
        }

        if (name.equals(CHO.name)) {
            return CHO;
        }

        throw new IllegalArgumentException("적절하지 않은 진영입니다.");
    }
}
