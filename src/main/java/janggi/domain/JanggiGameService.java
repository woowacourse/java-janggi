package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.Chu;
import janggi.domain.side.Han;
import janggi.domain.side.TeamType;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import janggi.dto.TurnDto;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class JanggiGameService {

    private final GameService gameService;
    private final TurnService turnService;
    private final PieceService pieceService;

    public JanggiGameService(GameService gameService, TurnService turnService, PieceService pieceService) {
        this.gameService = gameService;
        this.turnService = turnService;
        this.pieceService = pieceService;
    }

    public JanggiGame initializeJanggiGame(Consumer<List<GameDto>> printResumeNotice, Runnable printResumeGameNotice, Supplier<String> readLine) {
        List<GameDto> inProgressGames = gameService.findInProgressGames();
        if (!inProgressGames.isEmpty()) {
            printResumeNotice.accept(inProgressGames);
            String inputResumeCommand = readLine.get();
            ResumeCommand resumeCommand = new ResumeCommand(inputResumeCommand);
            if (resumeCommand.isResume()) {
                printResumeGameNotice.run();
                String inputGameId = readLine.get();
                return loadPreviousJanggiGame(GameDto.convertToIntId(inputGameId));
            }
        }
        return JanggiGame.createInitialJanggiGame();
    }

    private JanggiGame loadPreviousJanggiGame(Long gameId) {
        TurnDto turnDto = turnService.findLastTurnByGameId(gameId);

        List<PieceDto> pieceDtos = pieceService.findPiecesByTurnId(turnDto.id());

        Map<Position, Piece> chuPieces = pieceService.getPiecesByTeamType(pieceDtos, TeamType.CHU);
        Map<Position, Piece> hanPieces = pieceService.getPiecesByTeamType(pieceDtos, TeamType.HAN);

        Chu chu = Chu.loadLastChu(chuPieces);
        Han han = Han.loadLastHan(hanPieces);

        Board board = Board.loadPreviousBoard(chu, han);

        Turn turn = Turn.loadPreviousTurn(turnDto.currentTurnTeam(), board);

        return JanggiGame.loadPreviousJanggiGame(turn);
    }
}
