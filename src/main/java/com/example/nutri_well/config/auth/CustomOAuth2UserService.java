package com.example.nutri_well.config.auth;

import com.example.nutri_well.config.auth.dto.OAuthAttributes;
import com.example.nutri_well.config.auth.dto.SessionUser;
import com.example.nutri_well.model.User;
import com.example.nutri_well.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    //소셜 로그인 성공후 후속 조치를 취할 클래스 => OAuth2UserService 사용자의 정보들을 기반으로 가입, 정보수정, 세션 저장등의 기능을 지원
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); // delegate : OAuth2UserService 인터페이스를 구혈할 객체
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //request에는 토큰, 클라이언트 id 등이 담겨있음.

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); //사용자 정보를 받아서 OAuthAttributes로 전환
        if (attributes == null) {
            System.out.println("OAuthAttributes is nullOAuthAttributes is nullOAuthAttributes is nullOAuthAttributes is nullOAuthAttributes is null");
            throw new OAuth2AuthenticationException("OAuthAttributes is null");
        }
        System.out.println("=============================================================="+attributes);
        System.out.println("OAuthAttributes: " + attributes);
        System.out.println("NameAttributeKey: " + attributes.getNameAttributeKey());
        System.out.println("Attributes Map: " + attributes.getAttributes());
        User user = saveOrUpdate(attributes); //데이터베이스를 확인해서 기존 유저면 업데이트 , 새로운 유저면 저장
        System.out.println("==============================================================="+user);
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser (직렬화된 dto 클래스 사용) => 세션에 저장
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes) {
        Optional<User> userOptional = userRepository.findByEmail(attributes.getEmail()); //이메일로 사용자가 존재하는지 확인
        User user;

        if (userOptional.isPresent()) { //사용자가  존재하면
            user = userOptional.get();
            user.update(attributes.getName(), attributes.getPicture());
        } else {//사용자가 존재하지 않으면
            user = attributes.toEntity();
            user.setPassword("oauth2user"); // 기본 비밀번호 설정
            user.setGender(attributes.getGender() != null ? attributes.getGender() : "unknown"); //성별 설정
            user.setState(true); // 상태 기본값 설정
        }

        return userRepository.save(user);
    }
}
