package com.commerzbank.VideoGameDatabase.mappers

import com.commerzbank.VideoGameDatabase.dto.GameDto
import com.commerzbank.VideoGameDatabase.dto.UpdateGameDto
import com.commerzbank.VideoGameDatabase.model.Game
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface GameMapper {
    companion object{
        val mapper: GameMapper
            get() = Mappers.getMapper(GameMapper::class.java)
    }


    fun entityToDTO(entity: Game): GameDto
    fun dtoToEntity(dto: GameDto): Game
    fun dtoToUpdate(dto: GameDto): UpdateGameDto
    fun updateToDto(updateGameDto: UpdateGameDto): GameDto
    fun entityToUpdate(entity: Game):UpdateGameDto
    fun updateToEntity(updateGameDto: UpdateGameDto): Game
}