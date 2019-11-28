package metanet.book.controller;

import metanet.book.controller.request.LoginRequest;
import metanet.book.controller.request.LogoutRequest;
import metanet.book.dto.LoginResponse;
import metanet.book.dto.TokenInfo;
import metanet.book.dto.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Consumes(MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {

        if (!loginRequest.getUsername().equals("hadoan")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setFirstName("Ha");
        userInfo.setLastName("Doan");
        userInfo.setUsername("hadoan");

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        tokenInfo.setAccessTokenExpireIn(
                TimeUnit.MILLISECONDS.toSeconds((24 * 60 * 60 * 1000)));

        return ResponseEntity.ok(new LoginResponse(userInfo, tokenInfo));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody LogoutRequest logoutRequest) {

       return ResponseEntity.ok().build();
    }

}
