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
    /** Método que realiza a criação de um Avaliaçao (Review)
     * @param dto Entidade que contém os dados necessários para o cadastro da Avaliaçao. Ex: nota, recomendaçao, etc.
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
    /** Método que realiza a criação de um Avaliaçao de Materia(Review)
     * @param dto Entidade que registrou como uma avaliaçao de materia.
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
     * @param dto Entidade que registrou como uma avaliaçao de usuario.
     * @return Retorna Falso somente em caso de erro durante a criação da entidade.
     * */
    fun reviewUser(userId : Int, dto: ReviewDto) : Boolean {
        if(registerReview(dto)){
            val review = _reviewDAO.getAll().last() as Review
            return _reviewDAO.reviewUser(userId, review.id)
        }
        return false
    }
    /**Lista todos as avaliações de usuarios
     * @return lista todos os avaliações de usuarios
     */
    fun listUserReviews(userId: Int):List<UserReviewDto>{
        val list = _reviewDAO.getAllUserReviews(userId) as List<UserReview>
        var result = mutableListOf<UserReviewDto>()
        for (review in list){
            result.add(review.toDto())
        }
        return result
    }
    /**Lista todos as avaliações de materias
     * @return lista todos os avaliações de materias
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