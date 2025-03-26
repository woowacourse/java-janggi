package janggi.domain.board;

import static janggi.domain.PieceType.BYEONG;
import static janggi.domain.PieceType.CHA;
import static janggi.domain.PieceType.GOONG;
import static janggi.domain.PieceType.JOL;
import static janggi.domain.PieceType.PO;
import static janggi.domain.PieceType.SA;

import janggi.domain.Coordinate;
import janggi.domain.Team;
import janggi.domain.board.maSangStrategy.MaSangStrategy;
import janggi.domain.Piece;
import java.util.HashSet;
import java.util.Set;

public class BoardBuilder {

    private final Set<Piece> pieces = new HashSet<>();

    public BoardBuilder initTeam(Team team, MaSangStrategy strategy) {
        Set<Piece> defaultBoard = createDefaultBoard(team);
        Set<Piece> maAndSangPieces = strategy.createMaAndSang(team);

        pieces.addAll(defaultBoard);
        pieces.addAll(maAndSangPieces);
        return this;
    }

    public Board build() {
        return new Board(pieces);
    }

    private static Set<Piece> createDefaultBoard(Team team) {
        if (team.equals(Team.HAN)) {
            return createHanDefaultBoard();
        }
        return createChoDefaultBoard();
    }

    private static Set<Piece> createHanDefaultBoard() {
        return Set.of(
            new Piece(Team.HAN, new Coordinate(1, 1), CHA),
            new Piece(Team.HAN, new Coordinate(4, 1), SA),
            new Piece(Team.HAN, new Coordinate(6, 1), SA),
            new Piece(Team.HAN, new Coordinate(9, 1), CHA),
            new Piece(Team.HAN, new Coordinate(5, 2), GOONG),
            new Piece(Team.HAN, new Coordinate(2, 3), PO),
            new Piece(Team.HAN, new Coordinate(8, 3), PO),
            new Piece(Team.HAN, new Coordinate(1, 4), BYEONG),
            new Piece(Team.HAN, new Coordinate(3, 4), BYEONG),
            new Piece(Team.HAN, new Coordinate(5, 4), BYEONG),
            new Piece(Team.HAN, new Coordinate(7, 4), BYEONG),
            new Piece(Team.HAN, new Coordinate(9, 4), BYEONG)
        );
    }

    private static Set<Piece> createChoDefaultBoard() {
        return Set.of(
            new Piece(Team.CHO, new Coordinate(1, 10), CHA),
            new Piece(Team.CHO, new Coordinate(4, 10), SA),
            new Piece(Team.CHO, new Coordinate(6, 10), SA),
            new Piece(Team.CHO, new Coordinate(9, 10), CHA),
            new Piece(Team.CHO, new Coordinate(5, 9), GOONG),
            new Piece(Team.CHO, new Coordinate(2, 8), PO),
            new Piece(Team.CHO, new Coordinate(8, 8), PO),
            new Piece(Team.CHO, new Coordinate(1, 7), JOL),
            new Piece(Team.CHO, new Coordinate(3, 7), JOL),
            new Piece(Team.CHO, new Coordinate(5, 7), JOL),
            new Piece(Team.CHO, new Coordinate(7, 7), JOL),
            new Piece(Team.CHO, new Coordinate(9, 7), JOL)
        );
    }
}
