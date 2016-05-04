package org.ligson.fw.core.facade.base.dto;

import org.ligson.fw.core.facade.annotation.Param;

/**
 * 分页查询
 */
@SuppressWarnings("serial")
public class BaseQueryPageRequestDto extends BaseRequestDto {

    /**
     * 分页使用的参数，分页大小
     */
    @Param(name = "分页大小", required = false, min = 0)
    private Integer pageSize = 10;

    /**
     * 分页使用的参数，当前分页号
     */
    @Param(name = "当前分页号", required = false, min = 1)
    private Integer pageNum = 1;

    @Param(name = "是否分页", required = false)
    private Boolean pageAble = true;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Boolean getPageAble() {
        return pageAble;
    }

    public void setPageAble(Boolean pageAble) {
        this.pageAble = pageAble;
    }

    @Override
    public String toString() {
        return "BaseQueryPageRequestDto{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", pageAble=" + pageAble +
                '}';
    }

}
