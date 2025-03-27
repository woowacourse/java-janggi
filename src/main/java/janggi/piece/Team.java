package janggi.piece;

public enum Team {

    HAN("한나라(RED)") {
        @Override
        public Team changeTeam() {
            return CHU;
        }
    },
    CHU("초나라(GREEN)") {
        @Override
        public Team changeTeam() {
            return HAN;
        }
    };

    private final String description;

    Team(final String description) {
        this.description = description;
    }

    public abstract Team changeTeam();

    public static boolean isChu(final Team team) {
        return CHU.equals(team);
    }

    public static boolean isHan(final Team team) {
        return HAN.equals(team);
    }

    public boolean isSameTeam(final Team team) {
        return this.equals(team);
    }

    public String getDescription() {
        return description;
    }
}
