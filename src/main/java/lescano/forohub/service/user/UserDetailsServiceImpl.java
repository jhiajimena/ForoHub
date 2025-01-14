package lescano.forohub.service.user;

import lescano.forohub.dto.auth.AuthLoginRequest;
import lescano.forohub.dto.auth.AuthResponse;
import lescano.forohub.entities.ForumUser;
import lescano.forohub.repository.UserRepository;
import lescano.forohub.utils.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public  UserDetailsServiceImpl( PasswordEncoder passwordEncoder, UserRepository userRepository, JwtUtils jwtUtils){
        this.passwordEncoder=passwordEncoder;
        this.userRepository= userRepository;
        this.jwtUtils=jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ForumUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        String role = "ROLE_" + user.getProfile().getName();

        authorityList.add(new SimpleGrantedAuthority(role));
        user.getProfile().getPermissions().forEach(permission ->
                authorityList.add(new SimpleGrantedAuthority(permission.getName())));
        return new CustomUserDetails(user.getId(),user.getEmail(), user.getPassword(), authorityList);
    }

    public AuthResponse loginUser(AuthLoginRequest userRequest) {
        String username = userRequest.email();
        String password = userRequest.password();
        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

      return new AuthResponse(
                username, accessToken, "User logged successfully", true);
    }

    private Authentication authenticate(String username, String password) {
        CustomUserDetails userDetails = (CustomUserDetails) this.loadUserByUsername(username);

        if(userDetails == null){
            throw  new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw  new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }
}
