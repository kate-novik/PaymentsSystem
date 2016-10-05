package by.it.novik.utils;

import by.it.novik.dto.PagingTransferDTO;

/**
 * Created by Kate Novik.
 */
public class Pagination {
    private static final int START_PAGE = 1;
    private static final int START_ITEM_PER_PAGE = 10;
//    public static int pageResult;
//    public static int item_per_page_result;
//    public static int firstItem;
//
//    public static void  checkPage (int page, int item_per_page, Long totalCountItems) {
//        int totalCountPage = (int) Math.ceil((double)totalCountItems / item_per_page);
//        if (page > 1 && page <= totalCountPage){
//            pageResult = page;
//            item_per_page_result = item_per_page;
//        }
//        else {
//            pageResult = START_PAGE;
//            item_per_page_result = START_ITEM_PER_PAGE;
//        }
//        firstItem = (pageResult -1 ) * item_per_page_result;
//    }

    public static PagingTransferDTO  calculatePage (PagingTransferDTO paging) {
        long totalCountItems = paging.getTotalCountItems();
        int itemPerPage = paging.getItemPerPage();
        int page = paging.getPage();
        int totalCountPage = (int) Math.ceil((double)totalCountItems / itemPerPage);
        if (page <= 1 && page > totalCountPage){
            paging.setPage(START_PAGE);
            paging.setItemPerPage(START_ITEM_PER_PAGE);
        }
        paging.setFirstItem ((page -1 ) * itemPerPage);
        return paging;
    }

}
