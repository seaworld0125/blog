package co.test.spring

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, Long> {
    
    @Query(value = "SELECT * FROM members WHERE id = ?1 AND SLEEP(?2) = 0", nativeQuery = true)
    fun findByIdWithDelay(id: Long, delaySeconds: Int): Member?
}