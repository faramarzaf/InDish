package com.faraf.service;


import com.faraf.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repository;

/*    @Override
    public List<UserGetDto> getAllByCity(String city, Integer pageNo, Integer pageSize, String sortBy) {
        return getPaginatedUsers(true, city, pageNo, pageSize, sortBy);
    }

    @Override
    public List<UserGetDto> getAllByCountry(String country, Integer pageNo, Integer pageSize, String sortBy) {
        return getPaginatedUsers(false, country, pageNo, pageSize, sortBy);
    }*/


   /* private List<UserGetDto> getPaginatedUsers(boolean byCity, String parameterPaginateBy, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> userPage;
        int totalElements;
        PageImpl<UserGetDto> userGetDtoPage;

        if (byCity) {
            userPage = repository.findByUserProfile_CityIgnoreCase(parameterPaginateBy, paging);
            totalElements = (int) userPage.getTotalElements();
            userGetDtoPage = new PageImpl<>(userPage.getContent()
                    .stream()
                    .map(user -> new UserGetDto(
                            user.getId(),
                            user.getUserName(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getUserProfile(),
                            user.getPosts(),
                            user.getJoinedOn()
                            ))
                    .collect(Collectors.toList()), paging, totalElements);
        } else {
            userPage = repository.findByUserProfile_CountryIgnoreCase(parameterPaginateBy, paging);
            totalElements = (int) userPage.getTotalElements();
            userGetDtoPage = new PageImpl<>(userPage.getContent()
                    .stream()
                    .map(user -> new UserGetDto(
               *//*             user.getId(),
                            user.getUserName(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getUserProfile(),
                            user.getPosts(),
                            user.getJoinedOn()*//*
                            )
                    )
                    .collect(Collectors.toList()), paging, totalElements);
        }

        return userGetDtoPage.getContent();
    }*/

}
