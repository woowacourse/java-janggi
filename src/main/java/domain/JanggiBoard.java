package domain;

import domain.boardgenerator.JanggiBoardGenerator;
import domain.dao.PieceDao;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.Optional;

public class JanggiBoard {

    private final PieceDao pieceDao;

    private JanggiBoard(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    public static JanggiBoard of(PieceDao pieceDao) {
        return new JanggiBoard(pieceDao);
    }

    public static JanggiBoard init(PieceDao pieceDao) {
        pieceDao.addAll(new JanggiBoardGenerator().generateBoard());
        return new JanggiBoard(pieceDao);
    }

    public void move(final Position startPosition, final Position targetPosition) {
        Piece selectedPiece = findSelectedPiece(startPosition);
        Optional<Piece> targetPiece = findPiece(targetPosition);
        List<Position> path = selectedPiece.calculatePath(startPosition, targetPosition);
        validatePath(path, selectedPiece, targetPiece);

        targetPiece.ifPresent((piece) -> pieceDao.removeByPosition(targetPosition));
        pieceDao.changePosition(startPosition, targetPosition);
        selectedPiece.moveTo(targetPosition);
    }

    public int calculateTeamScore(Team team) {
        return pieceDao.findAll().stream().filter(piece -> piece.isTeam(team)).mapToInt(Piece::getScore).sum();
    }

    public Piece findSelectedPiece(Position startPosition) {
        return findPiece(startPosition)
                .orElseThrow(() -> new IllegalArgumentException("기물이 존재하지 않는 위치입니다."));
    }

    private void validatePath(List<Position> path, Piece selectedPiece, Optional<Piece> targetPiece) {
        if (selectedPiece.isType(PieceType.PO)) {
            validatePoRule(path, targetPiece);
        }
        if (!(selectedPiece.isType(PieceType.PO))) {
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
                .anyMatch(piece -> piece.isType(PieceType.PO));
        if (jumpPo) {
            throw new IllegalArgumentException("포는 포끼리 건너뛸 수 없습니다.");
        }
    }

    private void validateAttackPo(Optional<Piece> optionalTargetPiece) {
        if (optionalTargetPiece.isPresent() && optionalTargetPiece.get().isType(PieceType.PO)) {
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
        return pieceDao.findAll().stream().anyMatch(piece -> piece.isType(PieceType.GUNG) && piece.isTeam(team));
    }

    public Optional<Piece> findPiece(Position position) {
        return pieceDao.findByPosition(position);
    }
}
