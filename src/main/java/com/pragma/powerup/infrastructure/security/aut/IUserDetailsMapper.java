package com.pragma.powerup.infrastructure.security.aut;

import com.pragma.powerup.infrastructure.client.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserDetailsMapper {

    DetailsUser toUserDetail(UserDto userDto);
}
