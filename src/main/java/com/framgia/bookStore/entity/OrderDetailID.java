package com.framgia.bookStore.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderDetailID implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "ORDER_ID", nullable = false)
    private Long orderId;

    @Column(name = "BOOK_ID", nullable = false)
    private Long bookId;


    public OrderDetailID(){}

    public OrderDetailID(Long orderId, Long bookId){
        this.orderId = orderId;
        this.bookId = bookId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("orderId", orderId)
            .append("bookId", bookId)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDetailID that = (OrderDetailID) o;

        return new EqualsBuilder()
            .append(orderId, that.orderId)
            .append(bookId, that.bookId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(orderId)
            .append(bookId)
            .toHashCode();
    }
}
