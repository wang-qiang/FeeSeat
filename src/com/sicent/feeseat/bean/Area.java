/**
 * 
 */
package com.sicent.feeseat.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * 
 * @author wangqiang
 * 
 */
@Entity
@Table(name = "area")
public class Area implements Serializable {

    private static final long serialVersionUID = 6723629655994827282L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 64)
    private String name;

    @Column(length = 512)
    private String descrip;

    @Column(length = 512)
    private String background;

    @Column(name = "create_time", length = 24)
    private String createTime;

    @Column
    private float x;

    @Column
    private float y;

    @Column
    private float width;

    @Column
    private float height;

    @Column(length = 512)
    private String other;

    @Column(length = 128)
    private String snbid;

    @OneToMany(cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.MERGE }, fetch = FetchType.EAGER, mappedBy = "area")
    @JsonManagedReference
    private Set<Obj> objLst = new HashSet<Obj>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSnbid() {
        return snbid;
    }

    public void setSnbid(String snbid) {
        this.snbid = snbid;
    }

    public Set<Obj> getObjLst() {
        return objLst;
    }

    public void setObjLst(Set<Obj> objLst) {
        this.objLst = objLst;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((snbid == null) ? 0 : snbid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Area other = (Area) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (snbid == null) {
            if (other.snbid != null) {
                return false;
            }
        } else if (!snbid.equals(other.snbid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Area [id=" + id + ", name=" + name + ", descrip=" + descrip + ", background="
                + background + ", createTime=" + createTime + ", x=" + x + ", y=" + y + ", width="
                + width + ", height=" + height + ", other=" + other + ", snbid=" + snbid
                + ", objLst=" + objLst + "]";
    }

}
