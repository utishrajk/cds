package com.feisystems.bham.infrastructure;

/**
 * The Interface DtoToDomainEntityMapper.
 *
 * @param <TDto> the generic type
 * @param <TEntity> the generic type
 */
public interface DtoToDomainEntityMapper<TDto, TEntity> {
	
	/**
	 * Map.
	 *
	 * @param dto the dto
	 * @return the t entity
	 */
	TEntity map(TDto dto);
}
