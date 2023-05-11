package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**  
 *   作为实体类的基类
 * @author 魏敏捷
 * @version 1.0
 *
 * created_user VARCHAR(20) COMMENT ‘创建人’,
 * created_time DATETIME COMMENT ‘创建时间’,
 * modified_user VARCHAR(20) COMMENT ‘修改人’,
 * modified_time DATETIME COMMENT ‘修改时间’,
 *
 */
@Data
public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
/**
 * get,set
 * equals和hashCode
 * toString
 */
}

