package janggi.domain.gameState;

import janggi.domain.board.PlayingBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import janggi.domain.piece.PieceType;

public interface State {

    static State from(TeamColor teamColor, PlayingBoard playingBoard) {
        if(teamColor == TeamColor.RED) {
            return new RedTurn(playingBoard);
        }
        if(teamColor == TeamColor.BLUE) {
            return new BlueTurn(playingBoard);
        }
        throw new IllegalArgumentException("해당하는 정보로 State를 생성할 수 없습니다.");
    }
    State movePiece(PieceType pieceType, Position source, Position destination);

    TeamColor getColor();

    int getDestinationPieceScore(Position destination);

    boolean isFinished();

    PlayingBoard getPlayingBoard();
}
