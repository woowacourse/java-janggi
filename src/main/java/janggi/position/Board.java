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
import repository.connection.ConnectDatabase;
import repository.connection.ConnectMysql;
import repository.dao.PieceDao;
import repository.dao.TurnDao;
import repository.converter.PieceConverter;
import repository.converter.TurnConverter;

public final class Board {
    private static final double AFTER_TURN_BONUS_SCORE = 1.5;
    private final Team currentTeam;
    private final Map<Position, Piece> pieceOfPosition;

    public static Board generate() {
        Initializer initializer = new Initializer();
        return new Board(initializer.setTurn(), initializer.generate());
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

    public Board move(Position source, Position destination, ConnectDatabase connectDatabase) {
        Piece piece = get(source);
        Set<Piece> pieces = toSet();
        Set<Position> positions = piece.possibleRoutes(this);

        validateCanMovePosition(destination, positions);

        movePiece(destination, pieces, piece);
        catchPiece(destination, piece, pieces);

        PieceDao pieceDao = new PieceDao(connectDatabase);
        pieceDao.deleteAll();

        Set<PieceConverter> pieceConverters = new HashSet<>();
        for (Piece pieceForDB : pieces) {
            pieceConverters.add(PieceConverter.toEntity(pieceForDB));
        }
        pieceDao.addAll(pieceConverters);

        TurnDao turnDao = new TurnDao(connectDatabase);
        Team nextTurn = nextTurn();
        turnDao.updateTurn(TurnConverter.toEntity(nextTurn));

        return new Board(pieces, nextTurn);
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

    public boolean catchKing() {
        final long count = toSet().stream()
                .filter(piece -> piece.type() == PieceType.KING)
                .count();

        return count == 1;
    }

    public Team findWinner() {
        return toSet().stream()
                .filter(piece -> piece.type() == PieceType.KING)
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

    public double hanScore() {
        return toSet().stream()
                .filter(piece -> piece.team() == Team.HAN)
                .mapToDouble(Piece::score)
                .sum() + AFTER_TURN_BONUS_SCORE;
    }

    public double choScore() {
        return toSet().stream()
                .filter(piece -> piece.team() == Team.CHO)
                .mapToDouble(Piece::score)
                .sum();
    }
}
