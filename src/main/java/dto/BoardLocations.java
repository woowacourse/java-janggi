package dto;

import domain.board.Board;
import domain.board.Point;
import domain.pieces.Piece;
import domain.player.Player;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record BoardLocations(List<BoardLocation> locations) implements Iterable<BoardLocation> {
    public BoardLocations(final Map<Point, Piece> board) {
        this(convertToLocations(board));
    }

    @Override
    public Iterator<BoardLocation> iterator() {
        return locations.iterator();
    }

    public Board convertToBoard(final List<Player> players) {
        final Map<Point, Piece> boardLocations = locations.stream()
                .collect(Collectors.toMap(
                        BoardLocation::point,
                        location -> getPiece(players, location)
                ));
        return new Board(boardLocations);
    }

    private static List<BoardLocation> convertToLocations(final Map<Point, Piece> board) {
        return board.entrySet().stream()
                .map(entry -> new BoardLocation(entry.getKey(),
                        entry.getValue().getType(),
                        entry.getValue().getPlayerId()))
                .toList();
    }

    private Piece getPiece(final List<Player> players, final BoardLocation location) {
        final Player player = players.stream()
                .filter(p -> p.getId() == location.playerId())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "플레이어 정보를 찾을 수 없습니다: " + location.playerId()));
        return location.piece().apply(player);
    }
}
