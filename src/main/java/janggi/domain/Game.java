package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceAttribute;
import janggi.domain.piece.PieceType;
import janggi.domain.turn.PlayerTurn;
import janggi.domain.turn.TurnState;
import janggi.dto.BoardDto;
import janggi.initializer.BoardInitializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private PlayerTurn playerTurn;

    public List<PieceInitInfo> init(Arrangement choArrangement, Arrangement hanArrangement) {
        Map<Position, Piece> initBoard = BoardInitializer.createBoard(choArrangement, hanArrangement);
        Board board = Board.from(initBoard);

        this.playerTurn = PlayerTurn.init(board);
        return getPieceInitInfo(initBoard);
    }

    public void init(List<PieceInitInfo> pieceInitInfos, Side side, int turn) {
        Map<Position, Piece> initBoard = BoardInitializer.createBoard(pieceInitInfos);
        Board board = Board.from(initBoard);

        this.playerTurn = PlayerTurn.from(board, turn, side);
    }

    public PieceAttribute move(Position start, Position end) {
        TurnState turnState = playerTurn.move(start, end);
        this.playerTurn = turnState.playerTurn();
        return turnState.movedPiece();
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

    public Integer getCurrentTurn() {
        return playerTurn.getCurrentTurn();
    }

    public SideScore getCurrentSideScore() {
        return playerTurn.getCurrentScore();
    }

    public Side getWinnerSide() {
       return playerTurn.getWinnerSide();
    }

    private List<PieceInitInfo> getPieceInitInfo(Map<Position, Piece> board) {
        List<PieceInitInfo> pieceInitInfos = new ArrayList<>();
        board.forEach(((position, piece) -> {
            if(!piece.isEqualPieceType(PieceType.NONE)) {
                pieceInitInfos.add(piece.getPieceInitInfo(position));
            }
        }));
        return pieceInitInfos;
    }
}
