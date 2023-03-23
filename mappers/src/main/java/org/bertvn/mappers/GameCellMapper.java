package org.bertvn.mappers;

import org.bertvn.domain.GameCell;
import org.bertvn.dto.GameCellDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameCellMapper {

    GameCellMapper INSTANCE = Mappers.getMapper(GameCellMapper.class);

    @Mapping(target = "isBomb", source = "bomb")
    GameCellDto toDto(GameCell gameCell);
}
