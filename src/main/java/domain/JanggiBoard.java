package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JanggiBoard {

    private final List<Piece> pieces;

    private JanggiBoard(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public static JanggiBoard init(BoardGenerator boardGenerator) {
        List<Piece> pieces = boardGenerator.generateBoard();
        return new JanggiBoard(pieces);
    }

    public static JanggiBoard create(List<Piece> pieces) {
        return new JanggiBoard(pieces);
    }

    public void move(final Position startPosition, final Position targetPosition) {
        Piece selectedPiece = findSelectedPiece(startPosition);
        Optional<Piece> targetPiece = findPiece(targetPosition);
        List<Position> path = selectedPiece.calculatePath(startPosition, targetPosition);
        List<Piece> piecesInPath = findPiecesInPath(path);
        selectedPiece.applyRule(path, piecesInPath, targetPiece);

        targetPiece.ifPresent((piece) -> pieces.remove(targetPiece.get()));
        selectedPiece.changePosition(targetPosition);
    }

    private List<Piece> findPiecesInPath(List<Position> path) {
        return path.stream()
                .map(this::findPiece)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public int calculateTeamScore(Team team) {
        return pieces.stream().filter(piece -> piece.isTeam(team)).mapToInt(Piece::getScore).sum();
    }

    public Piece findSelectedPiece(Position startPosition) {
        return findPiece(startPosition)
                .orElseThrow(() -> new IllegalArgumentException("기물이 존재하지 않는 위치입니다."));
    }

    public boolean existGung(Team team) {
        return pieces.stream().anyMatch(piece -> piece.isType(PieceType.GUNG) && piece.isTeam(team));
    }

    public Optional<Piece> findPiece(Position position) {
        return pieces.stream().filter(piece -> piece.isSamePosition(position)).findFirst();
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JanggiBoard that = (JanggiBoard) o;
        return Objects.equals(pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieces);
    }

    @Override
    public String toString() {
        return "JanggiBoard{" +
                "pieces=" + pieces +
                '}';
    }
}
