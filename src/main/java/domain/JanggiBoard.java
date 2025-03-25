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
        Piece selectedPiece = findPiece(startPosition);
        Piece targetPiece = findPiece(targetPosition);

        List<Position> path = selectedPiece.calculatePath(startPosition, targetPosition);
        validatePath(path, selectedPiece, targetPiece);

        board.remove(startPosition);
        board.put(targetPosition, selectedPiece);
    }

    private void validateEmptyStartPosition(Position startPosition) {
        if (isPositionEmpty(startPosition)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }

    private void validatePath(List<Position> path, Piece selectedPiece, Piece targetPiece) {
        if (selectedPiece instanceof Po) {
            validatePoRule(path, targetPiece);
        }
        if (!(selectedPiece instanceof Po)) {
            validateEmptyPath(path);
        }
        validateSameTeamAttack(selectedPiece, targetPiece);
    }

    private void validateSameTeamAttack(Piece selectedPiece, Piece targetPiece) {
        if (targetPiece != null && selectedPiece.compareTeam(targetPiece)) {
            throw new IllegalArgumentException("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
        }
    }

    private void validatePoRule(List<Position> path, Piece targetPiece) {
        validateJumpOnePiece(path);
        validateJumpPo(path);
        validateAttackPo(targetPiece);
    }

    private void validateJumpOnePiece(List<Position> path) {
        int pieceCountInPath = (int) path.stream().filter(pos -> !isPositionEmpty(pos)).count();
        if (pieceCountInPath != 1) {
            throw new IllegalArgumentException("포는 다른 말 하나를 뛰어넘어야 합니다.");
        }
    }

    private void validateJumpPo(List<Position> path) {
        boolean jumpPo = path.stream().anyMatch(position -> findPiece(position) instanceof Po);
        if (jumpPo) {
            throw new IllegalArgumentException("포는 포끼리 건너뛸 수 없습니다.");
        }
    }

    private void validateAttackPo(Piece targetPiece) {
        if (targetPiece instanceof Po) {
            throw new IllegalArgumentException("포는 포끼리 잡을 수 없습니다");
        }
    }

    private void validateEmptyPath(List<Position> path) {
        boolean isEmptyPath = path.stream().allMatch(this::isPositionEmpty);
        if (!isEmptyPath) {
            throw new IllegalArgumentException("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
        }
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
