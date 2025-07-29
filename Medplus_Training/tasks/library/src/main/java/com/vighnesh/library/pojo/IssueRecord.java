package com.vighnesh.library.pojo;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IssueRecord {
	public enum Status {
        ISSUED,
        RETURNED
    }
	private Integer issueid;
    @NotNull(message = "Book ID cannot be null")
    @Positive(message = "Book ID must be positive")
    private Integer bookid;
    @NotNull(message = "Member ID cannot be null")
    @Positive(message = "Member ID must be positive")
    private Integer memberid;
    @NotNull(message = "Issue date cannot be null")
    private Date issuedate;
    private Date returndate;
    private Status status; 
}
