package domain;

import domain.piece.Team;

public class Turn {

    private Team currentTeam;

    public Turn(final Team startingTeam) {
        validateNotNull(startingTeam);
        this.currentTeam = startingTeam;
    }

    private void validateNotNull(final Team startingTeam) {
        if (startingTeam == null) {
            throw new IllegalArgumentException("턴은 반드시 팀을 가져야 합니다.");
        }
    }

    public void proceed() {
        currentTeam = currentTeam.nextTeam();
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }
}
