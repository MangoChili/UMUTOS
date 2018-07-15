package com.example.ding.umutos.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.ding.umutos.objects.Book;
import com.example.ding.umutos.persistence.WishListPersistence;


public class WishListPersistenceHSQLDB implements WishListPersistence {

    private final String dbPath;

    public WishListPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Book fromResultSet(final ResultSet rs) throws SQLException {
        int bookID = rs.getInt("bookID");
        String bookName = rs.getString("bookName");
        String authorName = rs.getString("authorName");
        int bookPicture = rs.getInt("bookPicture");
        String bookDescription = rs.getString("bookDescription");
        String bookCategory = rs.getString("bookCategory");
        Double price = rs.getDouble("price");
        String ownerName = rs.getString("ownerName");

        Book book = new Book(bookName, authorName, bookPicture, bookDescription, bookCategory, price, ownerName);
        book.setBookID(bookID);
        return book;
    }

    @Override
    public List<Book> getWishListSequential() {
        final List<Book> wishList = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM wishList");
            while (rs.next()) {
                final Book book = fromResultSet(rs);
                wishList.add(book);
            }
            rs.close();
            st.close();

            return wishList;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    @Override
    public void insertWishList(Book currentBook,String userName) {
        getWishListSequential();
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO wishList VALUES(?,?,?,?,?,?,?,?,?)");
            st.setString(1,currentBook.getName() );
            st.setInt(2,currentBook.getBookID());
            st.setString(3, currentBook.getName());
            st.setString(4, currentBook.getAuthor());
            st.setInt(5, currentBook.getPicture());
            st.setString(6, currentBook.getDescription());
            st.setString(7, currentBook.getCategory());
            st.setDouble(8, currentBook.getPrice());
            st.setString(9, currentBook.getOwner());

            st.executeUpdate();

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }


    @Override
    public Book searchWishList(int id) {
        Book book = null;
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM wishList WHERE bookID = ?");
            st.setInt(1, id);

            final ResultSet rs = st.executeQuery();

            if (rs.next()) {
                book = fromResultSet(rs);
            }

            rs.close();
            st.close();

            return book;

        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<Book> getUserWishListSequential(String userName) {
        final List<Book> wishList = new ArrayList<>();
        try (final Connection c = connection()){
            final PreparedStatement st = c.prepareStatement("SELECT * FROM wishList WHERE userName= ?");
            st.setString(1, userName);

            final ResultSet rs = st.executeQuery();
            while(rs.next()) {
                final Book book = fromResultSet(rs);
                wishList.add(book);
            }

            rs.close();
            st.close();

            return wishList;
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteWishList(int id ,String userName) {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM wishList WHERE bookID = ? AND userName = ?");
            st.setInt(1, id);
            st.setString(2,userName);
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new PersistenceException(e);
        }
    }
}