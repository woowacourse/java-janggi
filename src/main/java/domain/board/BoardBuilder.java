package domain.board;

import domain.Coordinate;
import domain.Team;
import domain.board.maSangStrategy.MaSangStrategy;
import domain.piece.noPathPiece.Byeong;
import domain.piece.pathPiece.Cha;
import domain.piece.noPathPiece.Goong;
import domain.piece.noPathPiece.Jol;
import domain.piece.Piece;
import domain.piece.pathPiece.Po;
import domain.piece.noPathPiece.Sa;
import java.util.HashMap;
import java.util.Map;

public class BoardBuilder {

    private final Map<Coordinate, Piece> board = new HashMap<>();

    public BoardBuilder initTeam(Team team, MaSangStrategy strategy) {
        Map<Coordinate, Piece> defaultBoard = createDefaultBoard(team);
        Map<Coordinate, Piece> maAndSangPieces = strategy.createMaAndSang(team);

        board.putAll(defaultBoard);
        board.putAll(maAndSangPieces);
        return this;
    }

    public Board build() {
        return new Board(board);
    }

    private static Map<Coordinate, Piece> createDefaultBoard(Team team) {
        if (team.equals(Team.HAN)) {
            return createHanDefaultBoard();
        }
        return createChoDefaultBoard();
    }

    private static Map<Coordinate, Piece> createHanDefaultBoard() {
        return Map.ofEntries(
            Map.entry(new Coordinate(1, 1), new Cha(Team.HAN, new Coordinate(1, 1))),
            Map.entry(new Coordinate(4, 1), new Sa(Team.HAN, new Coordinate(4, 1))),
            Map.entry(new Coordinate(6, 1), new Sa(Team.HAN, new Coordinate(6, 1))),
            Map.entry(new Coordinate(9, 1), new Cha(Team.HAN, new Coordinate(9, 1))),
            Map.entry(new Coordinate(5, 2), new Goong(Team.HAN, new Coordinate(5, 2))),
            Map.entry(new Coordinate(2, 3), new Po(Team.HAN, new Coordinate(2, 3))),
            Map.entry(new Coordinate(8, 3), new Po(Team.HAN, new Coordinate(8, 3))),
            Map.entry(new Coordinate(1, 4), new Byeong(new Coordinate(1, 4))),
            Map.entry(new Coordinate(3, 4), new Byeong(new Coordinate(3, 4))),
            Map.entry(new Coordinate(5, 4), new Byeong(new Coordinate(5, 4))),
            Map.entry(new Coordinate(7, 4), new Byeong(new Coordinate(7, 4))),
            Map.entry(new Coordinate(9, 4), new Byeong(new Coordinate(9, 4)))
        );
    }

    private static Map<Coordinate, Piece> createChoDefaultBoard() {
        return Map.ofEntries(
            Map.entry(new Coordinate(1, 10), new Cha(Team.CHO, new Coordinate(1, 10))),
            Map.entry(new Coordinate(4, 10), new Sa(Team.CHO, new Coordinate(4, 10))),
            Map.entry(new Coordinate(6, 10), new Sa(Team.CHO, new Coordinate(6, 10))),
            Map.entry(new Coordinate(9, 10), new Cha(Team.CHO, new Coordinate(9, 10))),
            Map.entry(new Coordinate(5, 9), new Goong(Team.CHO, new Coordinate(5, 9))),
            Map.entry(new Coordinate(2, 8), new Po(Team.CHO, new Coordinate(2, 8))),
            Map.entry(new Coordinate(8, 8), new Po(Team.CHO, new Coordinate(8, 8))),
            Map.entry(new Coordinate(1, 7), new Jol(new Coordinate(1, 7))),
            Map.entry(new Coordinate(3, 7), new Jol(new Coordinate(3, 7))),
            Map.entry(new Coordinate(5, 7), new Jol(new Coordinate(5, 7))),
            Map.entry(new Coordinate(7, 7), new Jol(new Coordinate(7, 7))),
            Map.entry(new Coordinate(9, 7), new Jol(new Coordinate(9, 7)))
        );
    }
}
