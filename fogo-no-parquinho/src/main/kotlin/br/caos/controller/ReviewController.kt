package br.caos.controller

import br.caos.dao.ReviewDAO
import br.caos.models.Review
import br.caos.models.SubjectReview
import br.caos.models.User
import br.caos.models.UserReview
import br.caos.view.*
import java.util.*

class ReviewController {
    private val _reviewDAO : ReviewDAO = ReviewDAO()

    /** Método que realiza a criação de uma Avaliação (Review)
     * @param dto [ReviewDto] Entidade que contém os dados necessários para o cadastro da Avaliação. Ex: nota, recomendação, etc.
     * @return Retorna Falso somente em caso de erro durante a criação da entidade.
     * */
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

    /** Método que realiza a criação de um Avaliação de Matéria (Review)
     * @param subjectId [Int] Identificador da disciplina que está sendo avaliada.
     * @param dto [ReviewDto] Entidade que contém os dados necessários para o cadastro da Avaliação. Ex: nota, recomendação, etc.
     * @return Retorna Falso somente em caso de erro durante a criação da entidade.
     * */
    fun reviewSubject(subjectId : Int, dto: ReviewDto) : Boolean {
        if(registerReview(dto)){
            val review = _reviewDAO.getAll().last() as Review
            return _reviewDAO.reviewSubject(subjectId, review.id)
        }
        return false
    }

    /** Método que realiza a criação de um Avaliaçao de Usuario(Review)
     * @param userId [Int] Identificador do usuário que está sendo avaliado.
     * @param dto [ReviewDto] Entidade que contém os dados necessários para o cadastro da Avaliação. Ex: nota, recomendação, etc.
     * @return Retorna Falso somente em caso de erro durante a criação da entidade.
     * */
    fun reviewUser(userId : Int, dto: ReviewDto) : Boolean {
        if(registerReview(dto)){
            val review = _reviewDAO.getAll().last() as Review
            return _reviewDAO.reviewUser(userId, review.id)
        }
        return false
    }

    /**Método que lista todas as avaliações feitas sobre um usuário.
     * @param userId [Int] Identificador do usuário consultado.
     * @return lista com as avaliações do usuário.
     */
    fun listUserReviews(userId: Int):List<UserReviewDto>{
        val list = _reviewDAO.getAllUserReviews(userId) as List<UserReview>
        var result = mutableListOf<UserReviewDto>()
        for (review in list){
            result.add(review.toDto())
        }
        return result
    }

    /** Método que lista todas as avaliações feitas sobre uma matéria.
     * @param subjectId [Int] Identificador da matéria consultada.
     * @return lista com as avaliações da matéria.
     */
    fun listSubjectReviews(subjectId: Int):List<SubjectReviewDto>{
        val list = _reviewDAO.getAllSubjectReviews(subjectId) as List<SubjectReview>
        var result = mutableListOf<SubjectReviewDto>()
        for (review in list){
            result.add(review.toDto())
        }
        return result
    }
}