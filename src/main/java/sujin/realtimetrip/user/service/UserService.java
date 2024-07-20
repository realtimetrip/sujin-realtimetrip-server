package sujin.realtimetrip.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sujin.realtimetrip.user.dto.SignUpDto;
import sujin.realtimetrip.user.dto.UserDto;
import sujin.realtimetrip.user.entity.User;
import sujin.realtimetrip.user.repository.UserRepository;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    // 회원가입
    public UserDto signUp(SignUpDto signUpDto) {

        // 입력된 이메일로 가입된 유저가 있는지 확인
        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        // 이미 사용된 이메일인 경우 에러 반환
        if(findUser.isPresent())
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);

        // 프로필 사진 s3에 저장
        String profile = saveImage(signUpDto.getProfile());

        // 입력된 이메일 및 정보로 회원가입
        User savedUser = userRepository.saveAndFlush(new User(signUpDto.getEmail(), signUpDto.getPassword(), signUpDto.getNickName(), profile));

        // 회원가입된 유저 정보 반환
        return new UserDto(savedUser.getId(), savedUser.getEmail(), savedUser.getNickName(), profile);
    }


    @Transactional
    public String saveImage(MultipartFile multipartFile) {
        String imageUrl = null;
        try {
            // 파일 원래 이름 가져오기
            String originalName = multipartFile.getOriginalFilename();
            // 파일 이름 null인 경우 예외 처리
            if (originalName == null) {
                throw new CustomException(ErrorCode.FILE_NAME_CANNOT_BE_NULL);
            }

            // S3 업로드를 위한 메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType()); // 컨텐트 타입 설정
            objectMetadata.setContentLength(multipartFile.getInputStream().available()); // 파일 크기 설정

            // S3에 파일 업로드
            amazonS3Client.putObject(bucketName, originalName, multipartFile.getInputStream(), objectMetadata);
            // 업로드된 파일의 URL 생성
            imageUrl = amazonS3Client.getUrl(bucketName, originalName).toString();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.ERROR_SAVING_IMAGE_TO_S3);
        }
        return imageUrl;
    }

        // 로그인
    public UserDto login(String email, String password) {
        // 이메일과 비밀번호가 일치하는 User 객체 반환
        User user = userRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password)) // 비밀번호 일치 여부 확인
                .orElse(null); // 일치하는 사용자가 없을 경우 null 반환
        assert user != null;
        return new UserDto(user.getId(), user.getEmail(), user.getNickName(), user.getProfile());
    }
}
