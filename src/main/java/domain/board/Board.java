package domain.board;

import domain.game.Turn;
import domain.movement.Movement;
import domain.movement.MovementFactory;
import domain.movement.MovementValidator;
import domain.movement.Paths;
import domain.piece.Piece;
import domain.piece.Team;
import domain.setup.Arrangements;
import domain.setup.Coordinate;
import java.util.Map;
import java.util.Optional;

public class Board {
    private final Pieces pieces;

    public Board(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Board of(Arrangements arrangements) {
        return new Board(Pieces.of(arrangements));
    }

    public Board move(Coordinate coordinate, Turn turn) {
        validateMove(coordinate, turn);
        Position from = coordinate.from();
        Position to = coordinate.to();
        return new Board(pieces.move(from, to));
    }

    public Optional<Piece> pieceAt(Position position) {
        return pieces.at(position);
    }

    public boolean isEmpty(Position position) {
        return pieces.at(position).isEmpty();
    }

    public boolean hasAnyPiece(Position position) {
        return !isEmpty(position);
    }

    public boolean hasFriendOf(Position position, Piece movingPiece) {
        return pieces.at(position)
                .map(movingPiece::isSameTeamAs)
                .orElse(false);
    }

    public boolean hasEnemyOf(Position position, Piece movingPiece) {
        return pieces.at(position)
                .map(targetPiece -> !movingPiece.isSameTeamAs(targetPiece))
                .orElse(false);
    }

    public Position findGeneral(Team team) {
        return pieces.findGeneral(team);
    }

    public boolean hasGeneral(Team team) {
        return pieces.getAllPiecesOf(team).values().stream()
                .anyMatch(Piece::isGeneral);
    }

    public Map<Position, Piece> getAllPiecesOf(Team team) {
        return pieces.getAllPiecesOf(team);
    }

    public Board simulateMove(Position from, Position to) {
        return new Board(pieces.move(from, to));
    }

    public double calculateScore(Team team) {
        double base = getAllPiecesOf(team).values().stream()
                .mapToInt(Piece::score)
                .sum();
        return base + team.dumPoint();
    }

    public boolean hasInsufficientPieces(Team team) {
        return getAllPiecesOf(team).values().stream()
                .allMatch(piece -> piece.isGeneral() || piece.isGuard());
    }

    public Map<Position, Piece> getAllPieces() {
        return pieces.getAll();
    }

    public boolean isColumnClearBetween(Position hanGeneralPosition, Position choGeneralPosition) {
        Column column = hanGeneralPosition.column();
        int minRow = Math.min(hanGeneralPosition.row().ordinal(), choGeneralPosition.row().ordinal());
        int maxRow = Math.max(hanGeneralPosition.row().ordinal(), choGeneralPosition.row().ordinal());
        for (int row = minRow + 1; row < maxRow; row++) {
            if (hasAnyPiece(new Position(column, Row.values()[row]))) {
                return false;
            }
        }
        return true;
    }

    private void validateMove(Coordinate coordinate, Turn turn) {
        Position from = coordinate.from();
        Position to = coordinate.to();

        Piece piece = pieces.at(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 없습니다: " + from));

        if (!turn.belongsTo(piece)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표의 기물이 상대 기물입니다.");
        }

        if (from.equals(to) && piece.isGeneral()) {
            return;
        }

        if (piece.isPalacePiece()) {
            validatePalaceMove(from, to);
        }

        Movement movement = MovementFactory.create(piece);
        Paths paths = movement.candidatePaths(from);

        MovementValidator validator = new MovementValidator(this);
        if (!validator.isValid(piece, paths, to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 움직임입니다.");
        }
    }

    private void validatePalaceMove(Position from, Position to) {
        Palace palace = Palace.requirePalace(from);
        palace.validateContains(to);

        boolean isDiagonal = from.column() != to.column() && from.row() != to.row();
        if (isDiagonal) {
            palace.validateOnDiagonal(from);
            palace.validateOnDiagonal(to);
        }
    }
}
