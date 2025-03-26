package domain;

public record Player(
        Team team
) {

    public boolean isHanTeam() {
        return team == Team.HAN;
    }
}
