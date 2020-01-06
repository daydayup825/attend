package app.commons;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import leap.web.exception.ResponseException;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Slf4j
@Builder
@Data
public class JwtTokenUtils {
    /**
     * 传输信息，必须是json格式
     */
    private String msg;
    /**
     * 所验证的jwt
     */

    private String token;

    private final String secret="324iu23094u598ndsofhsiufhaf_+0wq-42q421jiosadiusadiasd";


    public static List<String> tokenList= new ArrayList<>();

    public String creatJwtToken () {
        msg = new AESUtil(msg).encrypt();//先对信息进行aes加密(防止被破解）
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("attend").withExpiresAt(DateTime.now().plusDays(1).toDate())
                    .withClaim("user", msg)
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
              throw e;
        }
        log.info("加密后：" + token);
        return token;
    }
    /**
     * 解密jwt并验证是否正确
     */
    public String freeJwt (String token) {
        DecodedJWT decodedJWT = null;
        try {
            //使用hmac256加密算法
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("attend")
                    .build();
            decodedJWT = verifier.verify(token);
            log.info("签名人：" + decodedJWT.getIssuer() + " 加密方式：" + decodedJWT.getAlgorithm() + " 携带信息：" + decodedJWT.getClaim("user").asString());
        } catch (Exception e) {
            log.info("jwt解密出现错误，jwt或私钥或签证人不正确");
            throw new ResponseException(405,"jwt解密出现错误,jwt或私钥或签证人不正确");
        }
        //获得token的头部，载荷和签名，只对比头部和载荷
        String [] headPayload = token.split("\\.");
        //获得jwt解密后头部
        String header = decodedJWT.getHeader();
        //获得jwt解密后载荷
        String payload = decodedJWT.getPayload();
        if(!header.equals(headPayload[0]) && !payload.equals(headPayload[1])){
            throw new ResponseException(405,"jwt解密出现错误,jwt或私钥或签证人不正确");
        }
        return new AESUtil(decodedJWT.getClaim("user").asString()).decrypt();
    }


    public static void main(String[] args) {

        String token = JwtTokenUtils.builder().msg("hha").build().creatJwtToken();
        System.out.println(token);




    }

}
