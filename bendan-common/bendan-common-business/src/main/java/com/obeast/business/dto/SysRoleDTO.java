package com.obeast.business.dto;

import com.obeast.business.entity.SysRoleEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wxl
 * Date 2022/12/11 20:48
 * @version 1.0
 * Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDTO extends SysRoleEntity {

    @Schema(name = "权限集合")
    private List<Long> menuIds;
}
