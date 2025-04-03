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
        List<Piece> piecesInPath = findPiecesInPath(path);
        selectedPiece.applyRule(path, piecesInPath, targetPiece);

        targetPiece.ifPresent((piece) -> pieceDao.removeByPosition(targetPosition));
        pieceDao.changePosition(startPosition, targetPosition);
        selectedPiece.moveTo(targetPosition);
    }

    private List<Piece> findPiecesInPath(List<Position> path) {
        return path.stream()
                .map(this::findPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public int calculateTeamScore(Team team) {
        return pieceDao.findAll().stream().filter(piece -> piece.isTeam(team)).mapToInt(Piece::getScore).sum();
    }

    public Piece findSelectedPiece(Position startPosition) {
        return findPiece(startPosition)
                .orElseThrow(() -> new IllegalArgumentException("기물이 존재하지 않는 위치입니다."));
    }

    public boolean existGung(Team team) {
        return pieceDao.findAll().stream().anyMatch(piece -> piece.isType(PieceType.GUNG) && piece.isTeam(team));
    }

    public Optional<Piece> findPiece(Position position) {
        return pieceDao.findByPosition(position);
    }
}
