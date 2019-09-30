package biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.user;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomUserDetails extends Users implements OAuth2User,UserDetails {
    private Long id;
    private String email;
    private String phoneNumber;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

   /** public CustomUserDetails(final Users user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
*/
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
  }

    public CustomUserDetails(Long id, String phoneNumber, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public CustomUserDetails(Long id, String phoneNumber, String email, String password) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    // Construire un utilisateur d'authentification à partir d'un utilisateur ordinaire (Users)
    public static CustomUserDetails create(Users user) {

        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public static CustomUserDetails create(Users user, Map<String, Object> attributes) {
        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        customUserDetails.setAttributes(attributes);
        return customUserDetails;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getPhoneNumber();
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CustomUserDetails that = (CustomUserDetails) obj;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }


}