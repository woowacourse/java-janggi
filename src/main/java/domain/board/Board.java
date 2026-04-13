package domain.board;

import domain.movement.Movement;
import domain.movement.MovementFactory;
import domain.piece.Piece;
import domain.piece.Team;
import domain.setup.Arrangements;
import domain.setup.Coordinate;
import java.util.List;
import java.util.Optional;

public class Board implements BoardState {
    private final Pieces pieces;

    public Board(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Board of(Arrangements arrangements) {
        return new Board(Pieces.of(arrangements));
    }

    public Board move(Coordinate coordinate, Team currentTeam) {
        Position source = coordinate.source();
        Position target = coordinate.target();

        Piece sourcePiece = pieces.pieceAtOrThrow(source);
        validateSourcePieceOwnership(sourcePiece, currentTeam);
        validateTargetIsAvailable(coordinate);

        return new Board(pieces.move(source, target));
    }

    private void validateSourcePieceOwnership(Piece sourcePiece, Team team) {
        if (!sourcePiece.isOwnedBy(team)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표의 기물이 상대 기물입니다.");
        }
    }

    private void validateTargetIsAvailable(Coordinate coordinate) {
        Position source = coordinate.source();
        Position target = coordinate.target();

        List<Position> availableTargets = findAvailableTargetPositions(source);

        if (!availableTargets.contains(target)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 움직임입니다.");
        }
    }

    public List<Position> findAvailableTargetPositions(Position source) {
        Piece sourcePiece = pieces.pieceAtOrThrow(source);
        Movement movement = MovementFactory.create(sourcePiece);

        List<Position> allAvailableTargetPosition = movement.findReachablePositions(source, this);

        return allAvailableTargetPosition.stream()
                .filter(target -> pieces.canOccupy(target, sourcePiece))
                .toList();
    }

    public Piece pieceAt(Position position) {
        return pieces.pieceAtOrThrow(position);
    }

    public Optional<Piece> findPieceByPosition(Position position) {
        return pieces.pieceAt(position);
    }

    public boolean isEmpty(Position position) {
        return pieces.isEmpty(position);
    }

    public boolean hasGeneral(Team team) {
        return pieces.hasGeneral(team);
    }

    public Pieces snapshot() {
        return pieces.snapshot();
    }

}
