package de.fpietzko.htmxdemo.domain.visitors

import com.github.javafaker.Faker
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@Profile("faker")
class FakeVisitorService(
    private val visitorService: VisitorService,
) {
    private val faker = Faker()

    @Scheduled(fixedRate = 5000)
    private fun createFakeVisitor() {
        val visitor = Visitor(
            name = faker.name().fullName(),
            age = faker.number().numberBetween(18, 99),
            knowsKotlin = faker.bool().bool(),
            knowsHtmx = faker.bool().bool(),
            knowsJava = faker.bool().bool(),
            dislikesJavascript = faker.bool().bool(),
        )
        visitorService.addVisitor(visitor)
    }

}