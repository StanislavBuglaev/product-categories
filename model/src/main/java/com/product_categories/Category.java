package com.product_categories;

public class Category {
    private int categoryId;
    private String categoryName;
    private int quantityOfGoods;

    public Category() {
    }

    public Category(int categoryId, String categoryName, int quantityOfGoods) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.quantityOfGoods = quantityOfGoods;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuantityOfGoods() {
        return quantityOfGoods;
    }

    public void setQuantityOfGoods(int quantityOfGoods) {
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
