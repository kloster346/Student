package demo.ch7_5.entity;

import lombok.Data;

@Data
public class PageReq {
    //请求第几页
    private Integer current = 1;
    //每页的记录数
    private Integer size = 5;
//    //排序字段
//    private Integer sortBy;
//    //排序方式
//    private Boolean sortAsc;
}
