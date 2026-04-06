package janggi.service.dto;

import janggi.model.Team;

public record GameOptionResponse(
        Long gameId,
        Team currentTurn
) {}
