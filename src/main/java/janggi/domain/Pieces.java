package janggi.domain;

import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import janggi.domain.team.TeamType;
import janggi.dto.BoardSpot;
import janggi.dto.BoardSpots;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Pieces {

    private final Map<Position, Piece> value;

    private Pieces(Map<Position, Piece> value) {
        this.value = value;
    }

    public Pieces move(Position piecePosition, Position targetPosition) {
        Piece piece = value.get(piecePosition);
        Map<Position, Piece> updatedValue = new HashMap<>(value);
        updatedValue.remove(piecePosition);
        updatedValue.put(targetPosition, piece);
        return new Pieces(updatedValue);
    }

    public Pieces remove(Position position) {
        Map<Position, Piece> updatedValue = new HashMap<>(value);
        updatedValue.remove(position);
        return new Pieces(updatedValue);
    }

    public static Pieces create(TeamType teamType) {
        if (teamType == TeamType.HAN) {
            return createHan();
        }
        return createChu();
    }

    private static Pieces createHan() {
        Map<Position, Piece> pieces = new HashMap<>();
        createChas(pieces, 10, TeamType.HAN);
        createMas(pieces, 10, TeamType.HAN);
        createSangs(pieces, 10, TeamType.HAN);
        createSas(pieces, 10, TeamType.HAN);
        createGung(pieces, 9, TeamType.HAN);
        createPos(pieces, 8, TeamType.HAN);
        createJols(pieces, 7, TeamType.HAN);
        return new Pieces(pieces);
    }

    private static Pieces createChu() {
        Map<Position, Piece> pieces = new HashMap<>();
        createChas(pieces, 1, TeamType.CHU);
        createMas(pieces, 1, TeamType.CHU);
        createSangs(pieces, 1, TeamType.CHU);
        createSas(pieces, 1, TeamType.CHU);
        createGung(pieces, 2, TeamType.CHU);
        createPos(pieces, 3, TeamType.CHU);
        createJols(pieces, 4, TeamType.CHU);
        return new Pieces(pieces);
    }

    public BoardSpots makeSnapShot() {
        Map<Position, BoardSpot> snapShot = new HashMap<>();
        for (Map.Entry<Position, Piece> entry : value.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            snapShot.put(position, new BoardSpot(position, piece.nickname(), piece.getTeamType()));
        }
        return new BoardSpots(snapShot);
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(value.get(position));
    }

    public boolean hasPieceType(PieceType pieceType) {
        return value.values().stream()
            .anyMatch(piece -> piece.getPieceType() == pieceType);
    }

    private static void createChas(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(1, indexY), new Cha(teamType));
        pieces.put(new Position(9, indexY), new Cha(teamType));
    }

    private static void createMas(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(2, indexY), new Ma(teamType));
        pieces.put(new Position(8, indexY), new Ma(teamType));
    }

    private static void createSangs(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(7, indexY), new Sang(teamType));
        pieces.put(new Position(10 - 7, indexY), new Sang(teamType));
    }

    private static void createSas(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(4, indexY), new Sa(teamType));
        pieces.put(new Position(10 - 4, indexY), new Sa(teamType));
    }

    private static void createGung(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(5, indexY), new Gung(teamType));
    }

    private static void createPos(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        pieces.put(new Position(2, indexY), new Po(teamType));
        pieces.put(new Position(10 - 2, indexY), new Po(teamType));
    }

    private static void createJols(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        for (int i = 1; i < 10; i += 2) {
            pieces.put(new Position(i, indexY), new Jol(teamType));
        }
    }

    public int sumScore() {
        return value.values().stream()
            .mapToInt(Piece::getScore)
            .sum();
    }
}
