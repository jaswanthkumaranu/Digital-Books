package com.digitalbooks.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "tbl_subscribe")
@JsonDeserialize(as = BookVo.class)
public class SubscribeBookVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscribe_id")
	private Long SubscribeId;
	@Column(name = "reader_id")
	private String readerId;
	@Column(name = "book_id")
	private Long bookId;
//	@ManyToOne
//	@JoinColumn(name = "book_id_fk")
//	private BookVo book;
	@Column(name = "subscription_dt")
	private Date subscriptionDt;
	@Column(name = "is_active")
	private Long isActive;
	@Column(name = "email_id")
	private String emailId;

	public Long getSubscribeId() {
		return SubscribeId;
	}

	public void setSubscribeId(Long subscribeId) {
		SubscribeId = subscribeId;
	}

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Date getSubscriptionDt() {
		return subscriptionDt;
	}

	public void setSubscriptionDt(Date subscriptionDt) {
		this.subscriptionDt = subscriptionDt;
	}

	public Long getIsActive() {
		return isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "SubscribeBookVo [SubscribeId=" + SubscribeId + ", readerId=" + readerId + ", bookId=" + bookId
				+ ", subscriptionDt=" + subscriptionDt + ", isActive=" + isActive + ", emailId=" + emailId + "]";
	}

	public SubscribeBookVo() {
		super();
	}

	public SubscribeBookVo(Long subscribeId, String readerId, Long bookId, Date subscriptionDt, Long isActive,
			String emailId) {
		this(readerId, bookId, subscriptionDt, isActive, emailId);
		SubscribeId = subscribeId;

	}

	public SubscribeBookVo(String readerId, Long bookId, Date subscriptionDt, Long isActive, String emailId) {
		super();
		this.readerId = readerId;
		this.bookId = bookId;
		this.subscriptionDt = subscriptionDt;
		this.isActive = isActive;
		this.emailId = emailId;
	}

}
