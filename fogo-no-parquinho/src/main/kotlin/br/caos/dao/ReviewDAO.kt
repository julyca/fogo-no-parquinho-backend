package br.caos.dao

import br.caos.models.Review
import br.caos.models.Subject

class ReviewDAO : GenericDAO {
    override fun insert(element: Any): Boolean {
        var result: Boolean = true
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val review = element as Review
            val preparedStatement = connection.getPreparedStatement("""
                INSERT INTO Review
                (score, feedback, reviewerId, creationTime)
                VALUES (?,?, ?, NOW());
            """.trimIndent())
            preparedStatement?.setInt(1,review.score)
            preparedStatement?.setString(2,review.feedback)
            preparedStatement?.setInt(3,review.reviewerId)

            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            result = false
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }
    override fun get(id: Int): Any {
        var review : Review? = null
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection?.executeQuery("SELECT * FROM Review WHERE id == $id")
            while (resultSet?.next()!!)
                review = Review(
                    resultSet.getInt("id"),
                    resultSet.getInt("score"),
                    resultSet.getString("feedback"),
                    resultSet.getInt("reviewerId"),
                    resultSet.getDate("creationTime"))
        } catch (ex : Exception){
            ex.printStackTrace()
        } finally {
            connection?.close()
            return review!!
        }
    }
    override fun getAll(): List<Any> {
        val review = mutableListOf<Review>()
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val resultSet = connection.executeQuery("SELECT * FROM Review;")
            while (resultSet?.next()!!){
                review.add(
                    Review(
                        resultSet.getInt("id"),
                        resultSet.getInt("score"),
                        resultSet.getString("feedback"),
                        resultSet.getInt("reviewerId"),
                        resultSet.getDate("creationTime")
                    )
                )
            }
        } catch (ex : Exception){
            ex.printStackTrace()
        }
        finally {
            connection?.close()
            return review
        }
    }
    override fun update(element: Any): Boolean {
        var connection : ConnectionDAO? = null
        var review : Review
        var result : Boolean = true
        try {
            connection = ConnectionDAO()
            review = element as Review
            val preparedStatement = connection.getPreparedStatement("""UPDATE Review 
                SET feedback=?, score=?
                WHERE id=?
            """.trimMargin())
            preparedStatement?.setInt(2,review.score)
            preparedStatement?.setString(1,review.feedback)
            preparedStatement?.setInt(4,review.id)
            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            ex.printStackTrace()
            result = false
        } finally {
            connection?.close()
            return result
        }
    }

    override fun delete(id: Int): Boolean {
        var connection : ConnectionDAO? = null
        var result : Boolean = true
        try {
            connection = ConnectionDAO()
            val preparedStatement = connection.getPreparedStatement("DELETE FROM Review WHERE id == ?")
            preparedStatement?.setInt(1,id)
            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            ex.printStackTrace()
            result = false
        } finally {
            connection?.close()
            return result
        }
    }

    fun reviewSubject(subId : Int, reviewId: Int) : Boolean {
        var result: Boolean = true
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val preparedStatement = connection.getPreparedStatement("""
                INSERT INTO SubjectReviews
                (reviewId, reviewedSubjectId)
                VALUES (?,?);
            """.trimIndent())
            preparedStatement?.setInt(2,subId)
            preparedStatement?.setInt(1,reviewId)

            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            result = false
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }

    fun reviewUser(userId : Int, reviewId: Int) : Boolean {
        var result: Boolean = true
        var connection : ConnectionDAO? = null
        try {
            connection = ConnectionDAO()
            val preparedStatement = connection.getPreparedStatement("""
                INSERT INTO UserReviews
                (reviewId, reviewedUserId)
                VALUES (?,?);
            """.trimIndent())
            preparedStatement?.setInt(2,userId)
            preparedStatement?.setInt(1,reviewId)

            preparedStatement?.executeUpdate()
            connection.commit()
        } catch (ex : Exception){
            result = false
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }
}