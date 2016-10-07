package by.it.novik.utils;

import by.it.novik.dto.PagingTransferDTO;

/**
 * Created by Kate Novik.
 */
public class Pagination {
    private static final int START_PAGE = 1;
    private static final int START_ITEM_PER_PAGE = 10;

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
