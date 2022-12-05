package com.digitalbooks.dto;

import java.util.Date;

public class BookDto {
	private Long bookId;
	private String bookTitle;
	private String bookCode;
	private String authorId;
	private Double price;
	private String category;
	private String publisher;
	private Byte[] logo;
	private String audiourl;
	private String bookContent;
	private Long isActive;
	private Date updatedDt;
	private Long crtUsr;// userIDpk
	private Date createdDt;
	private Long block;
	private Long subscriptionCount;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

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
		return "BookDto [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookCode=" + bookCode + ", authorId="
				+ authorId + ", price=" + price + ", category=" + category + ", publisher=" + publisher + ", logo="
				+ logo + ", audiourl=" + audiourl + ", bookContent=" + bookContent + ", isActive=" + isActive
				+ ", updatedDt=" + updatedDt + ", crtUsr=" + crtUsr + ", createdDt=" + createdDt + ", block=" + block
				+ ", subscriptionCount=" + subscriptionCount + "]";
	}

	public BookDto(Long bookId, String bookTitle, String bookCode, String authorId, Double price, String category,
			String publisher, Byte[] logo, String audiourl, String bookContent, Long isActive, Date updatedDt,
			Long crtUsr, Date createdDt, Long block, Long subscriptionCount) {
		this(bookTitle, bookCode, authorId, price, category, publisher, logo, bookContent, audiourl, isActive,
				updatedDt, crtUsr, createdDt, block, subscriptionCount);
		this.bookId = bookId;

	}

	public BookDto() {
		super();
	}

	public BookDto(String bookTitle, String bookCode, String authorId, Double price, String category, String publisher,
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

	public BookDto(String bookTitle, String bookCode, String authorId, Double price, String category, String publisher,
			Byte[] logo, String audiourl, String bookContent, Long isActive, Date updatedDt, Long crtUsr,
			Date createdDt) {
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
	}

}
