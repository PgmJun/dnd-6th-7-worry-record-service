package dnd.project.dnd6th7worryrecordservice.config;

import dnd.project.dnd6th7worryrecordservice.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
// Spring Security Configuration Class를 작성하기 위해서는 WebSecurityConfigurerAdapter를 상속하여 클래스를 생성하고 @EnableWebSecurity 어노테이션을 추가해야 합니다(@Configuration 어노테이션 대신 사용).

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // swagger-ui.html의 경우 인증된 사용자가 아니어도 접근가능하도록 설정(dev환경에 대해서만 swagger 설정을 하였기 때문에 인증된 사용자가 아니어도 됨)
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll() // preflight 대응
//                .antMatchers("/auth/**").permitAll() // /auth/**에 대한 접근을 인증 절차 없이 허용(로그인 관련 url)
//                .antMatchers("/worries/**").permitAll() // /worries/**에 대한 접근을 인증 절차 없이 허용(로그인 관련 url)
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .antMatchers("/ping").permitAll()
//                // 특정 권한을 가진 사용자만 접근을 허용해야 할 경우, 하기 항목을 통해 가능
//                //.antMatchers("/admin/**").hasAnyRole("ADMIN")
//                .anyRequest().authenticated() // 위에서 따로 지정한 접근허용 리소스 설정 후 그 외 나머지 리소스들은 무조건 인증을 완료해야 접근 가능
//                .and()
//                .headers() // 아래에 X-Frame-Option 헤더 설정을 위해 headers() 작성
//                .frameOptions().sameOrigin() // 동일 도메인에서는 iframe 접근 가능하도록 X-Frame-Options을 smaeOrigin()으로 설정
//                .and()
//                .cors()
//                .and()
//                .csrf().disable()
//                // 예외 처리를 하고 싶다면 아래와 같이 작성 가능합니다.
//                //.exceptionHandling() // 예외 처리 지정
//                //.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                //.accessDeniedHandler(new CustomAccessDeniedHandler())
//                .sessionManagement()    //스프링 시큐리티 session 정책
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음(JWT 같은토큰방식을 쓸때 사용하는 설정)

    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secret);
    }
}
