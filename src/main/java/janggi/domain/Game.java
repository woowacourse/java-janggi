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
    private static final String INVALID_TURN = "게임이 이미 끝나서 턴을 가져올 수 없습니다.";

    private PlayerTurn playerTurn;

    public List<PieceInitInfo> init(Arrangement choArrangement, Arrangement hanArrangement) {
        Map<Position, Piece> initBoard = BoardInitializer.createBoard(choArrangement, hanArrangement);
        Board board = getBoard(initBoard);

        this.playerTurn = PlayerTurn.init(board, 0);
        return getPieceInitInfo(initBoard);
    }

    public void init(List<PieceInitInfo> pieceInitInfos, Side side, int turn) {
        Map<Position, Piece> initBoard = BoardInitializer.createBoard(pieceInitInfos);
        Board board = getBoard(initBoard);

        this.playerTurn = PlayerTurn.from(board, turn, side);
    }

    public PieceAttribute move(Position start, Position end) {
        TurnState turnState = playerTurn.move(start, end);
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

    private Board getBoard(Map<Position, Piece> board) {
        int hanScore = calculateScore(board, Side.HAN);
        int choScore = calculateScore(board, Side.CHO);

        return new Board(board, hanScore, choScore);
    }

    private int calculateScore(Map<Position, Piece> initBoard, Side side) {
        return initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(side))
                .mapToInt(Piece::getPieceScore)
                .sum();
    }
}
