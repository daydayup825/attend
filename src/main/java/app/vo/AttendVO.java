package app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: fanbopeng
 * @Date: 2019/11/13 16:51
 * @Description:
 */

@Data
public class AttendVO {


    private Integer aid;

    private String empName;

    private String taskName;

    private Integer eid;

    private Integer tid;

    private String  attendAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp attendTime;

    private Integer enumber;






}
