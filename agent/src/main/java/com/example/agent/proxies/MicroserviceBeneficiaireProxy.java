package com.example.agent.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.agent.beans.BeneficiaireBean;
import com.example.agent.beans.ClientBean;

@FeignClient(name = "beneficiaire-service")
public interface MicroserviceBeneficiaireProxy {

	    
	@GetMapping("/beneficiaire/findid/{id}")
    public ResponseEntity<BeneficiaireBean> getBeneficiaire (@PathVariable("id") Long id);
	    

	    
}
