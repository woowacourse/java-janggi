package janggi.domain;

import janggi.domain.game.GameService;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceService;
import janggi.domain.team.Chu;
import janggi.domain.team.Han;
import janggi.domain.team.TeamType;
import janggi.domain.turn.Turn;
import janggi.domain.turn.TurnService;
import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import janggi.dto.TurnDto;

import java.util.ArrayList;
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
        long gameId = gameService.save();
        return JanggiGame.createInitialJanggiGame(gameId);
    }

    public void move(JanggiGame janggiGame, Position start, Position end) {
        Turn movedTurn = janggiGame.move(start, end);
        long turnId = turnService.save(movedTurn, janggiGame.getId());
        Turn savedTurn = Turn.savedTurn(turnId, movedTurn);
        janggiGame.addNewTurn(savedTurn);
        List<PieceDto> pieceDtos = getPieceDtos(savedTurn);
        pieceService.saveAll(pieceDtos);
    }

    public void updateGameStatusFinished(JanggiGame janggiGame) {
        gameService.updateGameStatusFinished(janggiGame);
    }

    private JanggiGame loadPreviousJanggiGame(Long gameId) {
        TurnDto turnDto = turnService.findLastTurnByGameId(gameId);

        List<PieceDto> pieceDtos = pieceService.findPiecesByTurnId(turnDto.id());

        Map<Position, Piece> chuPieces = pieceService.getPiecesByTeamType(pieceDtos, TeamType.CHU);
        Map<Position, Piece> hanPieces = pieceService.getPiecesByTeamType(pieceDtos, TeamType.HAN);

        Chu chu = Chu.loadLastChu(chuPieces);
        Han han = Han.loadLastHan(hanPieces);

        Board board = Board.loadPreviousBoard(chu, han);

        Turn turn = Turn.loadPreviousTurn(turnDto.id(), turnDto.currentTurnTeam(), board);

        return JanggiGame.loadPreviousJanggiGame(gameId, turn);
    }

    private List<PieceDto> getPieceDtos(Turn savedTurn) {
        Map<Position, Piece> allPieces = savedTurn.allPieces();
        List<PieceDto> pieceDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> pieceEntry : allPieces.entrySet()) {
            Position position = pieceEntry.getKey();
            Piece piece = pieceEntry.getValue();
            pieceDtos.add(PieceDto.from(savedTurn.getId(), position, piece));
        }
        return pieceDtos;
    }
}
