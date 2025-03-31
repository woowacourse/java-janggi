package object.board;

import object.board.creator.TableSettingCreator;
import object.coordinate.Coordinate;
import java.util.Map;
import object.piece.Piece;
import object.team.Country;
import object.team.Score;
import object.team.Team;
import object.team.Teams;

public class Board {

    public static final int BOARD_MIN_WIDTH = 1;
    public static final int BOARD_MAX_WIDTH = 9;
    public static final int BOARD_MIN_HEIGHT = 1;
    public static final int BOARD_MAX_HEIGHT = 10;

    private final Teams teams;

    public Board(Teams teams) {
        this.teams = teams;
    }

    public static Board create(TableSettingCreator hanStrategy, TableSettingCreator choStrategy) {
        Team han = new Team(Country.HAN, hanStrategy.create(Country.HAN), new Score(0));
        Team cho = new Team(Country.CHO, choStrategy.create(Country.CHO), new Score(0));

        return new Board(new Teams(han, cho));
    }

    public void move(Country country, Coordinate departure, Coordinate arrival) {
        Piece piece = findPiece(departure);

        if (!piece.canMove(this, departure, arrival)) {
            throw new IllegalStateException("이동할 수 없는 좌표입니다.");
        }

        teams.movePiece(country, piece, departure, arrival);
    }

    public Piece findPiece(Coordinate coordinate) {
        return teams.findPiece(coordinate);
    }

    public boolean hasPiece(Coordinate coordinate) {
        return teams.hasPiece(coordinate);
    }

    public boolean isEnd() {
        return teams.isEnd();
    }

    public Country judgeWinner() {
        return teams.judgeWinner().getCountry();
    }

    public Map<Coordinate, Piece> getUnmodifiablePieces() {
        return teams.getUnmodifiablePieces();
    }

    public Map<Country, Integer> getUnmodifiableScores() {
        return teams.getUnmodifiableScores();
    }
}
