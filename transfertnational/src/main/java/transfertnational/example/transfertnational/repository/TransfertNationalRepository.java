package transfertnational.example.transfertnational.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import transfertnational.example.transfertnational.model.TransfertNational;

import java.util.List;

@Repository
public interface TransfertNationalRepository extends JpaRepository<TransfertNational,Long>,TransfertRepositoryCustom {
     List<TransfertNational> findTransfertNationalByIdClient(Long idClient);
     List<TransfertNational> findTransfertNationalByIdBeneficiaire(Long idBeneficiaire);
     List<TransfertNational> findTransfertNationalByIdClientAndIdBeneficiaire(Long idClient,Long idBeneficiaire);
     TransfertNational findTransfertNationalById(Long id);
     TransfertNational findTransfertNationalByCodeTransfert(String codeTransfert);

   
}



