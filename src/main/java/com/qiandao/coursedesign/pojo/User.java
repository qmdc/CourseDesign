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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户唯一id")
    @TableId(value = "userId", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "用户等级")
    @TableField("userGrade")
    private Integer userGrade;

    @ApiModelProperty(value = "用户积分")
    @TableField("userBranch")
    private Integer userBranch;

    @ApiModelProperty(value = "用户名称")
    @TableField("userName")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    @TableField("userPwd")
    private String userPwd;

    @ApiModelProperty(value = "用户性别")
    @TableField("userSex")
    private Integer userSex;

    @ApiModelProperty(value = "用户年龄")
    @TableField("userAge")
    private Integer userAge;

    @ApiModelProperty(value = "用户工作")
    @TableField("userWork")
    private String userWork;

    @ApiModelProperty(value = "用户电话")
    @TableField("userPhone")
    private String userPhone;

    @ApiModelProperty(value = "用户权限")
    @TableField("perm")
    private String perm;

    @ApiModelProperty(value = "用户收货地点")
    @TableField("userAddress")
    private String userAddress;

    @ApiModelProperty(value = "用户余额")
    @TableField("userMoney")
    private BigDecimal userMoney;

    @ApiModelProperty(value = "用户消费额")
    @TableField("userSpend")
    private BigDecimal userSpend;

    @ApiModelProperty(value = "用户订单总数")
    @TableField("userCount")
    private Integer userCount;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


}
