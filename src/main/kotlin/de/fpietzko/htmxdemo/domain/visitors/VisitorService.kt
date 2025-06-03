package de.fpietzko.htmxdemo.domain.visitors

import de.fpietzko.htmxdemo.examples.form.model.RegisterFormSubmission
import org.springframework.stereotype.Service

@Service
class VisitorService(
    private val visitorRepository: VisitorRepository,
) {

    fun addVisitor(visitor: Visitor) =
        visitorRepository.add(visitor)


    fun allVisitors() = visitorRepository.allVisitors()

    fun validateSubmission(model: RegisterFormSubmission): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        if (model.name.isBlank()) {
            errors["name"] = "Name is required"
        }
        if (model.name.length < 3) {
            errors["name"] = "Name must be at least 3 characters long"
        }
        if (model.age.isBlank()) {
            errors["age"] = "Age is required"
        }
        when (model.age.toIntOrNull()) {
            null -> errors["age"] = "Age must be a number"
            in Int.MIN_VALUE..17 -> errors["age"] = "You must be at least 18 years old"
            in 100..Int.MAX_VALUE -> errors["age"] = "You must be younger than 100 years"
        }
        return errors
    }
}