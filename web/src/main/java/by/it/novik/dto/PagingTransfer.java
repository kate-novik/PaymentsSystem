package by.it.novik.dto;

import by.it.novik.entities.Payment;
import java.util.List;

/**
 * Created by Kate Novik.
 */
// please add DTO suffix to this class name
public class PagingTransfer {

    private int page;
    private int itemPerPage;
    private long totalCountItems;
    private List<Payment> payments;

    public PagingTransfer() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public long getTotalCountItems() {
        return totalCountItems;
    }

    public void setTotalCountItems(long totalCountItems) {
        this.totalCountItems = totalCountItems;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
