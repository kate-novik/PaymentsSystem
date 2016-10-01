package by.it.novik.controller;

/**
 * Created by Kate Novik.
 */
public class Pagination {
    private static final int START_PAGE = 1;
    private static final int START_ITEM_PER_PAGE = 10;
    public static int pageResult;
    public static int item_per_page_result;
    public static int firstItem;

    public static void  checkPage (int page, int item_per_page, Long totalCountItems) {
        long totalCountPage = totalCountItems / item_per_page;
        if (page>1 && page <= totalCountPage){
            pageResult = page;
            item_per_page_result = item_per_page;
        }
        else {
            pageResult = START_PAGE;
            item_per_page_result = START_ITEM_PER_PAGE;
        }
        firstItem = (pageResult-1)*item_per_page_result;
    }

}
