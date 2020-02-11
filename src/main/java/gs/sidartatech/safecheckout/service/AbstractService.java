package gs.sidartatech.safecheckout.service;

import java.util.List;

public interface AbstractService<K, E> {
	E getByKey(K k);
	void insert(E e);
	void update(E e);
	List<E> getAll();
}
