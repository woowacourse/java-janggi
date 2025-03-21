package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.gererator.ChoPieceGenerator;
import janggi.domain.piece.gererator.HanPieceGenerator;
import janggi.domain.piece.gererator.KnightElephantSetting;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JanggiBoard {

    private final Map<Position, Piece> pieceMap;

    public JanggiBoard(
            HanPieceGenerator hanPieceGenerator,
            ChoPieceGenerator choPieceGenerator,
            KnightElephantSetting hanKnightElephantSetting,
            KnightElephantSetting choKnightElephantSetting
    ) {
        List<Piece> hanPieces = hanPieceGenerator.generate(hanKnightElephantSetting);
        List<Piece> choPieces = choPieceGenerator.generate(choKnightElephantSetting);

        pieceMap = Stream.concat(
                hanPieces.stream(),
                choPieces.stream()
        ).collect(Collectors.toMap(Piece::getPosition, piece -> piece));
    }

    public void move(int x, int y, int destinationX, int destinationY) {
        Position source = new Position(x, y);
        Position destination = new Position(destinationX, destinationY);
        List<Piece> existingPieces = new ArrayList<>(pieceMap.values().stream().toList());
        Piece sourcePiece = pieceMap.get(source);
        existingPieces.remove(sourcePiece);

        validatePieceExistence(source);

        sourcePiece.move(existingPieces, destinationX, destinationY);
        pieceMap.remove(source);
        pieceMap.put(destination, sourcePiece);
    }

    private void validatePieceExistence(Position source) {
        if (!pieceMap.containsKey(source)) {
            throw new IllegalArgumentException("해당 위치엔 기물이 존재하지 않습니다.");
        }
    }

    public Map<Position, Piece> getPieceMap() {
        return pieceMap;
    }

    public boolean isEnd() {
        return pieceMap.values().stream()
                .filter(value -> value.getClass() == King.class)
                .count() != 2;
    }

    public Side getWinner() {
        return pieceMap.values().stream()
                .filter(value -> value.getClass() == King.class)
                .map(Piece::getSide)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("게임이 종료되지 않았습니다."));
    }
}
