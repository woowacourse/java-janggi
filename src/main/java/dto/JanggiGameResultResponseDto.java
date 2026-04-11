package dto;

import domain.piece.Side;
import domain.janggigame.ScoreBoard;

public record JanggiGameResultResponseDto(
        String cho,
        String han,
        double choScore,
        double hanScore,
        String winSide
) {
    public static JanggiGameResultResponseDto from(ScoreBoard scoreBoard) {
        return new JanggiGameResultResponseDto(
                Side.CHO.getName(),
                Side.HAN.getName(),
                scoreBoard.getScoreBySide(Side.CHO),
                scoreBoard.getScoreBySide(Side.HAN),
                scoreBoard.determineSide().getName()
        );
    }
}
