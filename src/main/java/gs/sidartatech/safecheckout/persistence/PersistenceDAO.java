package gs.sidartatech.safecheckout.persistence;

import java.util.List;

public interface PersistenceDAO<K, E> {
	E getByKey(K k);
	void insert(E e);
	void update(E e);
	List<E> getAll();
}
