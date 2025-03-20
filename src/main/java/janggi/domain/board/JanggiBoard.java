package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.gererator.ChoPieceGenerator;
import janggi.domain.piece.gererator.HanPieceGenerator;
import janggi.domain.piece.gererator.KnightElephantSetting;
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

        validatePieceExistence(source);
    }

    private void validatePieceExistence(Position source) {
        if (!pieceMap.containsKey(source)) {
            throw new IllegalArgumentException("해당 위치엔 기물이 존재하지 않습니다.");
        }
    }
}
