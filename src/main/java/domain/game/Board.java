package domain.game;

import domain.movement.Movement;
import domain.movement.MovementFactory;
import domain.movement.MovementValidator;
import domain.movement.Paths;
import domain.vo.Arrangements;
import domain.vo.Coordinate;
import java.util.Optional;

public class Board {
    private final Pieces pieces;

    public Board(Pieces pieces) {
        this.pieces = pieces;
    }

    public static Board of(Arrangements arrangements) {
        return new Board(Pieces.of(arrangements));
    }

    public Piece getPieceAt(Position position) {
        return pieces.getPieceAt(position);
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
        return pieces.getPieceAt(position) == null;
    }

    public boolean hasAnyPiece(Position position) {
        return !isEmpty(position);
    }

    public boolean hasFriendOf(Position position, Piece movingPiece) {
        Piece targetPiece = pieces.getPieceAt(position);
        return targetPiece != null && movingPiece.isSameTeamAs(targetPiece);
    }

    public boolean hasEnemyOf(Position position, Piece movingPiece) {
        Piece targetPiece = pieces.getPieceAt(position);
        return targetPiece != null && !movingPiece.isSameTeamAs(targetPiece);
    }

    private void validateMove(Coordinate coordinate, Turn turn) {
        Position from = coordinate.from();
        Position to = coordinate.to();

        Piece piece = pieces.at(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 없습니다: " + from));

        if (!turn.belongsTo(piece)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표의 기물이 상대 기물입니다.");
        }

        Movement movement = MovementFactory.create(piece);
        Paths paths = movement.candidatePaths(from);

        MovementValidator validator = new MovementValidator(this);
        if (!validator.isValid(piece, paths, to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 움직임입니다.");
        }
    }
}