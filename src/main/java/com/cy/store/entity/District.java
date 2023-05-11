package com.cy.store.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 省市区的数据实体类
 * @author 魏敏捷
 * @version 1.0
 *
 *     code和name需要加``
 *     parent代表父区域的代码号
 *     code代表自身的代码号
 *     省的父代码号是+86,代表中国
 *
 * id INT(11) NOT NULL AUTO_INCREMENT,
 *   parent VARCHAR(6) DEFAULT NULL,
 *   `code` VARCHAR(6) DEFAULT NULL,
 *   `name` VARCHAR(16) DEFAULT NULL,
 *   PRIMARY KEY (id)
 */
public class District implements Serializable {

    private Integer id;
    private String parent;
    private String code;
    private String name;
    /**
     * get,set
     * equals和hashCode
     * toString
     */

    public District() {
    }

    public District(Integer id, String parent, String code, String name) {
        this.id = id;
        this.parent = parent;
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof District)) return false;
        District district = (District) o;
        return Objects.equals(getId(), district.getId()) &&
                Objects.equals(getParent(), district.getParent()) &&
                Objects.equals(getCode(), district.getCode()) &&
                Objects.equals(getName(), district.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParent(), getCode(), getName());
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", parent='" + parent + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
