package com.example.nutri_well.config.auth.dto;

import com.example.nutri_well.model.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    //User클래스를 그대로 사용하면 직렬화를 구현하지 않았기 때문에 에러가 발생한다. 그래서 직렬화된 SessionUser클래스를 만들어준다.
    //객체를 바이트스트림으로 변환하여 세션에 저장하기 위함. 이렇게 직렬화 하지 않으면 세션에 저장할 수 없음.
    //User 클래스는 엔티티 클래스이며 직렬화가 되지 않기때문에 별도의 직렬화 클래스를 만든것.
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
