package transfertnational.example.transfertnational.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transfertnational.example.transfertnational.beans.CompteBean;
import transfertnational.example.transfertnational.model.TransfertNational;
import transfertnational.example.transfertnational.proxies.MicroserviceCompteProxy;
import transfertnational.example.transfertnational.service.TransfertExcelExporter;
import transfertnational.example.transfertnational.service.TransfertNationalService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/transfert")
public class TransfertNationalController {
    private final TransfertNationalService transfertNationalService;
    @Autowired
    MicroserviceCompteProxy microserviceCompteProxy;
    public TransfertNationalController(TransfertNationalService transfertNationalService) {
        this.transfertNationalService = transfertNationalService;
    }
    ////get all transfert
    @GetMapping("/alltransferts")
    public ResponseEntity<List<TransfertNational>> getAllTransferts () {
        List<TransfertNational> transferts=transfertNationalService.getAllTransfers();
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }
    //get transfert by id Client
    @GetMapping("/all/client/{idClient}")
    public ResponseEntity<List<TransfertNational>> getTransfertByIdClient(@PathVariable("idClient") Long idClient) {
        List<TransfertNational> transferts=transfertNationalService.getTransfertByIdClient(idClient);
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }
    ///get transfert by id Beneficiaire
    @GetMapping("/all/beneficiaire/{idBeneficiaire}")
    public ResponseEntity<List<TransfertNational>> getTransfertByIdBeneficiaire(@PathVariable("idBeneficiaire") Long idBeneficiaire) {
        List<TransfertNational> transferts=transfertNationalService.getTransfertByIdBeneficiaire(idBeneficiaire);
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }
    ///get Transfert By Id client and Id Beneficiaire
    @GetMapping("/all/beneficiaire/{idClient}/{idBeneficiaire}")
    public ResponseEntity<List<TransfertNational>> getTransfertByIdClientAndIdBeneficiaire(@PathVariable("idClient") Long idClient,@PathVariable("idBeneficiaire") Long idBeneficiaire) {
        List<TransfertNational> transferts=transfertNationalService.getTransfertNationalByIdClientAndIdBeneficiaire(idClient,idBeneficiaire);
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }
    ///adding transfert
    @PostMapping("/add")
    public ResponseEntity<TransfertNational> addtransfert(@RequestBody TransfertNational transfert) throws Exception{
        TransfertNational newTransfert = transfertNationalService.addTransfert(transfert);
        return new ResponseEntity<>(newTransfert,HttpStatus.CREATED);
    }
    ////update nombre jours (restitué transfert)
    @PatchMapping("restitue/{id}/{nombreJours}")
    public ResponseEntity<TransfertNational> restituerTransfert(@PathVariable("id") Long id, @PathVariable("nombreJours") int nombreJours){
        TransfertNational transfertNational=transfertNationalService.updateTransfertNombreJours(id,nombreJours);
        transfertNational=transfertNationalService.updateTransfertStatus(id,"restitie");
        return new ResponseEntity<>(transfertNational, HttpStatus.OK);
    }
    /// bloquer un transfert
    @PatchMapping("status/bloque/{id}/{solde}")
    public ResponseEntity<TransfertNational> bloquerTransfert(@PathVariable("id") Long id,@PathVariable("solde") float solde){
        TransfertNational transfertNational=transfertNationalService.updateTransfertStatus(id,"bloqué");
        Long idCompte = transfertNational.getIdCompte();
        microserviceCompteProxy.debiterCompte(idCompte,solde);
        return new ResponseEntity<>(transfertNational, HttpStatus.OK);
    }
    ///// extourner un transfert
    @PatchMapping("status/extoune/{id}/{solde}")
    public ResponseEntity<TransfertNational> extournerTransfert(@PathVariable("id") Long id,@PathVariable("solde") float solde){
        TransfertNational transfertNational=transfertNationalService.updateTransfertStatus(id,"bloqué");
        Long idCompte = transfertNational.getIdCompte();
        microserviceCompteProxy.debiterCompte(idCompte,solde);
        return new ResponseEntity<>(transfertNational, HttpStatus.OK);
    }
    //debloquer un transfert
    @PatchMapping("status/debloqué/{id}/{solde}")
    public ResponseEntity<TransfertNational> debloquerTransfert(@PathVariable("id") Long id,@PathVariable("solde") float solde){
        TransfertNational transfertNational=transfertNationalService.updateTransfertStatus(id,"debloqué");
        Long idCompte = transfertNational.getIdCompte();
        microserviceCompteProxy.crediterCompte(idCompte,solde);
        return new ResponseEntity<>(transfertNational, HttpStatus.OK);
    }

