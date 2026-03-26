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

    public Board move(Coordinate coordinate, Team team) {
        validateMove(coordinate, team);
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

    private void validateMove(Coordinate coordinate, Team team) {
        Position from = coordinate.getStart();
        Position to = coordinate.getEnd();

        Piece piece = pieces.at(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 좌표에 기물이 없습니다: " + from));

        if (!piece.isOwnedBy(team)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표의 기물이 상대 기물입니다.");
        }

        Movement movement = MovementFactory.create(piece);
        List<Path> paths = movement.candidatePaths(from);

        MovementValidator validator = new MovementValidator(this);
        if (!validator.
                isValid(piece, paths, to)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 움직임입니다.");
        }
    }
}
