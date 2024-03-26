package kz.sagengaliyev.univerbackend.pagination

import org.springframework.data.domain.PageRequest

class PageableFactory{
    companion object{
        fun create(pageNumber: Int, pageSize: Int): PageRequest {
            return PageRequest.of(pageNumber - 1, pageSize)
        }
    }
}