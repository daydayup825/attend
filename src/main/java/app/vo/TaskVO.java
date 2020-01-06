package app.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import leap.core.validation.annotations.NotEmpty;
import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: fanbopeng
 * @Date: 2019/11/11 17:23
 * @Description:
 */
@Data
public class TaskVO {




    private int id;

    private String taskName;

    @NotEmpty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp beginTimeStart;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp beginTimeOver;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp endTimeStart;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp endTimeOver;

    private int status;

    private String type;

    private String address;

    private String createUser;



}
