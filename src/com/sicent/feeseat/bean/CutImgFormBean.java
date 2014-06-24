/**
 * 
 */
package com.sicent.feeseat.bean;

import java.io.Serializable;

import javax.validation.constraints.Min;

/**
 * 封装图片剪切对象
 * 
 * @author wangqiang
 * 
 */
public class CutImgFormBean implements Serializable {

    private static final long serialVersionUID = -3834006914161376984L;

    /**
     * 源地址
     */
    private String source;

    /**
     * 剪切后的地址
     */
    private String dest;

    /**
     * 选择框宽度
     */
    @Min(0)
    private Integer width;

    /**
     * 选择框高度
     */
    @Min(0)
    private Integer height;

    /**
     * 选择框的左边x坐标
     */
    @Min(0)
    private Integer left;

    /**
     * 选择框的左边y坐标
     */
    @Min(0)
    private Integer top;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dest == null) ? 0 : dest.hashCode());
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((top == null) ? 0 : top.hashCode());
        result = prime * result + ((width == null) ? 0 : width.hashCode());
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
        CutImgFormBean other = (CutImgFormBean) obj;
        if (dest == null) {
            if (other.dest != null) {
                return false;
            }
        } else if (!dest.equals(other.dest)) {
            return false;
        }
        if (height == null) {
            if (other.height != null) {
                return false;
            }
        } else if (!height.equals(other.height)) {
            return false;
        }
        if (left == null) {
            if (other.left != null) {
                return false;
            }
        } else if (!left.equals(other.left)) {
            return false;
        }
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }
        if (top == null) {
            if (other.top != null) {
                return false;
            }
        } else if (!top.equals(other.top)) {
            return false;
        }
        if (width == null) {
            if (other.width != null) {
                return false;
            }
        } else if (!width.equals(other.width)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CutImgFormBean [source=" + source + ", dest=" + dest + ", width=" + width
                + ", height=" + height + ", left=" + left + ", top=" + top + "]";
    }

}
