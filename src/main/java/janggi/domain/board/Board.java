package janggi.domain.board;

import janggi.domain.Score;
import janggi.domain.ScoreResult;
import janggi.domain.Team;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.PiecesOnPath;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.util.List;
import java.util.Map;

public class Board {

    private static final long GENERAL_COUNT = 2;

    private final Map<Position, Piece> pieces;

    private Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board from(Map<Position, Piece> pieces) {
        return new Board(pieces);
    }

    public Map<Position, Piece> move(Position from, Position to, Team currentTeam) {
        validate(from, to, currentTeam);
        Piece fromPiece = pieces.get(from);
        PiecesOnPath piecesOnPath = collectPath(fromPiece.getPath(from, to));
        fromPiece.canMove(piecesOnPath, pieces.get(to));
        movePiece(from, to, fromPiece);
        return showBoard();
    }

    public ScoreResult calculateScoreResult() {
        return new ScoreResult(
                calculateScore(Team.HAN),
                calculateScore(Team.CHO)
        );
    }

    private Score calculateScore(Team team) {
        double sum = pieces.values().stream()
                .filter(piece -> piece.isSame(team))
                .mapToInt(piece -> piece.getType().getScore())
                .sum();
        Score score = Score.of(sum);
        if (team == Team.HAN) {
            return score.applyHanBonus();
        }
        return score;
    }

    private PiecesOnPath collectPath(List<Position> positions) {
        return new PiecesOnPath(positions.stream()
                .map(pieces::get)
                .toList());
    }

    private void movePiece(Position from, Position to, Piece piece) {
        pieces.put(from, new EmptyPiece());
        pieces.put(to, piece);
    }

    public boolean isGeneralCaptured() {
        return pieces.values().stream()
                .filter(piece -> !piece.isEmptyPiece())
                .filter(piece -> piece.isSameType(PieceType.GENERAL))
                .count() < GENERAL_COUNT;
    }

    public Map<Position, Piece> showBoard() {
        return Map.copyOf(pieces);
    }

    private void validate(Position from, Position to, Team currentTeam) {
        validateNotEmpty(from);
        validateNotSamePosition(from, to);
        validateTurn(from, currentTeam);
    }

    private void validateNotEmpty(Position from) {
        if (pieces.get(from).isEmptyPiece()) {
            throw new IllegalArgumentException("[ERROR] 선택된 기물이 없습니다.");
        }
    }

    private void validateNotSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표와 도착 좌표는 같을 수 없습니다.");
        }
    }

    private void validateTurn(Position from, Team currentTeam) {
        if (!pieces.get(from).isSame(currentTeam)) {
            throw new IllegalArgumentException("[ERROR] 현재 턴의 기물만 이동할 수 있습니다.");
        }
    }
}
