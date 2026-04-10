package dto;

import domain.piece.Team;

import java.util.List;

public record GameDto(Team turn, double choScore, double hanScore, List<PieceDto> pieces) {}
