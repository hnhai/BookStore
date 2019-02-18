package com.framgia.bookStore.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_DETAIL")
public class OderDetailEntity extends AbstractEntity{
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "orderId", column = @Column(name = "ORDER_ID", nullable = false)),
            @AttributeOverride(name = "bookId", column = @Column(name = "BOOK_ID", nullable = false))})
    private OrderDetailID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID", nullable = false, insertable = false, updatable = false)
    @Where(clause = "DELETED_FLAG=0")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false)
    @Where(clause = "DELETED_FLAG=0")
    private OrderEntity order;

    @Column(name = "QUANTITY", nullable = false)
    private Long quantity;

    public OderDetailEntity(){}

    public OderDetailEntity(BookEntity book, OrderEntity order){
        this.book = book;
        this.order = order;
    }

    public OrderDetailID getId() {
        return id;
    }

    public void setId(OrderDetailID id) {
        this.id = id;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("order", order)
                .append("book", book)
                .appendSuper(super.toString())
                .toString();
    }
}
