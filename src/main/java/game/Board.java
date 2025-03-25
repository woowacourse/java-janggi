package game;

import position.Position;

public final class Board {

    private final Team cho;
    private final Team han;
    private boolean isChoTurn = true;

    public Team getCho() {
        return cho;
    }

    public Team getHan() {
        return han;
    }

    public Board(final Team cho, final Team han) {
        validateTeamIsNotNull(cho, han);
        validateCountryIsNotSame(cho, han);
        this.cho = Team.getFirstTeam(cho, han);
        this.han = Team.getSecondTeam(cho, han);
    }

    private void validateTeamIsNotNull(final Team team1, final Team team2) {
        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException("장기판은 필수값입니다.");
        }
    }

    private void validateCountryIsNotSame(final Team team1, final Team team2) {
        if (team1.getCountry().equals(team2.getCountry())) {
            throw new IllegalArgumentException("두 개의 장기판의 나라는 서로 달라야 합니다.");
        }
    }

    public void move(Position fromPosition, Position tagetPosition) {
        if (isChoTurn) {
            cho.move(fromPosition, tagetPosition, han.getPieces());
            han.removeIfExist(tagetPosition);
        } else {
            han.move(fromPosition, tagetPosition, cho.getPieces());
            cho.removeIfExist(tagetPosition);
        }
        nextTurn();
    }

    private void nextTurn() {
        isChoTurn = !isChoTurn;
    }

    public Team getCurrentTurnTeam() {
        return isChoTurn ? cho : han;
    }
}
