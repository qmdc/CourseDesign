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
@ApiModel(value="Item对象", description="")
public class Item implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "唯一商品id")
    @TableId(value = "itemId", type = IdType.ASSIGN_ID)
    private Long itemId;

    @ApiModelProperty(value = "商品种类")
    @TableField("itemType")
    private String itemType;

    @ApiModelProperty(value = "商品描述")
    @TableField("itemDes")
    private String itemDes;

    @ApiModelProperty(value = "商品上架数量")
    @TableField("itemNums")
    private Integer itemNums;

    @ApiModelProperty(value = "商品图片")
    @TableField("itemImg")
    private String itemImg;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal itemprice;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


}
