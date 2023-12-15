package com.laptopselling.laptop.util;

import com.laptopselling.laptop.model.User;
import com.laptopselling.laptop.respository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private UserRepository userRepository;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getUsernameFromToken(String token) {
//        System.out.println("Day la token: " );
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        String role = userDetails.getAuthorities().toString();
//        role = role.substring(1, role.length() - 1);
//
//        TokenInfo tokenInfo = new TokenInfo();
//        tokenInfo.setUsername(userDetails.getUsername());
//        tokenInfo.setUserRole(role);
//
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .claim("userInfo", tokenInfo)
////                .setSubject(jo.toString())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(key).compact();
//    }

    public String generateToken(UserDetails userDetails) {
        User tempUser = userRepository.findUserByUsername(userDetails.getUsername()).get();
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .claim("id", tempUser.getId().toString())
                .claim("name", tempUser.getFullName())
                .claim("role", tempUser.getRole())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
