package edward.duong.hospital_mgmt.service;

import edward.duong.hospital_mgmt.service.models.UserInfo;
import edward.duong.hospital_mgmt.service.models.UserInfoDetails;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("User: {} logged in", email);
        return new UserInfoDetails(UserInfo.builder()
                .email(email)
                .name("Test User")
                .roles(List.of("USER", "ADMIN"))
                .build());
    }
}
