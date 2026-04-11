package team.janggi.repository.dto;

import team.janggi.domain.Team;
import team.janggi.domain.board.BoardStateReader;

public record BoardViewDTO(
        Team currentTurn,
        double choScore,
        double hanScore,
        BoardStateReader boardStateReader
) {
}
