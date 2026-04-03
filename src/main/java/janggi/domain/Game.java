package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceAttribute;
import janggi.domain.piece.PieceType;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.HanTurn;
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
        int hanScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.HAN))
                .mapToInt(Piece::getPieceScore)
                .sum();
        int choScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.CHO))
                .mapToInt(Piece::getPieceScore)
                .sum();

        this.playerTurn = new ChoTurn(new Board(initBoard, hanScore, choScore), 1);
        return getPieceInitInfo(initBoard);
    }

    public void init(List<PieceInitInfo> pieceInitInfos, Side side, int turn) {
        Map<Position, Piece> initBoard = BoardInitializer.createBoard(pieceInitInfos);
        int hanScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.HAN))
                .mapToInt(Piece::getPieceScore)
                .sum();
        int choScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.CHO))
                .mapToInt(Piece::getPieceScore)
                .sum();

        initTurn(new Board(initBoard, hanScore, choScore), side, turn);
    }

    public MoveResult move(Position start, Position end) {
        TurnState turnState = playerTurn.move(start, end);
        playerTurn = turnState.playerTurn();
        return new MoveResult(turnState.turnAttribute(), turnState.movedPiece());
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

    private void initTurn(Board board, Side side, int turn) {
        if(side.equals(Side.CHO)) {
            this.playerTurn = new ChoTurn(board, turn);
            return;
        }
        this.playerTurn = new HanTurn(board, turn);
    }
}
