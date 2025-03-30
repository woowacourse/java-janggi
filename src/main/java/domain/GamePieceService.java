package domain;

import domain.piece.PieceService;

public class GamePieceService {
    private final JanggiGame janggiGame;
    private final PieceService pieceService;

    public GamePieceService(JanggiGame janggiGame, PieceService pieceService) {
        this.janggiGame = janggiGame;
        this.pieceService = pieceService;
    }

    // 게임 시작 시 보드에 있는 모든 말을 DB에 저장
    public void startGame() {
        pieceService.saveAllPieces(janggiGame.getBoardState());
    }

    // 말 이동 시 DB 상태 업데이트
    public void movePiece(Position startPosition, Position targetPosition) {
        // 이동 처리
        janggiGame.move(startPosition, targetPosition);

        // 말 이동 후 상태 DB에 저장
        pieceService.saveAllPieces(janggiGame.getBoardState());
    }
}
