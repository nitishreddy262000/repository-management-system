package com.repository.repository.dao;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="review")
@Table(name="review")
public class ReviewEntity {

	@EmbeddedId
	ReviewId reviewId;
	
	int marks;
	
	String review;

	public ReviewId getReviewId() {
		return reviewId;
	}

	public void setReviewId(ReviewId reviewId) {
		this.reviewId = reviewId;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	
}
