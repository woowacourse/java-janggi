package domain.game;

import domain.movement.Movement;
import domain.movement.MovementFactory;
import domain.movement.MovementValidator;
import domain.movement.Path;
import domain.vo.Arrangements;
import domain.vo.Coordinate;
import domain.vo.Team;
import java.util.List;
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

    public Board move(Coordinate coordinate) {
        validateMove(coordinate.getStart(), coordinate.getEnd());
        return new Board(pieces.move(coordinate.getStart(), coordinate.getEnd()));
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

    public boolean hasFriend(Position position, Team team) {
        Piece piece = pieces.getPieceAt(position);
        return piece != null && piece.getTeam() == team;
    }

    public boolean hasEnemy(Position position, Team team) {
        Piece piece = pieces.getPieceAt(position);
        return piece != null && piece.getTeam() != team;
    }

    private void validateMove(Position from, Position to) {
        Piece piece = pieces.at(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 없습니다: " + from));

        Movement movement = MovementFactory.create(piece);
        List<Path> paths = movement.candidatePaths(from);

        MovementValidator validator = new MovementValidator(this);
        if (!validator.
                isValid(piece, paths, to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 움직임입니다.");
        }
    }
}
