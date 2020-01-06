package app.vo;

import lombok.Data;

/**
 * @author: fanbopeng
 * @Date: 2019/11/20 14:33
 * @Description:
 */

@Data
public class AttendStatusVO {


    private Integer id;

    private String empName;

    private Integer workNumber;

    private Integer taskId;

    private String taskName;

    private Integer status;


}
