package com.product_categories.model;

public class Category {
    private Integer categoryId;
    private String categoryName;
    private Integer quantityOfGoods;

    public Category() {
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getQuantityOfGoods() {
        return quantityOfGoods;
    }

    public void setQuantityOfGoods(Integer quantityOfGoods) {
        this.quantityOfGoods = quantityOfGoods;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", quantityOfGoods=" + quantityOfGoods +
                '}';
    }
}
