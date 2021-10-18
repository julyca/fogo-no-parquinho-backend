package br.caos.controller

import br.caos.dao.ReviewDAO
import br.caos.models.Review
import br.caos.view.ReviewDto
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
}