package com.qiandao.coursedesign.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author konan
 * @since 2022-06-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserVo implements Serializable {

    private Long userId;

    private Integer userGrade;

    private Integer userBranch;

    private String userName;

    private String userPwd;

    private Integer userSex;

    private Integer userAge;

    private String userWork;

    private String userPhone;

    private String perm;

    private String userAddress;

    private BigDecimal userMoney;

    private BigDecimal userSpend;

    private Integer userCount;

    private String createTime;



}
