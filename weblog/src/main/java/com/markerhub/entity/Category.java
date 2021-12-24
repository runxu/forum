package com.markerhub.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author oo
 * @since 2021-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cid;

    private String cname;

    private String cdesription;


}
