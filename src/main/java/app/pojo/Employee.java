package app.pojo;

import leap.orm.annotation.Column;
import leap.orm.annotation.Id;
import leap.orm.annotation.Table;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: fanbopeng
 * @Date: 2019/11/4 14:02
 * @Description:
 */
@Data
@Table("employee")
public class Employee {

    @Id
    private Integer eid;
    @Column("ename")
    private String ename;
    @Column("gender")
    private String gender;
    @Column("department")
    private String department;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("email")
    private String email;
    @Column("createTime")
    private Timestamp createTime;
    @Column("deleted")
    private Integer deleted;
    @Column("enumber")
    private Integer enumber;



}
