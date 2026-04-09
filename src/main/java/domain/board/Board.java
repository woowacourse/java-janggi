package domain.board;

import domain.board.palace.Palace;
import domain.coordination.Coordination;
import domain.game.Turn;
import domain.move.MoveContext;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int TOTAL_GENERAL_COUNT = 2;
    private static final Palace PALACE = new Palace();

    private final Map<Coordination, Piece> board;

    public Board(Map<Coordination, Piece> board) {
        this.board = board;
    }

    public void move(Coordination from, Coordination to) {
        Piece piece = board.get(from);
        MoveContext moveContext = PALACE.createMoveContext(from, to);

        validateRule(piece, moveContext);
        validatePiecesOnPath(piece, moveContext);
        validateNotSameTeam(to, piece);

        resolve(from, to, piece);
    }

    private void resolve(Coordination from, Coordination to, Piece piece) {
        board.put(to, piece);
        board.put(from, new EmptyPiece(Team.NONE));
    }

    public boolean hasTwoGenerals() {
        return board.values().stream()
                .filter(Piece::isAliveGeneral)
                .count() == TOTAL_GENERAL_COUNT;
    }

    public void checkSameTeam(Coordination from, Turn turn) {
        Piece piece = board.get(from);

        turn.validateSameTeam(piece);
    }

    public Map<Coordination, Piece> getBoard() {
        return Map.copyOf(this.board);
    }

    public double scoreOf(Team team) {
        return sumPieceScoreOf(team) + team.bonusScore();
    }

    private int sumPieceScoreOf(Team team) {
        return board.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(Piece::score)
                .sum();
    }

    private void validateNotSameTeam(Coordination to, Piece piece) {
        piece.validateNotSameTeam(board.get(to));
    }

    private void validatePiecesOnPath(Piece piece, MoveContext moveContext) {
        List<Coordination> path = piece.resolvePath(moveContext);
        List<Piece> piecesOnPath = path.stream()
                .map(board::get)
                .filter(pathPiece -> !pathPiece.isEmpty())
                .toList();
        piece.validatePath(piecesOnPath);
    }

    private void validateRule(Piece piece, MoveContext moveContext) {
        piece.validateRule(moveContext);
    }
}
