package domain.board;

import static domain.piece.Team.CHO;
import static domain.piece.Team.HAN;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Scholar;
import domain.piece.Score;
import domain.piece.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private static final long REQUIRED_ALIVE_COUNT = 2;

    private final Map<BoardLocation, Piece> pieces;

    public Board(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece getByLocationOrThrow(BoardLocation current) {
        if (pieces.containsKey(current)) {
            return pieces.get(current);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다.");
    }

    public Optional<Piece> findByLocation(BoardLocation current) {
        return Optional.ofNullable(pieces.get(current));
    }

    public void occupy(BoardLocation current, BoardLocation destination) {
        Piece piece = pieces.remove(current);
        pieces.put(destination, piece);
    }

    public List<Piece> extractPathPiece(List<BoardLocation> allPath) {
        return allPath.stream()
                .filter(pieces::containsKey)
                .map(pieces::get)
                .toList();
    }

    public Score calculateScoreByTeam(Team team) {
        return pieces.values().stream()
                .filter(piece -> piece.isEqualTeam(team))
                .map(Piece::getScore)
                .reduce(Score::plus)
                .orElse(Score.ZERO);
    }

    public boolean isGameStopped() {
        long requiredAliveCount = pieces.values().stream()
                .filter(Piece::isStoppedGameIfDie)
                .count();

        return requiredAliveCount != REQUIRED_ALIVE_COUNT;
    }

    public Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }

    public static Board createWithPieces(Map<BoardLocation, Piece> placements) {
        Map<BoardLocation, Piece> pieces = new HashMap<>(placements);

        pieces.put(new BoardLocation(1, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(4, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(6, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(9, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(5, 2), King.createByTeam(HAN));
        pieces.put(new BoardLocation(2, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(8, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(1, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(3, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(5, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(7, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(9, 4), new Pawn(HAN));

        pieces.put(new BoardLocation(1, 10), new Chariot(CHO));
        pieces.put(new BoardLocation(4, 10), new Scholar(CHO));
        pieces.put(new BoardLocation(6, 10), new Scholar(CHO));
        pieces.put(new BoardLocation(9, 10), new Chariot(CHO));
        pieces.put(new BoardLocation(5, 9), King.createByTeam(CHO));
        pieces.put(new BoardLocation(2, 8), new Cannon(CHO));
        pieces.put(new BoardLocation(8, 8), new Cannon(CHO));
        pieces.put(new BoardLocation(1, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(3, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(5, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(7, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(9, 7), new Pawn(CHO));
        return new Board(pieces);
    }
}
