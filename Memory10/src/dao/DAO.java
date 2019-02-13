package dao;

/**
 * étape 1 : le patron de conception DAO
 *
 * @param <T> pour sérialisation d'objets de type T
 */
public abstract class DAO<T> {
		
	/**
	 * Méthode de création d'un objet de type "T",
	 * peut être amené à injecter l'id créé dans le programme
	 * @param obj
	 * @return boolean 
	 */
	public abstract boolean create(T obj);

	/**
	 * Méthode pour effacer selon l'id de l'objet
	 * @param obj
	 * @return boolean 
	 */
	public abstract boolean delete(T obj);

	/**
	 * Méthode de mise à jour selon l'id de l'objet
	 * @param obj
	 * @return boolean
	 */
	public abstract boolean update(T obj);

	/**
	 * Méthode de recherche des informations qui retourne un objet T
	 * @param id
	 * @return T
	 */
	public abstract T read(int id);



}