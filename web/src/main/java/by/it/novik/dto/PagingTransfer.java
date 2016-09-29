package by.it.novik.dto;

import by.it.novik.entities.Payment;

import java.util.List;

/**
 * Created by Kate Novik.
 */
public class PagingTransfer {
    private int page;
    private int item_per_page;
    private int totalCountItems;
    private List<Payment> payments;

    public PagingTransfer() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItem_per_page() {
        return item_per_page;
    }

    public void setItem_per_page(int item_per_page) {
        this.item_per_page = item_per_page;
    }

    public int getTotalCountItems() {
        return totalCountItems;
    }

    public void setTotalCountItems(int totalCountItems) {
        this.totalCountItems = totalCountItems;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
