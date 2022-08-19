package com.faraf.mapper;

import com.faraf.dto.RoleDto;
import com.faraf.entity.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);

    Set<Role> toEntity(Set<RoleDto> roleDto);

    Set<RoleDto> toDto(Set<Role> role);


}
