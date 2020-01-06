package app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;

/**
 * @author: fanbopeng
 * @Date: 2019/11/14 19:29
 * @Description:
 */
@Data
public class TimeVo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp endTime;

}
