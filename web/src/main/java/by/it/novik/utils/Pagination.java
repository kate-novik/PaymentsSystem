package by.it.novik.utils;

/**
 * Created by Kate Novik.
 */
public class Pagination {
    private static final int START_PAGE = 1;
    private static final int START_ITEM_PER_PAGE = 10;
    // this is not thread safe and must be changed
    // moreover this code violates incapsulation principle and this also must be changed
    // please refactor
    public static int pageResult;
    public static int item_per_page_result;
    public static int firstItem;

    // bad method name
    // this method clearly calculates something, not just checks
    // please rename
    public static void  checkPage (int page, int item_per_page, Long totalCountItems) {
        int totalCountPage = (int) Math.ceil((double)totalCountItems / item_per_page);
        if (page > 1 && page <= totalCountPage){
            pageResult = page;
            item_per_page_result = item_per_page;
        }
        else {
            pageResult = START_PAGE;
            item_per_page_result = START_ITEM_PER_PAGE;
        }
        firstItem = (pageResult -1 ) * item_per_page_result;
    }

}
