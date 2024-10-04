package org.kiennguyenfpt.datingapp.dtos.mapper;

import org.kiennguyenfpt.datingapp.dtos.responses.ProfileResponse;
import org.kiennguyenfpt.datingapp.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "avatar", target = "avatar")
    @Mapping(source = "phone", target = "phone")
    ProfileResponse profileToProfileResponse(Profile profile);
}
