package metanet.book.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class LoginResponse implements Serializable {

    private UserInfo userInfo;
    private TokenInfo tokenInfo;

    public LoginResponse(UserInfo userInfo, TokenInfo tokenInfo) {
        this.userInfo = userInfo;
        this.tokenInfo = tokenInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }
}
