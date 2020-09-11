package id.sansets.infood.core.domain.usecase

import id.sansets.infood.core.domain.repository.ICoreRepository
import javax.inject.Inject

class CoreUseCase @Inject constructor(private val repository: ICoreRepository) : ICoreUseCase {

    override fun getFoodCategories() = repository.getFoodCategories()
}