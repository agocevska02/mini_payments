package mk.iwec.mini_payments.mapper;

public interface GeneralMapper<D, E> {
    D entityToDto(E e);

    E dtoToEntity(D d);
}
