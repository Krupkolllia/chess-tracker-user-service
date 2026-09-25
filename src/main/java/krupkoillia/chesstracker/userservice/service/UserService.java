package krupkoillia.chesstracker.userservice.service;

import krupkoillia.chesstracker.userservice.dto.ChangePasswordRequestDto;
import krupkoillia.chesstracker.userservice.dto.UserResponseDto;
import krupkoillia.chesstracker.userservice.mapper.UserMapper;
import krupkoillia.chesstracker.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto getMe() {
        // TODO: method logic
        return null;
    }

    @Transactional
    public UserResponseDto changeUsername(String username) {
        // TODO: method logic
        return null;
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDto requestDto) {
        // TODO: method logic
    }

    public void delete() {
        // TODO: method logic
    }

}
