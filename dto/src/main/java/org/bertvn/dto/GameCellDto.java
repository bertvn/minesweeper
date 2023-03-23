package org.bertvn.dto;

import org.bertvn.common.GameCellState;

public record GameCellDto(boolean isBomb, GameCellState cellState, int bombCount) { }
