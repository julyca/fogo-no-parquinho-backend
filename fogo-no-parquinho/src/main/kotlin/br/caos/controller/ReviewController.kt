package br.caos.controller

import br.caos.dao.ReviewDAO
import br.caos.models.Review
import br.caos.models.User
import br.caos.models.UserReview
import br.caos.view.ReviewDto
import br.caos.view.SubjectDto
import br.caos.view.UserDto
import br.caos.view.UserReviewDto
import java.util.*

class ReviewController {
    private val _reviewDAO : ReviewDAO = ReviewDAO()

    private fun registerReview(dto : ReviewDto) : Boolean {
        var review = Review(0, dto.score, dto.feedback, dto.reviewerId, Date())
        var result = true
        try {
            _reviewDAO.insert(review)
        }catch (ex : Exception){
            result = false
            ex.printStackTrace()
        }
        return result
    }

    fun reviewSubject(subjectId : Int, dto: ReviewDto) : Boolean {
        if(registerReview(dto)){
            val review = _reviewDAO.getAll().last() as Review
            return _reviewDAO.reviewSubject(subjectId, review.id)
        }
        return false
    }

    fun reviewUser(userId : Int, dto: ReviewDto) : Boolean {
        if(registerReview(dto)){
            val review = _reviewDAO.getAll().last() as Review
            return _reviewDAO.reviewUser(userId, review.id)
        }
        return false
    }

    fun listUserReviews(userId: Int):List<UserReviewDto>{
        val list = _reviewDAO.getAllUserReviews(userId) as List<UserReview>
        var result = mutableListOf<UserReviewDto>()
        for (review in list){
            result.add(review.toDto())
        }
        return result
    }
}