package app.model.facebook;

import lombok.Data;

@Data
public class FaceBookTokenVO {

    private String accessToken;

    private String tokenType;

    private String expiresIn;
}
