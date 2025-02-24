package edward.duong.hospital_mgmt.controller;

import edward.duong.hospital_mgmt.controller.models.AuthResponse;
import edward.duong.hospital_mgmt.utility.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestParam String email, @RequestParam String password) {
        String token = jwtUtil.generateToken(email);
        return AuthResponse.builder().token(token).build();
    }
}
