package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.ArrayList;
import java.util.List;

public class Boards {

    private static final int BOARD_HEIGHT = 10;

    private final Board board;

    public Boards(Board board) {
        this.board = board;
    }

    public void move(Point from, Point to, Team team) {
        board.move(from, to, team);
    }

    public int scoreOf(Team team) {
        return board.getPiecesByPoint().values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(Piece::getScore)
                .sum();
    }

    public boolean isKingDie(Team team) {
        return board.getPiecesByPoint().values().stream()
                .noneMatch(piece -> piece.isSameType(PieceType.JANG) &&
                        piece.isSameTeam(team));
    }

    public List<PositionInfo> getBoardStatus() {
        return PositionInfo.from(board.getPiecesByPoint());
    }

    public List<List<Piece>> getPoints() {
        List<List<Piece>> pieces = new ArrayList<>();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            pieces.add(
                    Point.getPointsAtY(i).stream()
                            .map(board.getPiecesByPoint()::get)
                            .toList()
            );
        }
        return pieces;
    }
}
