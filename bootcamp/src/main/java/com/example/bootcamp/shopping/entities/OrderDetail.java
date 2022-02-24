package com.example.bootcamp.shopping.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int paymentId;
    private int invoiceNumber;
    private Date orderDate;
    private Date paidDate;
    private Date paymentExpireDate;

    public OrderDetail() {
    }

    public OrderDetail(int id, int userId, int paymentId, int invoiceNumber, Date orderDate, Date paidDate, Date paymentExpireDate) {
        this.id = id;
        this.userId = userId;
        this.paymentId = paymentId;
        this.invoiceNumber = invoiceNumber;
        this.orderDate = orderDate;
        this.paidDate = paidDate;
        this.paymentExpireDate = paymentExpireDate;
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

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Date getPaymentExpireDate() {
        return paymentExpireDate;
    }

    public void setPaymentExpireDate(Date paymentExpireDate) {
        this.paymentExpireDate = paymentExpireDate;
    }
}
