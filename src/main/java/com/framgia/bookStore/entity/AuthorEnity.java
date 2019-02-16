package com.framgia.bookStore.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORS")
public class AuthorEnity extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOOK_ID", nullable = false, insertable = false, updatable = false)
    @Where(clause = "DELETED_FLAG=0")
    private BookEntity book;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;
}
