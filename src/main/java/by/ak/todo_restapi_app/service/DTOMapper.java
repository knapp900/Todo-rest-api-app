package by.ak.todo_restapi_app.service;

public interface DTOMapper<E,D>{
    E toEntity(D dto);
    D toDto(E entity);

}
