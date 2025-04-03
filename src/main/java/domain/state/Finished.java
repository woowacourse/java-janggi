package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public class Finished extends Started {

    public Finished(Board board) {
        super(board);
    }

    @Override
    public State movePiece(PieceType pieceType, Position source, Position destination) {
        throw new UnsupportedOperationException("게임이 끝난 후에는 움직일 수 없습니다.");
    }

    @Override
    public PieceColor getTurnColor() {
        throw new UnsupportedOperationException("게임이 종료되어 턴이 없습니다.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public PieceColor determineWinner() {
        if (board.isGeneralKilledByColor(PieceColor.RED)) {
            return PieceColor.BLUE;
        }
        if (board.isGeneralKilledByColor(PieceColor.BLUE)) {
            return PieceColor.RED;
        }
        if (getRedTeamScore() < getBlueTeamScore()) {
            return PieceColor.BLUE;
        }
        return PieceColor.RED;
    }
}
