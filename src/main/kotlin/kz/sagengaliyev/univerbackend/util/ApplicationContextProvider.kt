package kz.sagengaliyev.univerbackend.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class ApplicationContextProvider(
    private var context: ApplicationContext
) : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    companion object{
        private lateinit var context: ApplicationContext

        fun getContext() : ApplicationContext{
            return context
        }
    }

}