/**
 * 
 */
package com.sicent.feeseat.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * 
 * @author wangqiang
 * 
 */
@Entity
@Table(name = "obj")
public class Obj implements Serializable {

    private static final long serialVersionUID = -1620691702349743368L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(length = 64)
    private String name;

    @Column(length = 32)
    private String type;

    @Column(length = 512)
    private String descrip;

    @Column
    private float x;

    @Column
    private float y;

    @Column
    private float width;

    @Column
    private float height;

    @Column(length = 128)
    private String snbid;

    @Column(length = 24)
    private String font;

    @Column(name = "font_size", length = 8)
    private String fontSize;

    @Column(length = 8)
    private String color;

    @Column(length = 8)
    private String bgcolor;

    @Column(length = 512)
    private String other;

    // optional=true：可选，表示此对象可以没有，可以为null；false表示必须存在
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE })
    @JoinColumn(name = "area_id")
    @JsonBackReference
    private Area area;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getSnbid() {
        return snbid;
    }

    public void setSnbid(String snbid) {
        this.snbid = snbid;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Obj other = (Obj) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Obj [id=" + id + ", name=" + name + ", type=" + type + ", descrip=" + descrip
                + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", snbid="
                + snbid + ", font=" + font + ", fontSize=" + fontSize + ", color=" + color
                + ", bgcolor=" + bgcolor + ", other=" + other + ", area=" + area + "]";
    }

}
