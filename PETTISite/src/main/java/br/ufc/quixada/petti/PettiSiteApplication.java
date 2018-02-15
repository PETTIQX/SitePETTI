package br.ufc.quixada.petti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import br.ufc.quixada.petti.util.CriptUtil;

@SpringBootApplication
public class PettiSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PettiSiteApplication.class, args);
		//System.out.println(CriptUtil.hashSenha("carla"));
	}
}
