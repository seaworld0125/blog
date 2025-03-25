package co.test.spring

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class MysqlConnector8Test @Autowired constructor(
    val memberRepository: MemberRepository,
) {
    @Test
    fun testVirtualThreadPinning() {

        Thread.ofVirtual().start {
            println("Virtual Thread")
            val member = memberRepository.save(Member(null, "test"))
            memberRepository.delete(Member(member.id, "test"))
        }.join()
    }
}