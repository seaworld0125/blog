package co.test.spring

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberService (
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun update(id: Long, name: String) {
        val member = memberRepository.findById(id)
        member.ifPresent {
            it.name = name
        }
    }
}