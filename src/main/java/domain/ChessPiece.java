package domain;

import java.nio.file.Path;
import java.util.List;

public abstract class ChessPiece {

    private final ChessPieceType chessPieceType;
    private final ChessTeam chessTeam;

    protected ChessPiece(final ChessPieceType chessPieceType, final ChessTeam chessTeam) {
        this.chessPieceType = chessPieceType;
        this.chessTeam = chessTeam;
    }

    public abstract List<Path> getAvailablePaths();


}
