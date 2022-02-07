package transfertnational.example.transfertnational.repository;
import transfertnational.example.transfertnational.model.TransfertNational;
import java.util.List;

public interface TransfertRepositoryCustom {

	    List<TransfertNational> findTransfertNationalByIdAdminAndIdClientAndPiAndNumGsmAndCodeTransfertAndStatus(Long idAdmin,Long idClient,String pi,String numGsm ,
	    		 String codeTransfert,String status);
	
}
