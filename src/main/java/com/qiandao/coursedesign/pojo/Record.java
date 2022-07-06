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
@ApiModel(value="Record对象", description="")
public class Record implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "唯一记录id")
    @TableId(value = "recordId", type = IdType.AUTO)
    private Long recordId;

    @ApiModelProperty(value = "商品唯一id")
    @TableField("itemId")
    private Long itemId;

    @ApiModelProperty(value = "用户唯一id")
    @TableField("userId")
    private Long userId;

    @ApiModelProperty(value = "商品成交数量")
    @TableField("recordNums")
    private Integer recordNums;

    @ApiModelProperty(value = "交易时间")
    @TableField("recordTime")
    private String recordTime;

    @ApiModelProperty(value = "总成交额")
    @TableField("recordMoney")
    private BigDecimal recordMoney;

    @ApiModelProperty(value = "商品描述")
    @TableField("itemDes")
    private String itemDes;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal itemprice;

    @ApiModelProperty(value = "商品图片")
    @TableField("itemImg")
    private String itemImg;

    @ApiModelProperty(value = "使用积分数量")
    @TableField("useBranch")
    private Integer useBranch;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


}
