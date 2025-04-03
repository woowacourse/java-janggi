package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public interface State {
    State movePiece(PieceType pieceType, Position source, Position destination);

    PieceColor getTurnColor();

    boolean isFinished();

    double getRedTeamScore();

    double getBlueTeamScore();

    PieceColor determineWinner();

    Board getBoard();

    State startGame(Board board, PieceColor pieceColor);
}
