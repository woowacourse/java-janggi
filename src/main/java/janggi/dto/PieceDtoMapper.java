package janggi.dto;

import janggi.domain.game.Game;
import janggi.domain.piece.Piece;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.Arrays;
import java.util.List;

public final class PieceDtoMapper {

    private static final List<Position> allPositions = Arrays.stream(Column.values())
            .flatMap(column -> Arrays.stream(Row.values())
                    .map(row -> new Position(column, row)))
            .toList();

    public static List<PieceDto> toPieceDtos(Game game) {
        return allPositions.stream()
                .filter(game::hasPieceAt)
                .map(position -> createPieceDto(game, position))
                .toList();
    }

    private static PieceDto createPieceDto(final Game game, final Position position) {
        Piece piece = game.getPieceAt(position);
        return new PieceDto(piece.type().name(),
                piece.team().name(),
                position.column().getValue(),
                position.row().getValue());
    }
}
