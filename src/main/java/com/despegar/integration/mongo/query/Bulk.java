package com.despegar.integration.mongo.query;

import java.util.ArrayList;
import java.util.List;

import com.despegar.integration.mongo.entities.Bulkeable;
import com.despegar.integration.mongo.entities.GenericIdentifiableEntity;

public class Bulk<T extends GenericIdentifiableEntity<?>>{
	
	
	private List<Bulkeable> operationsList = new ArrayList<Bulkeable>();
	private Boolean orderRequired = Boolean.FALSE; 
	
	public Bulk<T> insert(List<T> entities){
		List<BulkInsert<T>> listToInsert = new ArrayList<BulkInsert<T>>();
		BulkInsert<T> entityToInsert;
		for(T entity:entities){
			entityToInsert = new BulkInsert<T>(entity);
			listToInsert.add(entityToInsert);
		}
		this.operationsList.addAll(listToInsert);
		return this;
	}
	
	public Bulk<T> insert(T entity){
		BulkInsert<T> entityToInsert = new BulkInsert<T>(entity);
		this.operationsList.add(entityToInsert);
		return this;
	}
	
	public BulkFind<T> find(Query query){
		BulkFind<T> findInstance = new BulkFind<T>(query);
		operationsList.add(findInstance);
		return findInstance;
	}
	
	public <X extends Object> BulkFind<T> find(X id){
		Query query = new Query();
		query.equals("_id", id);
		BulkFind<T> findInstance = new BulkFind<T>(query);
		operationsList.add(findInstance);
		return findInstance;
	}

	public Boolean getOrderRequired() {
		return orderRequired;
	}

	public void isOrderRequired(Boolean orderRequired) {
		this.orderRequired = orderRequired;
	}

	public List<Bulkeable> getOperationsList() {
		return operationsList;
	}
	
}
