package com.product_categories.dao;

import com.product_categories.model.Category;
import com.product_categories.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao{

    ProductRowMapper productRowMapper = new ProductRowMapper();
    ProductUpdateRowMapper productUpdateRowMapper = new ProductUpdateRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return namedParameterJdbcTemplate
                .query("select products.product_id, products.product_name, categories.category_name, products.product_price, products.category_categoryId " +
                        "from products inner join categories on products.category_categoryId = categories.category_id group by products.product_id;", productRowMapper);
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("productId", productId);
        Product product = namedParameterJdbcTemplate.queryForObject("select product_id, product_name, product_price " +
                "from products where product_id = :productId", sqlParameterSource, productUpdateRowMapper);
        return Optional.ofNullable(product);
    }

    @Override
    public Integer create(Product product) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("productName", product.getProductName());
        params.addValue("categoryName", product.getCategoryName());
        params.addValue("productPrice", product.getProductPrice());
        params.addValue("categoryId", product.getCategoryId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO products (product_name, product_price, category_categoryId) " +
                "VALUES (:productName, :productPrice,:categoryId)", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(Product product) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", product.getProductId());
        params.put("productName", product.getProductName());
        params.put("productPrice", product.getProductPrice());
        return namedParameterJdbcTemplate.update("UPDATE products SET product_name = :productName, product_price = :productPrice WHERE product_id = :productId", params);
    }

    @Override
    public Integer delete(Integer productId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();//create/collect data to properties
        mapSqlParameterSource.addValue("productId", productId);//"productId" in properties, value
        return namedParameterJdbcTemplate.update("DELETE FROM products WHERE product_id = :productId", mapSqlParameterSource);
    }

    private class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setCategoryName(rs.getString("category_name"));
            product.setProductPrice(rs.getDouble("product_price"));
            product.setCategoryId(rs.getInt("category_categoryId"));
            return product;
        }
    }

    private class ProductUpdateRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setProductPrice(rs.getDouble("product_price"));
            return product;
        }
    }
}
