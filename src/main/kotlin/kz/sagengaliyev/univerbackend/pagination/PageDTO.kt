package kz.sagengaliyev.univerbackend.pagination

import org.springframework.data.domain.Page

data class PageDTO<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Long
) {
    constructor(page: Page<T>) : this(
        content = page.content,
        pageNumber = page.pageable.pageNumber + 1,
        pageSize = page.pageable.pageSize,
        totalPages = page.totalPages,
        totalElements = page.totalElements
    )
}

