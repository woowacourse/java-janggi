package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Gung;
import domain.piece.Piece;
import domain.piece.Po;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private final Map<Position, Piece> board;

    public JanggiBoard(BoardGenerator boardGenerator) {
        this.board = boardGenerator.generateBoard();
    }

    public void move(Position startPosition, Position targetPosition) {
        validateEmptyStartPosition(startPosition);
        Piece startPiece = findPiece(startPosition);

        Piece targetPositionPiece = findPiece(targetPosition);
        List<Position> path = startPiece.calculatePath(startPosition, targetPosition);
        validateMovePiece(startPiece, path, targetPositionPiece);

        board.remove(startPosition);
        board.put(targetPosition, startPiece);
    }

    private void validateEmptyStartPosition(Position startPosition) {
        if (isPositionEmpty(startPosition)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }

    private void validateMovePiece(Piece startPiece, List<Position> path, Piece targetPositionPiece) {
        if (startPiece instanceof Po) {
            validateCanonMove(path, targetPositionPiece);
        }
        if (!(startPiece instanceof Po)) {
            validateNonCanonMove(path);
        }
        validateSameTeamAttack(startPiece, targetPositionPiece);
    }

    private void validateSameTeamAttack(Piece startPiece, Piece targetPositionPiece) {
        if (targetPositionPiece != null && startPiece.compareTeam(targetPositionPiece)) {
            throw new IllegalArgumentException("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
        }
    }

    private void validateCanonMove(List<Position> path, Piece targetPositionPiece) {
        validateJumpOtherPiece(path);
        path.forEach(this::validateJumpCanon);
        validateAttackCanon(targetPositionPiece);
    }

    private void validateAttackCanon(Piece targetPositionPiece) {
        if (targetPositionPiece instanceof Po) {
            throw new IllegalArgumentException("포는 포끼리 잡을 수 없습니다");
        }
    }

    private void validateJumpCanon(Position position) {
        if (findPiece(position) instanceof Po) {
            throw new IllegalArgumentException("포는 포끼리 건너뛸 수 없습니다.");
        }
    }

    private void validateJumpOtherPiece(List<Position> path) {
        if (countPieceInPath(path) != 1) {
            throw new IllegalArgumentException("포는 다른 말 하나를 뛰어넘어야 합니다.");
        }
    }

    private void validateNonCanonMove(List<Position> path) {
        path.forEach(this::validateIsEmptyPath);
    }

    private void validateIsEmptyPath(Position position) {
        if (!isPositionEmpty(position)) {
            throw new IllegalArgumentException("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
        }
    }

    private int countPieceInPath(List<Position> path) {
        return (int) path.stream().filter(pos -> !isPositionEmpty(pos)).count();
    }

    public boolean existGung(Team team) {
        return board.values().stream().anyMatch(piece -> piece instanceof Gung && piece.compareTeam(team));
    }

    public boolean isPositionEmpty(Position position) {
        return findPiece(position) == null;
    }

    public Piece findPiece(Position startPosition) {
        return board.get(startPosition);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
