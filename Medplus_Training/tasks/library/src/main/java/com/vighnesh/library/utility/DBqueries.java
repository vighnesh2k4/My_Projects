package com.vighnesh.library.utility;

public class DBqueries {
	private DBqueries() {
		
	}
	public static final String INSERT_BOOK = 
        "INSERT INTO books (title, author, category, status, availability) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_BOOK_LOG = 
        "INSERT INTO books_log SELECT * FROM books WHERE bookid = ?";
    public static final String UPDATE_BOOK_DETAILS = 
        "UPDATE books SET title = ?, author = ?, category = ?, status = ? WHERE bookid = ?";
    public static final String UPDATE_BOOK_AVAILABILITY = 
        "UPDATE books SET availability = ? WHERE bookid = ?";
    public static final String SELECT_ALL_BOOKS = 
        "SELECT * FROM books";
    public static final String SELECT_BOOK_BY_ID="SELECT * FROM books WHERE bookid = ?";
    public static final String DELETE_BOOK="DELETE FROM books WHERE bookid = ?";
    public static final String INSERT_MEMBER = 
        "INSERT INTO members (name, email, mobile, gender, address) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_MEMBER_LOG = 
        "INSERT INTO members_log SELECT * FROM members WHERE memberid = ?";
    public static final String UPDATE_MEMBER = 
        "UPDATE members SET name = ?, email = ?, mobile = ?, gender = ?, address = ? WHERE memberid = ?";
    public static final String SELECT_ALL_MEMBERS = 
        "SELECT * FROM members";
    public static final String SELECT_MEMBER_BY_ID = "SELECT memberid, name, email, mobile, gender, address FROM members WHERE memberid = ?";
    public static final String DELETE_MEMBER = "DELETE FROM members WHERE memberid = ?";
    public static final String SELECT_MEMBER_BY_EMAIL = "SELECT memberid, name, email, mobile, gender, address FROM members WHERE email = ?";
	public static final String CHECK_BOOK_AVAILABILITY = 
		    "SELECT availability FROM books WHERE bookid = ?";
    public static final String INSERT_ISSUE_RECORD = 
    "INSERT INTO issue_records (bookid, memberid, status, issuedate) VALUES (?, ?, 'I', ?)";
    public static final String INSERT_ISSUE_LOG = 
	    "INSERT INTO issue_records_log SELECT * FROM issue_records WHERE issueid = ?";
	public static final String UPDATE_ISSUE_STATUS_RETURN = 
	    "UPDATE issue_records SET status = 'R', returndate = ? WHERE issueid = ?";
	public static final String SELECT_ALL_ISSUED_RECORDS = 
			"SELECT issueid, bookid, memberid, issuedate, returndate, status FROM issue_records";
	public static final String SELECT_ISSUE_RECORD_BY_ID = 
			"SELECT issueid, bookid, memberid, issuedate, returndate, status FROM issue_records WHERE issueid = ?";
	public static final String SELECT_ACTIVE_LOANS_FOR_BOOK = 
			"SELECT COUNT(*) FROM issue_records WHERE bookid = ? AND status = 'I'";
	public static final String SELECT_ACTIVE_LOANS_FOR_MEMBER = 
			"SELECT COUNT(*) FROM issue_records WHERE memberid = ? AND status = 'I'";
}
