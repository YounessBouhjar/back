package transfertnational.example.transfertnational.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


import transfertnational.example.transfertnational.model.TransfertNational;
import transfertnational.example.transfertnational.repository.TransfertNationalRepository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransfertNationalService {
    private final TransfertNationalRepository transfertNationalRepository;

    public TransfertNationalService(TransfertNationalRepository transfertNationalRepository) {
        this.transfertNationalRepository = transfertNationalRepository;
    }
    /// get all transferts
    public List<TransfertNational> getAllTransfers(){
        return transfertNationalRepository.findAll();
    }
    /////get transfert by id
    public TransfertNational findTransfertNationalByID(Long id){
        return transfertNationalRepository.findTransfertNationalById(id);
    }
    ////get transferts by idClient
    public List<TransfertNational> getTransfertByIdClient(Long idClient){
        return transfertNationalRepository.findTransfertNationalByIdClient(idClient);
    }
    ///get transfers by id Beneficiaire
    public List<TransfertNational> getTransfertByIdBeneficiaire(Long idBeneficiaire){
        return transfertNationalRepository.findTransfertNationalByIdBeneficiaire(idBeneficiaire);
    }
    ///get transfert by id beneficiaire and id client
    public List<TransfertNational> getTransfertNationalByIdClientAndIdBeneficiaire(Long idClient,Long idBeneficiaire){
        return transfertNationalRepository.findTransfertNationalByIdClientAndIdBeneficiaire(idClient,idBeneficiaire);
    }
    ////add transfert
    public TransfertNational addTransfert(TransfertNational transfertNational){
    	TransfertNational transfert = transfertNationalRepository.save(transfertNational);
    	Long id = transfert.getId();
    	transfert.setCodeTransfert("837"+id.toString()+RandomStringUtils.random(10- id.toString().length(),false,true));

    	Date currentUtilDate = new Date();
//    	Timestamp ts=new Timestamp(currentUtilDate.getTime()); 
//    	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//    	transfert.setDateTransfert(formatter.format(currentUtilDate).toString());
    	transfert.setDateTransfert(currentUtilDate);
    	transfert.setStatus("à servir");
    	return transfertNationalRepository.save(transfert);
    }
    ///update nombre jours
    public TransfertNational updateTransfertNombreJours(Long id,int nombreJours){
        TransfertNational transfertNational= transfertNationalRepository.findTransfertNationalById(id);
        transfertNational.setNombreJours(nombreJours);
        return transfertNationalRepository.save(transfertNational);
    }
    ///update status
    public TransfertNational updateTransfertStatus(Long id,String status){
        TransfertNational transfertNational= transfertNationalRepository.findTransfertNationalById(id);
        transfertNational.setStatus(status);
        return transfertNationalRepository.save(transfertNational);
    }

    public List<TransfertNational> GetTransfertNationalByIdAdminAndIdClientAndPiAndNumGsmAndCodeTransfertAndStatus(
    		Long idAdmin,Long idClient,String pi,String numGsm ,String codeTransfert,String status){
        return transfertNationalRepository.findTransfertNationalByIdAdminAndIdClientAndPiAndNumGsmAndCodeTransfertAndStatus(
        		idAdmin, idClient,pi, numGsm, codeTransfert, status);
    }
    public TransfertNational GetTransfertNationalByCodeTransfert(String codeTransfert){
        return transfertNationalRepository.findTransfertNationalByCodeTransfert(codeTransfert);
    }
    
    public List<TransfertNational> GetTransfertNationalByIdAgent(Long id){
        return transfertNationalRepository.findTransfertNationalByIdAgent(id);
    }
    
    public TransfertNational updateTransfert(String codeTransfert,String status,String motif){
        TransfertNational transfertNational= transfertNationalRepository.findTransfertNationalByCodeTransfert(codeTransfert);        
         transfertNational.setStatus(status);
         transfertNational.setMotif(motif);
        return transfertNationalRepository.save(transfertNational);
    }
    
    
    

}


