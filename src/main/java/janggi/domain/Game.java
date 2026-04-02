package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceAttribute;
import janggi.domain.turn.ChoTurn;
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

    public List<PieceInitInfo> init(List<PieceInitInfo> pieceInitInfos) {
        Map<Position, Piece> initBoard = BoardInitializer.createBoard(pieceInitInfos);
        int hanScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.HAN))
                .mapToInt(Piece::getPieceScore)
                .sum();
        int choScore = initBoard.values().stream()
                .filter(piece -> piece.isEqualSide(Side.CHO))
                .mapToInt(Piece::getPieceScore)
                .sum();

        this.playerTurn = new ChoTurn(new Board(initBoard, hanScore, choScore), 1); // 현재 side, turn 가져오기
        return getPieceInitInfo(initBoard);
    }

    public PieceAttribute move(Position start, Position end) {
        Side currentTurn = playerTurn.getCurrentSide();

        TurnState turnState = playerTurn.move(start, end);
        playerTurn = turnState.playerTurn();
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

    public SideScore getCurrentSideScore() {
        return playerTurn.getCurrentScore();
    }

    public Side getWinnerSide() {
        return playerTurn.getWinnerSide();
    }

    private List<PieceInitInfo> getPieceInitInfo(Map<Position, Piece> board) {
        List<PieceInitInfo> pieceInitInfos = new ArrayList<>();
        board.forEach(((position, piece) -> pieceInitInfos.add(piece.getPieceInitInfo(position))));
        return pieceInitInfos;
    }
}
