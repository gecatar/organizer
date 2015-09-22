package com.organizer.beans;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.organizer.model.Record;

/**
 * Save, find and edit Record from database.
 *
 * @author GecaTM
 *
 */
@ApplicationScoped
public class RecordsService {

	private static final SessionFactory sessionFactory = new Configuration()
	.configure().buildSessionFactory();

	/**
	 * Find all records which first or last name contains search value.
	 *
	 * @param name
	 *            search value.
	 * @return list with records.
	 */
	@SuppressWarnings("unchecked")
	public List<Record> findByName(String name) {
		Session session = sessionFactory.openSession();
		Query allUsersWithName = session.getNamedQuery("User with name");
		allUsersWithName.setParameter("firstName", "%" + name + "%");
		return allUsersWithName.list();
	}

	/**
	 * Gets record by id.
	 *
	 * @param id
	 * @return
	 */
	public Record getById(long id) {
		return sessionFactory.openSession().get(Record.class, id);
	}

	/**
	 * Return record picture stored in database by id.
	 *
	 * @param id
	 *            record id.
	 * @return record picture.
	 */
	public byte[] getPictureById(long id) {
		Session session = sessionFactory.openSession();
		Record record = session.get(Record.class, id);
		if (record != null) {
			return record.getPhoto();
		}
		return null;
	}

	/**
	 * Finds records by first or last name.
	 *
	 * @param anyName
	 *            first or last name.
	 * @param orderBy
	 *            the order by value.
	 * @param accending
	 *            determine order of result.
	 * @return records which contains anyName.
	 */
	@SuppressWarnings("unchecked")
	public List<Record> getRecordByAnyName(String anyName, String orderBy,
			boolean accending) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Record.class);
		criteria.add(Restrictions.ilike("firstName", anyName));
		if (accending) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria.list();
	}

	@PostConstruct
	public void press() {

	}

	/**
	 * Save record in data base.
	 *
	 * @param record
	 *            record for storing in database.
	 */
	public void saveRecord(Record record) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(record);
		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Search records by criteria.
	 *
	 * @param searchFields
	 * @param orderBy
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Record> searchByCriteria(Map<String, String> searchFields,
			Map<String, Boolean> orderBy) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Record.class);
		for (Entry<String, String> entry : searchFields.entrySet()) {
			String searchArg = "%" + entry.getValue() + "%";
			criteria.add(Restrictions.ilike(entry.getKey(), searchArg));
		}
		for (Entry<String, Boolean> entry : orderBy.entrySet()) {
			if (entry.getValue()) {
				criteria.addOrder(Order.asc(entry.getKey()));
			} else {
				criteria.addOrder(Order.desc(entry.getKey()));
			}
		}
		return criteria.list();
	}

	/**
	 * Update record.
	 *
	 * @param record
	 */
	public void updateRecord(Record record) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.saveOrUpdate(record);
		session.getTransaction().commit();
		session.close();
	}
}
