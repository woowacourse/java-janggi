package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
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
    private Turn turn;

    public JanggiBoard(
        HanPieceGenerator hanPieceGenerator,
        ChoPieceGenerator choPieceGenerator,
        KnightElephantSetting hanKnightElephantSetting,
        KnightElephantSetting choKnightElephantSetting
    ) {
        List<Piece> hanPieces = hanPieceGenerator.generate(hanKnightElephantSetting);
        List<Piece> choPieces = choPieceGenerator.generate(choKnightElephantSetting);

        placedPieces = Pieces.from(Stream.concat(hanPieces.stream(), choPieces.stream()).collect(Collectors.toList()));
        turn = Turn.start();
    }

    public JanggiBoard(Pieces placedPieces) {
        this.placedPieces = placedPieces;
        turn = Turn.start();
    }

    public void move(Position source, Position destination) {
        Piece sourcePiece = placedPieces.findExistingByPosition(source);
        validateTurn(sourcePiece);
        validateSamePosition(source, destination);
        Pieces map = placedPieces.getMapWithoutPosition(source);

        sourcePiece.move(map, destination);
        placedPieces.removeByPosition(source);
        placedPieces.put(sourcePiece);
        turn = turn.next();
    }

    private void validateTurn(Piece piece) {
        if (!turn.isTurn(piece.getSide())) {
            throw new IllegalArgumentException("현재 당신의 턴이 아닙니다.");
        }
    }

    private void validateSamePosition(Position source, Position destination) {
        if (source.equals(destination)) {
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
