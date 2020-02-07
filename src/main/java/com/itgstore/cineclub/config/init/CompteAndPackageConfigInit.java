//package com.itgstore.cineclub.config.init;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import com.itgstore.cineclub.config.DiversConfigProperties;
//import com.itgstore.cineclub.domain.PgwCompte;
//import com.itgstore.cineclub.domain.PgwRoleApp;
//import com.itgstore.cineclub.domain.enumeration.EnmPgwUserPortalRole;
//import com.itgstore.cineclub.repository.PgwCompteRepository;
//import com.itgstore.cineclub.repository.PgwRoleAppRepository;
//import com.itgstore.cineclub.service.PgwRoleAppService;
//import com.itgstore.cineclub.service.util.SeqGenUtil;
//
//
//
//
///**
// * Initialisation des données de l'application à son démarrage
// * @author : p.djomga
// * @date : 15 déc. 2018
// *
// */
//@Component
//public class CompteAndPackageConfigInit implements ApplicationListener<ContextRefreshedEvent> {
//	
//	
//	private final Logger log = LoggerFactory.getLogger(CompteAndPackageConfigInit.class);
//
//	@Autowired
//	private PgwCompteRepository compteRepository;
//	
//	@Autowired
//	private SeqGenUtil seqGenUtil;
//
//	@Autowired
//	private DiversConfigProperties diversConfig;
//	
//	@Autowired
//	private PgwRoleAppRepository pgwRoleAppRepository;
//	
//	@Autowired
//	private PgwRoleAppService pgwRoleAppService;
//	
//	public void createRole(PgwRoleApp pgwRoleApp) {		
//		pgwRoleAppRepository.save(pgwRoleApp);
//	}
//	
//	/**
//	 * Création d'un compte a partir de son code et son libelle
//	 * @param libelle
//	 * @param code
//	 */
//	private void createCompte(String libelle, String code) {
//		
//		PgwCompte compte = new PgwCompte();
//		compte.code(code);
//		compte.libelle(libelle);
//		compte.balance(0.0);
//		compte.soldeCredit(0.0);
//		compte.soldeDebit(0.0);
//		compte.estCrediteur(true);
//		compte.estDebiteur(false);		
//		
//		compteRepository.save(compte);		
//	}
//	
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//		
//		PgwCompte compteCommission = compteRepository.findOneByCode(diversConfig.getBaseCpteCommissionPgw());
//		
//		if(compteCommission != null) {
//			log.debug("L'application est deja Initialisée");
//			
//		}else {
//			log.debug("Initialisation de l'application");
//			// Creation du compte de commission PGW
//			createCompte("Compte des commissions PGW", diversConfig.getBaseCpteCommissionPgw() );
//			
//			// Creation du compte Operation Retro Commission
//			createCompte("Compte Operation Retro Commission", diversConfig.getBaseCpteOpRetroCommission() );
//			
//			//creation role Agent
//			  PgwRoleApp pgwRoleAppAgent = new PgwRoleApp();
//			  Set<String> permissionsAgent = new HashSet<>();
//			  pgwRoleAppAgent.setLibelle(EnmPgwUserPortalRole.AGENT.toString());
//			  pgwRoleAppAgent.setDescription(EnmPgwUserPortalRole.AGENT.toString());
//			  permissionsAgent.add(EnmPgwUserPortalRole.AGENT.toString());
//			  pgwRoleAppAgent.setRoles(pgwRoleAppService.authoritiesFromStrings(permissionsAgent));
//			  createRole(pgwRoleAppAgent);
//			//creation role Agent admin
//			  PgwRoleApp pgwRoleAppAgentAdmin = new PgwRoleApp();
//			  Set<String> permissionsAgentAdmin =  new HashSet<>();
//			  pgwRoleAppAgentAdmin.setLibelle(EnmPgwUserPortalRole.AGENT_ADMIN.toString());
//			  pgwRoleAppAgentAdmin.setDescription(EnmPgwUserPortalRole.AGENT_ADMIN.toString());
//			  permissionsAgentAdmin.add(EnmPgwUserPortalRole.AGENT_ADMIN.toString());
//			  pgwRoleAppAgentAdmin.setRoles(pgwRoleAppService.authoritiesFromStrings(permissionsAgentAdmin));
//			  createRole(pgwRoleAppAgentAdmin);
//			//creation role Agent gerant
//			  PgwRoleApp pgwRoleAppAgentGerant = new PgwRoleApp();
//			  Set<String> permissionsAgentGerant =  new HashSet<>();
//			  pgwRoleAppAgentGerant.setLibelle(EnmPgwUserPortalRole.AGENT_GERANT.toString());
//			  pgwRoleAppAgentGerant.setDescription(EnmPgwUserPortalRole.AGENT_GERANT.toString());
//			  permissionsAgentGerant.add(EnmPgwUserPortalRole.AGENT_GERANT.toString());
//			  pgwRoleAppAgentGerant.setRoles(pgwRoleAppService.authoritiesFromStrings(permissionsAgentGerant));
//			  createRole(pgwRoleAppAgentGerant);
//		}
//		
//	}
//	
//
//}
