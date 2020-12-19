package com.khangse616.serverecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "rating_star")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RatingStar {

    @Id
    private int id;

    @Column(name = "star1")
    private int star1;
    @Column(name = "star2")
    private int star2;
    @Column(name = "star3")
    private int star3;
    @Column(name = "star4")
    private int star4;
    @Column(name = "star5")
    private int star5;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "ratingStar")
    @JsonIgnore
    private Product product;

    public RatingStar() {
    }

    public RatingStar(int id, int star1, int star2, int star3, int star4, int star5) {
        this.id = id;
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.star5 = star5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStar1() {
        return star1;
    }

    public void setStar1(int star1) {
        this.star1 = star1;
    }

    public int getStar2() {
        return star2;
    }

    public void setStar2(int star2) {
        this.star2 = star2;
    }

    public int getStar3() {
        return star3;
    }

    public void setStar3(int star3) {
        this.star3 = star3;
    }

    public int getStar4() {
        return star4;
    }

    public void setStar4(int star4) {
        this.star4 = star4;
    }

    public int getStar5() {
        return star5;
    }

    public void setStar5(int star5) {
        this.star5 = star5;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
