package dnd.project.dnd6th7worryrecordservice.jwt;

import com.google.gson.Gson;
import dnd.project.dnd6th7worryrecordservice.dto.jwt.JwtPayloadDto;
import dnd.project.dnd6th7worryrecordservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("####### Interceptor preHandle Start!!!");

        String atJwtAccessToken = request.getHeader("at-jwt-access-token");
        String atJwtRefreshToken = request.getHeader("at-jwt-refresh-token");

        System.out.println("atJwtAccessToken = " + atJwtAccessToken);
        System.out.println("atJwtRefreshToken = " + atJwtRefreshToken);
        System.out.println("request method : " + request.getMethod());

        //preflight 대응
        if ("OPTIONS".equals(request.getMethod())) {
            System.out.println("request method is OPTIONS!!");
            return true;
        }

        //토큰 검증 및 재발급

        //When RefreshToken isn't in HTTP header
        if (atJwtRefreshToken == null) {
            //When AccessToken is in HTTP header
            if (atJwtAccessToken != null && atJwtAccessToken.length() > 0) {
                //validate AccessToken
                if (jwtUtil.validate(atJwtAccessToken)) return true;
                else throw new IllegalArgumentException("Token Error!!!");
            }//When AccessToken isn't HTTP header
            else {
                throw new IllegalArgumentException("No Token!!!");
            }
        }//When RefreshToken in HTTP header
        else{
            //validate RefreshToken
            if(jwtUtil.validate(atJwtRefreshToken)){
                //Decode & Parse AccessToken to JwtPayloadDto
                String accessTokenPayload = jwtUtil.decodePayload(atJwtAccessToken);
                Gson gson = new Gson();
                JwtPayloadDto jwtPayload = gson.fromJson(accessTokenPayload, JwtPayloadDto.class);

                //Compare RefreshToken in DB with RefreshToken in HTTP header
                String refreshTokenInDB = userService.findRefreshTokenByKakaoId(jwtPayload.getKakaoId());

                if(refreshTokenInDB.equals(atJwtRefreshToken)){
                    //Create new AccessToken and addHeader
                    String AccessJws = jwtUtil.createToken(jwtPayload.toUserRequestDto()).getJwtAccessToken();
                    response.addHeader("at-jwt-access-token", AccessJws);
                }else {
                    throw new IllegalArgumentException("Refresh Token Error!!!");
                }

                return true;

            }else {
                throw new IllegalArgumentException("Refresh Token Error!!!");
            }
        }

    }
}