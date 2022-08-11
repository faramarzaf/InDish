package com.faraf.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_ingredient")
public class Ingredient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = FoodPost.class)
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private FoodPost foodPost;

    @Column(name = "content")
    private String content;

    public Ingredient() {
    }

    public Ingredient(Long id, FoodPost foodPost, String content) {
        this.id = id;
        this.foodPost = foodPost;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FoodPost getFoodPost() {
        return foodPost;
    }

    public void setFoodPost(FoodPost foodId) {
        this.foodPost = foodId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (foodPost != null ? !foodPost.equals(that.foodPost) : that.foodPost != null) return false;
        return content != null ? content.equals(that.content) : that.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (foodPost != null ? foodPost.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", foodPost=" + foodPost +
                ", content='" + content + '\'' +
                '}';
    }

}
