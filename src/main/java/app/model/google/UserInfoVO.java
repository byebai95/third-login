package app.model.google;

import lombok.Data;

@Data
public class UserInfoVO {

    private String id;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
}
