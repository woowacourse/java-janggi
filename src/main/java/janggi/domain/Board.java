package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece_initiaizer.StaticPieceInitializer;
import janggi.domain.position.Position;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class Board {

    private final Team team1;
    private final Team team2;

    private boolean isTeam1Turn = true;

    private Board(final Team team1, final Team team2) {
        validateTeamIsNotNull(team1, team2);
        validateCountryIsNotSame(team1, team2);
        this.team1 = Team.getFirstTeam(team1, team2);
        this.team2 = Team.getSecondTeam(team1, team2);
    }

    public static Board start(final StartingPosition startingPosition1, final StartingPosition startingPosition2) {
        final Team team1 = new Team(startingPosition1, new StaticPieceInitializer(), Country.CHO);
        final Team team2 = new Team(startingPosition2, new StaticPieceInitializer(), Country.HAN);
        return new Board(team1, team2);
    }

    public static Board continueWith(final Map<Country, List<Piece>> pieces, final Country turn) {
        final Team team1 = new Team(pieces.get(turn), turn);
        final Team team2 = new Team(pieces.get(turn.opponent()), turn.opponent());
        return new Board(team1, team2);
    }

    private void validateTeamIsNotNull(final Team team1, final Team team2) {
        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException("장기판은 필수값입니다.");
        }
    }

    private void validateCountryIsNotSame(final Team team1, final Team team2) {
        if (team1.isSameCountry(team2)) {
            throw new IllegalArgumentException("두 개의 장기판의 나라는 서로 달라야 합니다.");
        }
    }

    public Map<Country, List<Piece>> getBoard() {
        final Map<Country, List<Piece>> board = new EnumMap<>(Country.class);
        board.put(team1.getCountry(), team1.getPieces());
        board.put(team2.getCountry(), team2.getPieces());
        return Collections.unmodifiableMap(board);
    }

    public void move(Position fromPosition, Position tagetPosition) {
        if (isTeam1Turn) {
            team1.move(fromPosition, tagetPosition, team2);
            nextTurn();
            return;
        }
        team2.move(fromPosition, tagetPosition, team1);
        nextTurn();
    }

    private void nextTurn() {
        isTeam1Turn = !isTeam1Turn;
    }

    public boolean isEnd() {
        return team1.isEnd() || team2.isEnd();
    }

    public Country getCurrentCountry() {
        return getCurrentTeam().getCountry();
    }

    private Team getCurrentTeam() {
        if (isTeam1Turn) {
            return team1;
        }
        return team2;
    }

    public Country getWinner() {
        if (team1.isEnd()) {
            return team2.getCountry();
        }
        if (team2.isEnd()) {
            return team1.getCountry();
        }
        throw new IllegalStateException("아직 승자가 없습니다.");
    }

    public int getWinnerScore() {
        if (team1.isEnd()) {
            return team2.getScore();
        }
        if (team2.isEnd()) {
            return team1.getScore();
        }
        throw new IllegalStateException("아직 승자가 없습니다.");
    }

    public int getLooserScore() {
        if (team1.isEnd()) {
            return team1.getScore();
        }
        if (team2.isEnd()) {
            return team2.getScore();
        }
        throw new IllegalStateException("아직 승자가 없습니다.");
    }
}
