package transfertnational.example.transfertnational.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import transfertnational.example.transfertnational.model.TransfertNational;

public class TransfertRepositoryCustomImpl implements TransfertRepositoryCustom {

	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TransfertNational> findTransfertNationalByIdAdminAndIdClientAndPiAndNumGsmAndCodeTransfertAndStatus(
    		Long idAdmin,Long idClient,String pi,String numGsm ,
   		 String codeTransfert,String status)
    	 {
    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransfertNational> query = cb.createQuery(TransfertNational.class);
        Root<TransfertNational> transferts = query.from(TransfertNational.class);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (idAdmin != null)
            predicates.add(cb.equal(transferts.get("idAgent"), idAdmin));
        
        
        if (idClient != null)
            predicates.add(cb.equal(transferts.get("idClient"), idClient));
       
        if (pi != null)
            predicates.add(cb.like(transferts.get("pi"), pi.toString()+'%'));
        
        if (numGsm != null)
            predicates.add(cb.like(transferts.get("numGsm"), numGsm.toString()+'%'));
        
        if (codeTransfert != null)
                predicates.add(cb.like(transferts.get("codeTransfert"), codeTransfert.toString()+'%'));
        
        if (status != null)
                predicates.add(cb.like(transferts.get("status"), status.toString()+'%'));
            
        if (!predicates.isEmpty()) {
            query
                    .select(transferts)
                    .where(cb.and(
                            predicates.toArray(new Predicate[predicates.size()])));

        }
        return entityManager.createQuery(query).getResultList();
    
    }
	
}
