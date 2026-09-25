package krupkoillia.chesstracker.userservice.mapper;

import krupkoillia.chesstracker.userservice.config.MapStructConfig;
import krupkoillia.chesstracker.userservice.dto.UserResponseDto;
import krupkoillia.chesstracker.userservice.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    UserResponseDto toDto(User model);

}
