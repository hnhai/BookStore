package com.framgia.bookStore.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BOOKS")
public class BookEntity extends AbstractEntity{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ALIAS_NAME", unique = true, nullable = false)
    private String aliasName;

    @Column(name = "DESCRIPTION",  columnDefinition="TEXT")
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Long price;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "DISCOUNT")
    private Long discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false, updatable = false)
    @Where(clause = "DELETED_FLAG=0")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false, updatable = false)
    @Where(clause = "DELETED_FLAG=0")
    private PublisherEntity publisher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @OrderBy("id ASC")
    private Set<ImageEntity> images = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    @JoinTable(name = "BOOK_AUTHORS", catalog = "book_store", joinColumns = {
            @JoinColumn(name = "BOOK_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "AUTHOR_ID",
                    nullable = false, updatable = false) })
    private Set<AuthorEnity> authors = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade=CascadeType.ALL)
    private Set<OrderDetailEntity> orderDetails = new HashSet<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Set<ImageEntity> getImages() {
        return images;
    }

    public void setImages(Set<ImageEntity> images) {
        this.images = images;
    }

    public PublisherEntity getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherEntity publisher) {
        this.publisher = publisher;
    }

    public Set<AuthorEnity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEnity> authors) {
        this.authors = authors;
    }

    public Set<OrderDetailEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetailEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }
}
