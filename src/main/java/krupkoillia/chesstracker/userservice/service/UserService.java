package krupkoillia.chesstracker.userservice.service;

import krupkoillia.chesstracker.userservice.dto.ChangePasswordRequestDto;
import krupkoillia.chesstracker.userservice.dto.UserResponseDto;
import krupkoillia.chesstracker.userservice.exception.AuthenticatedUserNotFoundException;
import krupkoillia.chesstracker.userservice.exception.WrongPasswordException;
import krupkoillia.chesstracker.userservice.mapper.UserMapper;
import krupkoillia.chesstracker.userservice.model.User;
import krupkoillia.chesstracker.userservice.repository.UserRepository;
import krupkoillia.chesstracker.userservice.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponseDto getMe() {
        return userMapper.toDto(getUserFromSecurityContext());
    }

    @Transactional
    public UserResponseDto changeDisplayName(String displayName) {
        User user = getUserFromSecurityContext();

        user.setDisplayName(displayName);

        return userMapper.toDto(user);
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDto requestDto) {
        User user = getUserFromSecurityContext();

        if (!passwordEncoder.matches(requestDto.oldPassword(), user.getPassword())) {
            throw new WrongPasswordException("Old password does not match with current one");
        }

        user.setPassword(passwordEncoder.encode(requestDto.newPassword()));

    }

    @Transactional
    public void delete() {
        userRepository.delete(getUserFromSecurityContext());
    }

    private User getUserFromSecurityContext() {
        Long userId = SecurityUtil.getAuthenticatedUserId();

        return userRepository.findById(userId).orElseThrow(
                () -> new AuthenticatedUserNotFoundException(
                    "Authenticated user with id " + userId + " does not exist")
        );
    }

}
