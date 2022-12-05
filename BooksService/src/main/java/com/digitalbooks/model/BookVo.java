package com.digitalbooks.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "tbl_book")
@JsonDeserialize(as = BookVo.class)
public class BookVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Long bookId;
//	@OneToMany(targetEntity = SubscribeBookVo.class, mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<SubscribeBookVo> subscribeList;
	@Column(name = "book_title")
	private String bookTitle;
	@Column(name = "book_code")
	private String bookCode;
	@Column(name = "author_id")
	private String authorId;
	@Column(name = "price")
	private Double price;
	@Column(name = "category")
	private String category;
	@Column(name = "publisher")
	private String publisher;
	@Column(name = "book_logo")
	@Lob
	private Byte[] logo;
	@Column(name = "audio_url")
	private String audiourl;
	@Column(name = "book_content")
	private String bookContent;
	@Column(name = "is_active")
	private Long isActive;
	@Column(name = "upd_dt")
	private Date updatedDt;
	@Column(name = "crt_usr")
	private Long crtUsr;// userIDpk
	@Column(name = "crt_dt")
	private Date createdDt;
	@Column(name = "block")
	private Long block;
//	@Column(name = "subscription")
//	private Long subscription;
//	@Column(name = "subscription_dt")
//	private Date subscriptionDt;
	@Column(name = "subscription_Count")
	private Long subscriptionCount;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

//	public List<SubscribeBookVo> getSubscribeList() {
//		return subscribeList;
//	}
//
//	public void setSubscribeList(List<SubscribeBookVo> subscribeList) {
//		this.subscribeList = subscribeList;
//	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Byte[] getLogo() {
		return logo;
	}

	public void setLogo(Byte[] logo) {
		this.logo = logo;
	}

	public String getAudiourl() {
		return audiourl;
	}

	public void setAudiourl(String audiourl) {
		this.audiourl = audiourl;
	}

	public String getBookContent() {
		return bookContent;
	}

	public void setBookContent(String bookContent) {
		this.bookContent = bookContent;
	}

	public Long getIsActive() {
		return isActive;
	}

	public void setIsActive(Long isActive) {
		this.isActive = isActive;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public Long getCrtUsr() {
		return crtUsr;
	}

	public void setCrtUsr(Long crtUsr) {
		this.crtUsr = crtUsr;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getSubscriptionCount() {
		return subscriptionCount;
	}

	public void setSubscriptionCount(Long subscriptionCount) {
		this.subscriptionCount = subscriptionCount;
	}

	@Override
	public String toString() {
		return "BookVo [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookCode=" + bookCode + ", authorId="
				+ authorId + ", price=" + price + ", category=" + category + ", publisher=" + publisher + ", logo="
				+ logo + ", audiourl=" + audiourl + ", bookContent=" + bookContent + ", isActive=" + isActive
				+ ", updatedDt=" + updatedDt + ", crtUsr=" + crtUsr + ", createdDt=" + createdDt + ", block=" + block
				+ ", subscriptionCount=" + subscriptionCount + "]";
	}

	public BookVo(Long bookId, String bookTitle, String bookCode, String authorId, Double price, String category,
			String publisher, Byte[] logo, String audiourl, String bookContent, Long isActive, Date updatedDt,
			Long crtUsr, Date createdDt, Long block, Long subscriptionCount) {
		this(bookTitle, bookCode, authorId, price, category, publisher, logo, bookContent, audiourl, isActive,
				updatedDt, crtUsr, createdDt, block, subscriptionCount);
		this.bookId = bookId;

	}

	public BookVo() {
		super();
	}

	public BookVo(String bookTitle, String bookCode, String authorId, Double price, String category, String publisher,
			Byte[] logo, String audiourl, String bookContent, Long isActive, Date updatedDt, Long crtUsr,
			Date createdDt, Long block, Long subscriptionCount) {
		super();
		this.bookTitle = bookTitle;
		this.bookCode = bookCode;
		this.authorId = authorId;
		this.price = price;
		this.category = category;
		this.publisher = publisher;
		this.logo = logo;
		this.audiourl = audiourl;
		this.bookContent = bookContent;
		this.isActive = isActive;
		this.updatedDt = updatedDt;
		this.crtUsr = crtUsr;
		this.createdDt = createdDt;
		this.block = block;
		this.subscriptionCount = subscriptionCount;
	}

}
