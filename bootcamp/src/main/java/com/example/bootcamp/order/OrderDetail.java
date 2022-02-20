package com.example.bootcamp.order;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class OrderDetail {
    @Id
    private int id;
    private int userId;
    private int paymentId;
    private int invoiceNumber;
    private Date orderDate;
    private Date paymentDate;
    private Date paymentExpireDate;
    private boolean paid;

    public OrderDetail() {
    }

    public OrderDetail(int id, int userId, int paymentId, int invoiceNumber, Date orderDate, Date paymentDate, Date paymentExpireDate, boolean paid) {
        this.id = id;
        this.userId = userId;
        this.paymentId = paymentId;
        this.invoiceNumber = invoiceNumber;
        this.orderDate = orderDate;
        this.paymentDate = paymentDate;
        this.paymentExpireDate = paymentExpireDate;
        this.paid = paid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getPaymentExpireDate() {
        return paymentExpireDate;
    }

    public void setPaymentExpireDate(Date paymentExpireDate) {
        this.paymentExpireDate = paymentExpireDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
