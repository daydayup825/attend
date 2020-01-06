package app.pojo;

import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author: fanbopeng
 * @Date: 2019/11/4 13:30
 * @Description:
 */
@Data
@Table("attend")
public class Attend {


    @Id
    private Integer id;
    @Column("emp_id")
    private Integer empid;
    @Column("task_id")
    private Integer taskid;
    @Column("attend_time")
    private Timestamp attendTime;
    @Column("attend_address")
    private String attendAddress;
    @Column("deleted")
    private Integer deleted;

}
