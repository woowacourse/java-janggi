package domain.state;

import domain.board.Board;
import domain.board.Position;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public class SetUp implements State {

    public static final String BEFORE_START_ERROR = "게임 시작 전입니다.";

    @Override
    public State movePiece(PieceType pieceType, Position source, Position destination) {
        throw new UnsupportedOperationException(BEFORE_START_ERROR);
    }

    @Override
    public PieceColor getTurnColor() {
        throw new UnsupportedOperationException(BEFORE_START_ERROR);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public double getRedTeamScore() {
        return 0;
    }

    @Override
    public double getBlueTeamScore() {
        return 0;
    }

    @Override
    public PieceColor determineWinner() {
        throw new UnsupportedOperationException(BEFORE_START_ERROR);
    }

    @Override
    public Board getBoard() {
        throw new UnsupportedOperationException(BEFORE_START_ERROR);
    }

    @Override
    public State startGame(Board board, PieceColor pieceColor) {
        if (pieceColor == PieceColor.BLUE) {
            return new BlueTurn(board);
        }
        return new RedTurn(board);
    }
}