    //get all comptes
    @GetMapping("/compte/all")
    public ResponseEntity<List<CompteBean>> getAllComptes () {
        return  microserviceCompteProxy.getAllCompte();
    }

    /// get compte by compte number

    @GetMapping("/compte/find/{numCompte}")
    ResponseEntity<CompteBean> findCompteBynumCompte(@PathVariable("numCompte") String numCompte){
        return microserviceCompteProxy.findCompteBynumCompte(numCompte);
    }


    @PostMapping("/tranSearch")
    public ResponseEntity<List<TransfertNational>> getTransCrit (    		
    		@RequestParam(required = false) Long idAdmin,
    		@RequestParam(required = false) Long idClient,
    		@RequestParam(required = false) String pi,
    		@RequestParam(required = false) String numGsm,
    		@RequestParam(required = false) String codeTransfert,
    		@RequestParam(required = false) String status) {
        List<TransfertNational> transferts=transfertNationalService.GetTransfertNationalByIdAdminAndIdClientAndPiAndNumGsmAndCodeTransfertAndStatus(idAdmin, idClient, pi, numGsm, codeTransfert, status);
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }

    
    @GetMapping("/export/excel")
    public ResponseEntity<List<TransfertNational>> exportToExcel(HttpServletResponse response,    		
    		@RequestParam(required = false) Long idAdmin,
    		@RequestParam(required = false) Long idClient,
    		@RequestParam(required = false) String pi,
    		@RequestParam(required = false) String numGsm,
    		@RequestParam(required = false) String codeTransfert,
    		@RequestParam(required = false) String status) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=transferts_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<TransfertNational> listTransferts = transfertNationalService.GetTransfertNationalByIdAdminAndIdClientAndPiAndNumGsmAndCodeTransfertAndStatus(idAdmin, idClient, pi, numGsm, codeTransfert, status);
         
        TransfertExcelExporter excelExporter = new TransfertExcelExporter(listTransferts);
         
        excelExporter.export(response);
        return new ResponseEntity<>(listTransferts, HttpStatus.OK);
  
        
    }  
    @GetMapping("/find/{codetransfert}")
    public ResponseEntity<TransfertNational> getTransfertByCodeTransfert(@PathVariable("codetransfert") String codeTransfert) {
    	TransfertNational transferts=transfertNationalService.GetTransfertNationalByCodeTransfert(codeTransfert);
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }
    
    
    
    @PutMapping("/status/{codeTransfert}")
    	public ResponseEntity<TransfertNational> updateTransfert(@PathVariable("codeTransfert") String codeTransfert,@RequestParam(required = true) String status,@RequestParam(required = true) String motif){
    	TransfertNational transfertNational=transfertNationalService.updateTransfert(codeTransfert,status,motif);
      return new ResponseEntity<>(transfertNational, HttpStatus.OK);
  
    }
    @GetMapping("/findAgent/{id}")
    public ResponseEntity<List<TransfertNational>> getTransfertByIdAgent(@PathVariable("id") Long id) {
    	List<TransfertNational> transferts=transfertNationalService.GetTransfertNationalByIdAgent(id);
        return new ResponseEntity<>(transferts, HttpStatus.OK);
    }
    
}
