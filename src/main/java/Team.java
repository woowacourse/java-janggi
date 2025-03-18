import java.util.Arrays;

public enum Team {
    RED("홍"),
    BLUE("청"),
    ;

    private static final String INVALID_TEAM = "팀은 홍 청이어야합니다.";

    Team(String type) {
        this.type = type;
    }

    private final String type;

    public static Team from(String s) {
        return Arrays.stream(Team.values())
                .filter(team -> s.equals(team.type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TEAM));
    }
}
