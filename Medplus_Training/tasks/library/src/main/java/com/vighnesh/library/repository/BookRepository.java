package com.vighnesh.library.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vighnesh.library.pojo.Book;
import com.vighnesh.library.pojo.Book.Availability;
import com.vighnesh.library.pojo.Book.Status;
import com.vighnesh.library.utility.DBqueries;

@Repository
public class BookRepository {
    
    class BookRowMapper implements RowMapper<Book>{
		@Override
		public Book mapRow(ResultSet rs, int bookId) throws SQLException{
			Book book = new Book();
	        book.setBookid(rs.getInt("bookid"));
	        book.setTitle(rs.getString("title"));
	        book.setAuthor(rs.getString("author"));
	        book.setCategory(rs.getString("category"));
	        book.setStatus(Status.valueOf(rs.getString("status").equals("A") ? "ACTIVE" : "INACTIVE"));
	        book.setAvailability(Availability.valueOf(rs.getString("availability").equals("A") ? "AVAILABLE" : "ISSUED"));
	        return book;
		}
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addBook(Book book) {
        jdbcTemplate.update(DBqueries.INSERT_BOOK,book.getTitle(),book.getAuthor(),book.getCategory(),
        		book.getStatus().toString().substring(0, 1),book.getAvailability().toString().substring(0, 1));
    }

    public void updateBookDetails(Book book) {
        jdbcTemplate.update(DBqueries.UPDATE_BOOK_DETAILS,book.getTitle(),book.getAuthor(),book.getCategory(),
                book.getStatus().toString().substring(0, 1),book.getBookid());
    }

    public void updateBookAvailability(int bookId, Availability availability) {
        jdbcTemplate.update(DBqueries.UPDATE_BOOK_AVAILABILITY,availability.toString().substring(0, 1),bookId);
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query(DBqueries.SELECT_ALL_BOOKS, new BookRowMapper());
    }
    
    public Optional<Book> getBookById(int bookId) {
        try {
            Book book = jdbcTemplate.queryForObject(DBqueries.SELECT_BOOK_BY_ID, new BookRowMapper(), bookId);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    public void deleteById(int bookId) {
        jdbcTemplate.update(DBqueries.DELETE_BOOK, bookId);
    }
}