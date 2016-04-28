package org.ligson.fw.core.facade.base.dto;


/**
 * 描述: 分页
 *
 * @version V1.0
 */
@SuppressWarnings("serial")
public class BaseQueryPageResponseDto extends BaseResponseDto {

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 分页使用的参数，分页大小
     */
    private int pageSize = 10;

    /**
     * 分页使用的参数，当前分页号
     */
    private int pageNum = 1;

    /**
     * 分页使用的参数，总数据条数
     */
    private int totalCount = 0;

    /**
     * 分页使用的参数，总页数
     */
    private int pageCount = 0;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        //(responseDto.getTotalCount() - 1) / requestDto.getPageSize() + 1
        setPageCount((getTotalCount() - 1) / getPageSize() + 1);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        setPageCount((getTotalCount() - 1) / getPageSize() + 1);
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
        if (pageCount < 0) {
            this.pageCount = 0;
        }
    }

    @Override
    public String toString() {
        return "BaseQueryPageResponseDto [pageSize=" + pageSize + ", pageNum=" + pageNum + ", totalCount=" + totalCount
                + ", pageCount=" + pageCount + ", toString()=" + super.toString() + "]";
    }

}
