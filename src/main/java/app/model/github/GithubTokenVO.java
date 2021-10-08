package app.model.github;

import lombok.Data;

@Data
public class GithubTokenVO {

    private String accessToken;

    private String scope;
}
