package dto;

import domain.game.Game;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record GameData(
        String choPlayerName,
        String hanPlayerName,
        String currentTeam,
        List<PieceData> caughtPieces,
        Set<BoardPieceData> boardPieces
) {
    public static GameData from(Game game) {
        return new GameData(
                game.getChoPlayerName(),
                game.getHanPlayerName(),
                game.getCurrentTeam().name(),
                toPieceData(game.getCaughtPieces()),
                toBoardPieceData(game.getBoardMap())
        );
    }

    private static List<PieceData> toPieceData(List<Piece> caughtPieces) {
        return caughtPieces.stream()
                .map(PieceData::from)
                .toList();
    }

    private static Set<BoardPieceData> toBoardPieceData(Map<Position, Piece> boardMap) {
        return boardMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isNone())
                .map(entry -> BoardPieceData.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }
}
