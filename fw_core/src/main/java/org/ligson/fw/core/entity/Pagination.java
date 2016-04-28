package org.ligson.fw.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/4/11.
 * 分页工具
 */
public class Pagination<E extends BasicEntity> implements Serializable {

    /***
     * 开始记录数
     */
    private int offset;
    /**
     * 分页使用的参数，分页大小
     */
    private int max;

    /**
     * 分页使用的参数，当前分页号
     */
    private int page;

    /**
     * 分页使用的参数，总数据条数
     */
    private int totalCount;

    /**
     * 分页使用的参数，总页数
     */
    private int pageCount;


    /**
     * 查询结果数据
     */
    private List<E> datas = new ArrayList<>();

    /***
     * 使用page初始化
     *
     * @param max        每页的记录书
     * @param page       当前页码
     * @param totalCount 总记录数
     * @param datas      数据
     */
    public Pagination(int max, int page, int totalCount, List<E> datas) {
        this.max = max;
        this.page = page;
        this.totalCount = totalCount;
        this.datas = datas;
        this.offset = (page - 1) / max;
        if (this.totalCount % this.max == 0) {
            this.pageCount = this.totalCount / this.max;
        } else {
            this.pageCount = totalCount / this.max + 1;
        }
    }


    /***
     * 使用offset初始化
     *
     * @param offset     offset
     * @param max        max
     * @param totalCount 总记录数
     * @param pageCount  总页数不用设置
     * @param datas      数据
     */
    public Pagination(int offset, int max, int totalCount, int pageCount, List<E> datas) {
        this.offset = offset;
        this.max = max;
        this.totalCount = totalCount;
        this.datas = datas;
        if (this.totalCount % this.max == 0) {
            this.pageCount = this.totalCount / this.max;
        } else {
            this.pageCount = totalCount / this.max + 1;
        }
        this.page = this.offset / this.max + 1;
    }

    /***
     * 分页未启用时使用
     *
     * @param datas
     */
    public Pagination(List<E> datas) {
        this.datas = datas;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<E> getDatas() {
        return datas;
    }

    public void setDatas(List<E> datas) {
        this.datas = datas;
    }
}
