package app.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: fanbopeng
 * @Date: 2019/11/3 18:32
 * @Description:
 */
@Data
@Table(name = "task")
public class Task {



    @Id
    private int id;
    @Column(name = "task_name")
    private String taskName;

    @Column(name = "begin_time_start")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp beginTimeStart;

    @Column(name = "begin_time_over")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp beginTimeOver;

    @Column(name = "end_time_start")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp endTimeStart;

    @Column(name = "end_time_over")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp endTimeOver;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    private Timestamp createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "status")
    private int status;

    @Column(name = "type")
    private String type;

    @Column(name = "address")
    private String address;

    @Column(name = "deleted")
    private int deleted;



}
