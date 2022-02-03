package transfertnational.example.transfertnational.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import transfertnational.example.transfertnational.beans.CompteBean;

import java.util.List;

@FeignClient(name = "compte-service",url = "localhost:8082/compte")
public interface MicroserviceCompteProxy {
    @GetMapping("/all")
    ResponseEntity<List<CompteBean>> getAllCompte ();
    @GetMapping("/find/{numCompte}")
    ResponseEntity<CompteBean> findCompteBynumCompte(@PathVariable("numCompte") String numCompte);
    @PutMapping("debiter/{id}/{solde}")
    ResponseEntity<CompteBean> debiterCompte(@PathVariable("id") Long id,@PathVariable("solde") float solde);
    @PutMapping("crediter/{id}/{solde}")
    ResponseEntity<CompteBean> crediterCompte(@PathVariable("id") Long id,@PathVariable("solde") float solde);



}