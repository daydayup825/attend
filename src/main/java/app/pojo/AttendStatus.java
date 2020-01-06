package app.pojo;

import leap.lang.enums.Bool;
import leap.orm.annotation.Column;
import leap.orm.annotation.Entity;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Table(name = "attendStatus")
public class AttendStatus {


    @Id
    private Integer asid;
    @Column("task_id")
    private Integer taskid;
    @Column("emp_id")
    private Integer empid;

    //状态（1.正常，2.早退，3.迟到，4.缺勤5.异地打卡）
    @Column("status")
    private Integer status;
    @Column("create_time")
    private Timestamp createTime;
    @Column("deleted")
    private Integer deleted;


}
