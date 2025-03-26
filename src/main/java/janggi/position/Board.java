package janggi.position;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.piece.normalPiece.Blank;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Board {
    private final Team currentTeam;
    private final Map<Position, Piece> pieceOfPosition;

    public static Board generate() {
        return new Board(new Initializer().generate());
    }

    public Board(Team team, Set<Piece> pieces) {
        currentTeam = team;
        pieceOfPosition = pieces.stream().collect(toMap(Piece::position, identity()));
    }

    public Board(Set<Piece> pieces) {
        currentTeam = Team.CHO;
        pieceOfPosition = pieces.stream().collect(toMap(Piece::position, identity()));
    }

    public Board(Set<Piece> pieces, Team team) {
        currentTeam = team;
        pieceOfPosition = pieces.stream().collect(toMap(Piece::position, identity()));
    }

    public Piece get(final Position position) {
        return pieceOfPosition.getOrDefault(position, new Blank(position));
    }

    public boolean isBlank(Position position) {
        return get(position).type() == PieceType.BLANK;
    }

    public boolean canMoveLast(Position position) {
        Piece piece = get(position);
        return (piece.type() == PieceType.BLANK) || piece.isDifferentTeam(currentTeam);
    }

    public boolean canMoveLastForCannon(Position position) {
        Piece piece = get(position);
        return (piece.type() != PieceType.CANNON) && piece.isDifferentTeam(currentTeam);
    }

    public void validateTeam(Team team) {
        if (team != currentTeam) {
            throw new IllegalArgumentException("[ERROR] 같은 팀 기물만 움직일 수 있습니다.");
        }
    }

    public boolean hasPieceWithoutCannon(Position target) {
        PieceType type = get(target).type();
        return type != PieceType.BLANK && type != PieceType.CANNON;
    }

    public Set<Piece> toSet() {
        return new HashSet<>(pieceOfPosition.values());
    }

    public Team nextTurn() {
        return Team.next(currentTeam);
    }

    public Board move(Position source, Position destination) {
        Piece piece = get(source);
        Set<Piece> pieces = toSet();
        Set<Position> positions = piece.possibleRoutes(this);

        validateCanMovePosition(destination, positions);

        movePiece(destination, pieces, piece);
        catchPiece(destination, piece, pieces);

        return new Board(pieces, nextTurn());
    }

    private void movePiece(Position destination, Set<Piece> pieces, Piece piece) {
        pieces.remove(piece);
        pieces.add(piece.move(currentTeam, destination));
    }

    private void catchPiece(Position destination, Piece piece, Set<Piece> pieces) {
        if (piece.isDifferentTeam(get(destination).team())) {
            pieces.remove(get(destination));
        }
    }

    private void validateCanMovePosition(Position destination, Set<Position> positions) {
        if (!positions.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 좌표입니다.");
        }
    }

    public boolean catchPalace() {
        final long count = toSet().stream()
                .filter(piece -> piece.type() == PieceType.PALACE)
                .count();

        return count == 1;
    }

    public Team findWinner() {
        return toSet().stream()
                .filter(piece -> piece.type() == PieceType.PALACE)
                .map(Piece::team)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    public Map<Position, Piece> pieceOfPosition() {
        return pieceOfPosition;
    }

    public Team currentTeam() {
        return currentTeam;
    }
}
