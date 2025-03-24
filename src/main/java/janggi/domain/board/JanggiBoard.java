package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.gererator.ChoPieceGenerator;
import janggi.domain.piece.gererator.HanPieceGenerator;
import janggi.domain.piece.gererator.KnightElephantSetting;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JanggiBoard {

    private final Pieces placedPieces;

    public JanggiBoard(
        HanPieceGenerator hanPieceGenerator,
        ChoPieceGenerator choPieceGenerator,
        KnightElephantSetting hanKnightElephantSetting,
        KnightElephantSetting choKnightElephantSetting
    ) {
        List<Piece> hanPieces = hanPieceGenerator.generate(hanKnightElephantSetting);
        List<Piece> choPieces = choPieceGenerator.generate(choKnightElephantSetting);

        placedPieces = new Pieces(Stream.concat(
            hanPieces.stream(),
            choPieces.stream()
        ).collect(Collectors.toMap(Piece::getPosition, piece -> piece))
        );
    }

    public void move(int x, int y, int destinationX, int destinationY) {
        Position source = new Position(x, y);
        Position destination = new Position(destinationX, destinationY);
        Piece sourcePiece = placedPieces.findExistingByPosition(source);
        Pieces map = new Pieces(placedPieces);
        map.removeByPosition(source);

        sourcePiece.move(map, destinationX, destinationY);
        placedPieces.removeByPosition(source);
        placedPieces.put(destination, sourcePiece);
    }

    public Map<Position, Piece> getPlacedPieces() {
        return placedPieces.getValues();
    }

    public boolean isEnd() {
        return placedPieces.getValues().values().stream()
            .filter(value -> value.getPieceType() == PieceType.KING)
            .count() != 2;
    }

    public Side getWinner() {
        return placedPieces.getValues().values().stream()
            .filter(value -> value.getPieceType() == PieceType.KING)
            .map(Piece::getSide)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("게임이 종료되지 않았습니다."));
    }
}
