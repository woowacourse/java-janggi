package domain.position;

import domain.chesspiece.ChessPiece;
import domain.score.Score;
import domain.type.ChessTeam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessPiecePositions {

    private final List<ChessPiece> chessPieces;

    public ChessPiecePositions(final List<ChessPiece> chessPieces) {
        this.chessPieces = new ArrayList<>(chessPieces);
    }

    public static ChessPiecePositions from(final ChessPiecePositionsGenerator chessPiecePositionsGenerator) {
        return new ChessPiecePositions(chessPiecePositionsGenerator.generate());
    }

    public boolean existPieceByPosition(final ChessPosition position) {
        return chessPieces.stream()
                .anyMatch(chessPiece -> chessPiece.matchPosition(position));
    }

    public ChessPiece findPieceByPosition(final ChessPosition position) {
        return chessPieces.stream()
                .filter(chessPiece -> chessPiece.matchPosition(position))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에는 기물이 존재하지 않습니다."));
    }

    public void move(final ChessPiece chessPiece, final ChessPosition to) {
        chessPieces.remove(chessPiece);
        killChessPiece(to);
        chessPieces.add(chessPiece.from(to));
    }

    public Score calculateScoreByTeam(final ChessTeam team) {
        return chessPieces.stream()
                .filter(chessPiece -> chessPiece.getTeam() == team)
                .map(ChessPiece::getScore)
                .reduce(Score.initScore(team), Score::add);
    }

    public List<ChessPiece> getChessPieces() {
        return Collections.unmodifiableList(chessPieces);
    }

    private void killChessPiece(final ChessPosition to) {
        if (existPieceByPosition(to)) {
            chessPieces.remove(findPieceByPosition(to));
        }
    }
}
