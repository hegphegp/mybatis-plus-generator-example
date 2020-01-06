package com.hegp.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hgp
 * @since 2020-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="SysUser对象", description="")
public class SysUser extends TreeEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private Integer active;

    private Date createAt;

    private String description;

    private String duty;

    private String email;

    private Integer loginDaysNumber;

    private String nickname;

    private String officePhone;

    private Long orderIndex;

    private String password;

    private String phone;

    private String picImg;

    private Boolean sync;

    private String unitId;

    private Date updateAt;

    private String username;

    private String groupId;

    private String positionId;

}
