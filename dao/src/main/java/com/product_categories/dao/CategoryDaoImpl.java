package com.product_categories.dao;

import com.product_categories.model.Category;
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

public class CategoryDaoImpl implements CategoryDao{

   CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
   CategoryUpdateRowMapper categoryUpdateRowMapper = new CategoryUpdateRowMapper();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CategoryDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public List<Category> findAll() {
        return namedParameterJdbcTemplate
                .query("select categories.category_id, categories.category_name, count(products.product_id) as quantity_of_goods " +
                        "from categories left join products on categories.category_id = products.category_categoryId group by categories.category_name;", categoryRowMapper);
    }

    @Override
    public Optional<Category> findById(Integer categoryId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("categoryId", categoryId);
        Category category = namedParameterJdbcTemplate.queryForObject("select category_id, category_name " +
                "from categories where category_id = :categoryId", sqlParameterSource, categoryUpdateRowMapper);
        return Optional.ofNullable(category);
    }

    @Override
    public Integer create(Category category) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("categoryName", category.getCategoryName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO categories (category_name) VALUES (:categoryName)", params, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer update(Category category) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", category.getCategoryId());
        params.put("categoryName", category.getCategoryName());
        return namedParameterJdbcTemplate.update("UPDATE categories SET category_name = :categoryName WHERE category_id = :categoryId", params);
    }

    @Override
    public Integer delete(Integer categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();//create/collect data to properties
        mapSqlParameterSource.addValue("categoryId", categoryId);//"categoryId" in properties, value
        return namedParameterJdbcTemplate.update("DELETE FROM categories WHERE category_id = :categoryId", mapSqlParameterSource);
    }

    private class CategoryRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            category.setQuantityOfGoods(rs.getInt("quantity_of_goods"));
            return category;
        }
    }

    private class CategoryUpdateRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            return category;
        }
    }

}
