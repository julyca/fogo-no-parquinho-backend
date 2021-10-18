package br.caos.controller

import br.caos.dao.ReviewDAO
import br.caos.models.Review
import br.caos.view.ReviewDto
import java.util.*

class ReviewController {
    private val _reviewDAO : ReviewDAO = ReviewDAO()

    fun RegisterReview(dto : ReviewDto) : Boolean {
        var review = Review(0, dto.score, dto.feedback, dto.revierId)
        var result = true
        try {
            _reviewDAO.insert(review)
        }catch (ex : Exception){
            result = false
            ex.printStackTrace()
        }
        return result
    }
}