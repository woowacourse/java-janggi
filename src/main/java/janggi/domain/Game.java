package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.PlayerTurn;
import janggi.dto.BoardDto;
import janggi.initializer.BoardInitializer;
import java.util.Map;

public class Game {
    private static final String INVALID_WINNER_SIDE = "잘못된 승자 진영입니다.";

    private PlayerTurn playerTurn;

    public Game(Arrangement choArrangement, Arrangement hanArrangement) {
        Map<Position, Piece> initBoard = BoardInitializer.createBoard(choArrangement, hanArrangement);
        int hanScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.HAN))
                .mapToInt(Piece::getPieceScore)
                .sum();
        int choScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.CHO))
                .mapToInt(Piece::getPieceScore)
                .sum();

        this.playerTurn = new ChoTurn(new Board(initBoard, hanScore, choScore));
    }

    public void move(Position start, Position end) {
        playerTurn = playerTurn.move(start, end);
    }

    public boolean isFinished() {
        return playerTurn.isFinished();
    }

    public BoardDto getCurrentBoardDto() {
        return new BoardDto(playerTurn.getCurrentBoard());
    }

    public Side getCurrentSide() {
        return playerTurn.getCurrentSide();
    }

    public SideScore getCurrentSideScore() {
        return playerTurn.getCurrentScore();
    }

    public Side getWinnerSide() {
        Side winnerSide = playerTurn.getWinnerSide();
        if (winnerSide.equals(Side.EMPTY)) {
            throw new IllegalStateException(INVALID_WINNER_SIDE);
        }
        return winnerSide;
    }
}
