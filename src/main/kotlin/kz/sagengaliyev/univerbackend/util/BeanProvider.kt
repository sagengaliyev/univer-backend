package kz.sagengaliyev.univerbackend.util

import org.springframework.stereotype.Component

@Component
class BeanProvider {
    companion object{
        fun <T> getBean(clazz: Class<*>) : T {
            @Suppress("UNCHECKED_CAST")
            return ApplicationContextProvider.getContext().getBean(clazz) as T
        }
    }
}