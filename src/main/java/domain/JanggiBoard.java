package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Gung;
import domain.piece.Piece;
import domain.piece.Po;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiBoard {

    private final Map<Position, Piece> board;

    public JanggiBoard(BoardGenerator boardGenerator) {
        this.board = boardGenerator.generateBoard();
    }

    public void move(Position startPosition, Position targetPosition) {
        Piece selectedPiece = findSelectedPiece(startPosition);
        Optional<Piece> targetPiece = findPiece(targetPosition);
        List<Position> path = selectedPiece.calculatePath(startPosition, targetPosition);
        validatePath(path, selectedPiece, targetPiece);

        board.remove(startPosition);
        board.put(targetPosition, selectedPiece);
    }

    public Piece findSelectedPiece(Position startPosition) {
        return findPiece(startPosition)
                .orElseThrow(() -> new IllegalArgumentException("기물이 존재하지 않는 위치입니다."));
    }

    private void validatePath(List<Position> path, Piece selectedPiece, Optional<Piece> targetPiece) {
        if (selectedPiece instanceof Po) {
            validatePoRule(path, targetPiece);
        }
        if (!(selectedPiece instanceof Po)) {
            validateEmptyPath(path);
        }
        validateSameTeamAttack(selectedPiece, targetPiece);
    }

    private void validateSameTeamAttack(Piece selectedPiece, Optional<Piece> optionalTargetPiece) {
        if (optionalTargetPiece.isPresent() && selectedPiece.isTeam(optionalTargetPiece.get())) {
            throw new IllegalArgumentException("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
        }
    }

    private void validatePoRule(List<Position> path, Optional<Piece> optionalTargetPiece) {
        validateJumpOnePiece(path);
        validateJumpPo(path);
        validateAttackPo(optionalTargetPiece);
    }

    private void validateJumpOnePiece(List<Position> path) {
        int pieceCountInPath = (int) path.stream().filter(pos -> findPiece(pos).isPresent()).count();
        if (pieceCountInPath != 1) {
            throw new IllegalArgumentException("포는 다른 말 하나를 뛰어넘어야 합니다.");
        }
    }

    private void validateJumpPo(List<Position> path) {
        boolean jumpPo = path.stream()
                .map(this::findPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .anyMatch(piece -> piece instanceof Po);
        if (jumpPo) {
            throw new IllegalArgumentException("포는 포끼리 건너뛸 수 없습니다.");
        }
    }

    private void validateAttackPo(Optional<Piece> optionalTargetPiece) {
        if (optionalTargetPiece.isPresent() && optionalTargetPiece.get() instanceof Po) {
            throw new IllegalArgumentException("포는 포끼리 잡을 수 없습니다");
        }
    }

    private void validateEmptyPath(List<Position> path) {
        boolean isEmptyPath = path.stream().allMatch(position -> findPiece(position).isEmpty());
        if (!isEmptyPath) {
            throw new IllegalArgumentException("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
        }
    }

    public boolean existGung(Team team) {
        return board.values().stream().anyMatch(piece -> piece instanceof Gung && piece.isTeam(team));
    }

    public Optional<Piece> findPiece(Position startPosition) {
        return Optional.ofNullable(board.get(startPosition));
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }
}
