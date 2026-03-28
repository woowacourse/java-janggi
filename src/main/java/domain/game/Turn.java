package domain.game;

public class Turn {
    private final Team current;

    private Turn(Team current) {
        this.current = current;
    }

    public static Turn first() {
        return new Turn(Team.CHO);
    }

    public Turn next() {
        if (current == Team.CHO) {
            return new Turn(Team.HAN);
        }
        return new Turn(Team.CHO);
    }

    public boolean isTurn(Team team) {
        return this.current == team;
    }

    public Team current() {
        return current;
    }
}
