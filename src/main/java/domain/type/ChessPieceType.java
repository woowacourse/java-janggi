package domain.type;

import domain.chesspiece.Cannon;
import domain.chesspiece.Chariot;
import domain.chesspiece.ChessPiece;
import domain.chesspiece.Elephant;
import domain.chesspiece.Guard;
import domain.chesspiece.Horse;
import domain.chesspiece.King;
import domain.chesspiece.Pawn;
import domain.position.ChessPosition;
import domain.score.Score;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

public enum ChessPieceType {

    CHARIOT(new Score(13), Chariot::new),
    HORSE(new Score(5), Horse::new),
    ELEPHANT(new Score(3), Elephant::new),
    CANNON(new Score(7), Cannon::new),
    GUARD(new Score(3), Guard::new),
    PAWN(new Score(2),Pawn::new),
    KING(new Score(100), King::new),
    ;

    private final Score score;
    private final BiFunction<ChessTeam, ChessPosition, ChessPiece> generate;


    ChessPieceType(Score score, final BiFunction<ChessTeam, ChessPosition, ChessPiece> generate) {
        this.score = score;
        this.generate = generate;
    }

    public static ChessPiece parseToChess(final String name, final ChessTeam team, final ChessPosition position) {
        return Arrays.stream(ChessPieceType.values())
                .filter(chessPieceType -> Objects.equals(chessPieceType.toString(), name))
                .findAny()
                .map(chessPieceType -> chessPieceType.generate.apply(team, position))
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 기물 타입 입니다."));
    }

    public Score getScore() {
        return score;
    }
}
