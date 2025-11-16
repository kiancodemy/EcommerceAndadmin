package com.Main.Ecommerce.product;
import com.Main.Ecommerce.category.Category;
import com.Main.Ecommerce.comment.Comment;
import com.Main.Ecommerce.image.Image;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product",orphanRemoval = true,cascade = CascadeType.ALL)
    Set<Image> images = new HashSet<>();


    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name="category-id")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();


    ///////**** add and remove methods  ******///////

    //// add comment
    public void addComment(Comment comment){
        comments.add(comment);
        comment.setProduct(this);
    }


    /// remove comment
    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setProduct(null);
    }
    /// remove image
    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
    }

    /// add image
    public void addImage(Image image) {
        images.add(image);
        image.setProduct(this);
    }
}
