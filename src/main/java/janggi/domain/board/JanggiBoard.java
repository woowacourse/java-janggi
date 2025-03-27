package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;
import janggi.domain.piece.generator.ChoPieceGenerator;
import janggi.domain.piece.generator.HanPieceGenerator;
import janggi.domain.piece.generator.KnightElephantSetting;
import janggi.domain.piece.pieces.Pieces;
import java.util.List;
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

        placedPieces = Pieces.from(Stream.concat(hanPieces.stream(), choPieces.stream()).collect(Collectors.toList()));
    }

    public void move(int x, int y, int destinationX, int destinationY) {
        validateSamePosition(x, y, destinationX, destinationY);
        Piece sourcePiece = placedPieces.findExistingByPosition(x, y);
        Pieces map = placedPieces.getMapWithoutPosition(x, y);

        sourcePiece.move(map, destinationX, destinationY);
        placedPieces.removeByPosition(x, y);
        placedPieces.put(sourcePiece);
    }

    private void validateSamePosition(int x, int y, int destinationX, int destinationY) {
        if (x == destinationX && y == destinationY) {
            throw new IllegalArgumentException("현재 위치로 이동할 수 없습니다.");
        }
    }

    public Pieces getPlacedPieces() {
        return placedPieces.clone();
    }

    public boolean isEnd() {
        return placedPieces.countByPieceType(PieceType.KING) != Side.values().length;
    }

    public Side getWinner() {
        if (!isEnd()) {
            throw new IllegalStateException("게임이 종료되지 않았습니다.");
        }
        return placedPieces.findAllByPieceType(PieceType.KING).stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("게임이 종료되지 않았습니다."));
    }
}
