package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.domain.enums.*;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.*;
import tn.esprit.autoloc.service.*;
import tn.esprit.autoloc.web.dto.AgenceDTO;
import tn.esprit.autoloc.web.dto.ClientDTO;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Autowired
    private IEmployeRepository employeRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    @Autowired
    private IEmployeService employeService;

    @Autowired
    private IContratService contratService;

    @Autowired
    private IMaintenanceService maintenanceService;

    @Test
    void contextLoads() {
    }

    // ============================================================
    // TESTS DES REPOSITORIES (Atelier 3)
    // ============================================================
    @Test
    void testAgenceRepository() {
        System.out.println("===== TEST AGENCE REPOSITORY =====");

        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("12 Avenue Habib Bourguiba");
        agence.setTelephone("71123456");

        Agence agenceSauvegardee = agenceRepository.save(agence);
        assertNotNull(agenceSauvegardee);
        assertNotNull(agenceSauvegardee.getIdAgence());
        System.out.println("Agence creee : " + agenceSauvegardee.getNom()
                + " (id = " + agenceSauvegardee.getIdAgence() + ")");

        List<Agence> toutesLesAgences = agenceRepository.findAll();
        System.out.println("Nombre total d'agences : " + toutesLesAgences.size());
        toutesLesAgences.forEach(a -> System.out.println(" - " + a.getNom()));

        Optional<Agence> agenceTrouvee = agenceRepository.findById(agenceSauvegardee.getIdAgence());
        assertTrue(agenceTrouvee.isPresent());
        System.out.println("Agence retrouvee par id : " + agenceTrouvee.get().getNom());

        boolean existe = agenceRepository.existsById(agenceSauvegardee.getIdAgence());
        assertTrue(existe);

        long nombreAgences = agenceRepository.count();
        System.out.println("Nombre d'agences (count) : " + nombreAgences);
        assertTrue(nombreAgences > 0);

        agenceRepository.deleteById(agenceSauvegardee.getIdAgence());
        boolean existeEncore = agenceRepository.existsById(agenceSauvegardee.getIdAgence());
        assertFalse(existeEncore);
        System.out.println("Agence supprimee, existsById renvoie maintenant : " + existeEncore);
    }

    @Test
    void testClientRepository() {
        System.out.println("===== TEST CLIENT REPOSITORY =====");

        Client client1 = new Client();
        client1.setNom("Ben Salah");
        client1.setPrenom("Amine");
        client1.setEmail("amine.bensalah@mail.com");
        client1.setTelephone("20111222");
        client1.setNumPermis("TN123456");
        client1.setDateInscription(LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Sarra");
        client2.setEmail("sarra.trabelsi@mail.com");
        client2.setTelephone("20333444");
        client2.setNumPermis("TN654321");
        client2.setDateInscription(LocalDate.now());

        Client client1Sauvegarde = clientRepository.save(client1);
        Client client2Sauvegarde = clientRepository.save(client2);

        assertNotNull(client1Sauvegarde.getIdClient());
        assertNotNull(client2Sauvegarde.getIdClient());
        System.out.println("Clients crees : " + client1Sauvegarde.getNom()
                + " et " + client2Sauvegarde.getNom());

        List<Client> tousLesClients = clientRepository.findAll();
        System.out.println("Nombre total de clients : " + tousLesClients.size());
        tousLesClients.forEach(c -> System.out.println(" - " + c.getNom() + " " + c.getPrenom()));

        Optional<Client> clientTrouve = clientRepository.findById(client1Sauvegarde.getIdClient());
        assertTrue(clientTrouve.isPresent());
        assertEquals("Ben Salah", clientTrouve.get().getNom());

        boolean existeVrai = clientRepository.existsById(client1Sauvegarde.getIdClient());
        boolean existeFaux = clientRepository.existsById(999999L);
        assertTrue(existeVrai);
        assertFalse(existeFaux);

        long nombreClients = clientRepository.count();
        System.out.println("Nombre de clients (count) : " + nombreClients);
        assertTrue(nombreClients >= 2);
    }

    @Test
    void testVehiculeRepository() {
        System.out.println("===== TEST VEHICULE REPOSITORY =====");

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("123 TUN 4567");
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("80.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule vehiculeSauvegarde = vehiculeRepository.save(vehicule);
        assertNotNull(vehiculeSauvegarde.getIdVehicule());
        System.out.println("Vehicule cree : " + vehiculeSauvegarde.getMarque()
                + " " + vehiculeSauvegarde.getModele()
                + " (id = " + vehiculeSauvegarde.getIdVehicule() + ")");

        List<Vehicule> tousLesVehicules = vehiculeRepository.findAll();
        System.out.println("Nombre total de vehicules : " + tousLesVehicules.size());
        tousLesVehicules.forEach(v -> System.out.println(" - " + v.getMarque() + " " + v.getModele()));

        long nombreVehicules = vehiculeRepository.count();
        assertTrue(nombreVehicules > 0);

        vehiculeSauvegarde.setStatut(StatutVehicule.LOUE);
        vehiculeSauvegarde.setTarifJournalier(new BigDecimal("95.00"));
        Vehicule vehiculeModifie = vehiculeRepository.save(vehiculeSauvegarde);
        assertEquals(StatutVehicule.LOUE, vehiculeModifie.getStatut());

        vehiculeRepository.deleteById(vehiculeModifie.getIdVehicule());
        assertFalse(vehiculeRepository.existsById(vehiculeModifie.getIdVehicule()));
        System.out.println("Vehicule supprime.");
    }

    @Test
    void testEmployeRepository() {
        System.out.println("===== TEST EMPLOYE REPOSITORY =====");

        Employe employe = new Employe();
        employe.setNom("Jlassi");
        employe.setPrenom("Mohamed");
        employe.setRole(RoleEmploye.AGENT);

        Employe employeSauvegarde = employeRepository.save(employe);
        assertNotNull(employeSauvegarde.getIdEmploye());

        Optional<Employe> employeTrouve = employeRepository.findById(employeSauvegarde.getIdEmploye());
        assertTrue(employeTrouve.isPresent());

        assertTrue(employeRepository.existsById(employeSauvegarde.getIdEmploye()));

        employeRepository.deleteById(employeSauvegarde.getIdEmploye());
        assertNull(employeRepository.findById(employeSauvegarde.getIdEmploye()).orElse(null));
        System.out.println("Employe supprime avec succes.");
    }

    @Test
    void testReservationRepository() {
        System.out.println("===== TEST RESERVATION REPOSITORY =====");

        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(3));
        reservation.setStatut(StatutReservation.EN_ATTENTE);

        Reservation reservationSauvegardee = reservationRepository.save(reservation);
        assertNotNull(reservationSauvegardee.getIdReservation());

        List<Reservation> toutes = reservationRepository.findAll();
        System.out.println("Nombre de reservations : " + toutes.size());

        assertTrue(reservationRepository.existsById(reservationSauvegardee.getIdReservation()));

        reservationRepository.deleteById(reservationSauvegardee.getIdReservation());
        assertFalse(reservationRepository.existsById(reservationSauvegardee.getIdReservation()));
        System.out.println("Reservation supprimee avec succes.");
    }

    @Test
    void testMaintenanceRepository() {
        System.out.println("===== TEST MAINTENANCE REPOSITORY =====");

        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDateFin(LocalDate.now().plusDays(2));
        maintenance.setDescription("Vidange et controle freins");

        Maintenance maintenanceSauvegardee = maintenanceRepository.save(maintenance);
        assertNotNull(maintenanceSauvegardee.getIdMaintenance());

        Optional<Maintenance> trouvee = maintenanceRepository.findById(maintenanceSauvegardee.getIdMaintenance());
        assertTrue(trouvee.isPresent());
        assertEquals("Vidange et controle freins", trouvee.get().getDescription());

        maintenanceRepository.deleteById(maintenanceSauvegardee.getIdMaintenance());
        assertFalse(maintenanceRepository.existsById(maintenanceSauvegardee.getIdMaintenance()));
        System.out.println("Maintenance supprimee avec succes.");
    }

    // ============================================================
    // TESTS DES SERVICES (Atelier 4, adaptes aux DTO de l'Atelier 7)
    // ============================================================
    @Test
    void testAgenceService() {
        System.out.println("===== TEST AGENCE SERVICE =====");

        AgenceDTO dto = AgenceDTO.builder().nom("Agence Sfax").ville("Sfax")
                .adresse("5 Rue de la Liberté").telephone("74111222").build();

        AgenceDTO creee = agenceService.create(dto);
        assertNotNull(creee.getId());
        System.out.println("Agence créée : " + creee.getNom() + " (id = " + creee.getId() + ")");

        System.out.println("Nombre d'agences : " + agenceService.findAll().size());

        assertEquals("Agence Sfax", agenceService.findById(creee.getId()).getNom());

        AgenceDTO modif = AgenceDTO.builder().nom("Agence Sfax Centre").ville("Sfax").build();
        assertEquals("Agence Sfax Centre", agenceService.update(creee.getId(), modif).getNom());

        // création avec un id déjà renseigné : refusée
        assertThrows(IllegalArgumentException.class, () -> agenceService.create(creee));

        agenceService.deleteById(creee.getId());
        assertThrows(ResourceNotFoundException.class, () -> agenceService.findById(creee.getId()));
        System.out.println("Agence supprimée : elle n'existe plus.");
    }

    @Test
    void testClientService() {
        System.out.println("===== TEST CLIENT SERVICE =====");

        ClientDTO dto = ClientDTO.builder().nom("Ben Salah").prenom("Amine")
                .email("amine.bensalah@mail.com").telephone("20111222")
                .numPermis("TN123456").dateInscription(LocalDate.now()).build();

        ClientDTO cree = clientService.create(dto);
        assertNotNull(cree.getId());
        System.out.println("Client créé : " + cree.getNom());

        assertEquals("Ben Salah", clientService.findById(cree.getId()).getNom());
        assertTrue(clientService.findAll().size() >= 1);

        clientService.deleteById(cree.getId());
        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(cree.getId()));
        System.out.println("Client supprimé : il n'existe plus.");
    }

    @Test
    void testVehiculeService() {
        System.out.println("===== TEST VEHICULE SERVICE =====");

        AgenceDTO agence = agenceService.create(
                AgenceDTO.builder().nom("Agence Test").ville("Tunis").build());

        VehiculeDTO dto = VehiculeDTO.builder().immatriculation("123 TUN 4567").marque("Renault")
                .modele("Clio").categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(new BigDecimal("80.00")).statut(StatutVehicule.DISPONIBLE)
                .agenceId(agence.getId()).build();

        VehiculeDTO cree = vehiculeService.create(dto);
        assertNotNull(cree.getId());
        assertEquals(agence.getId(), cree.getAgenceId());
        System.out.println("Véhicule créé : " + cree.getMarque() + " " + cree.getModele());

        // désaffectation puis nouvelle affectation
        vehiculeService.desaffecterVehiculeDeAgence(cree.getId());
        assertNull(vehiculeService.findById(cree.getId()).getAgenceId());
        assertThrows(IllegalArgumentException.class,
                () -> vehiculeService.desaffecterVehiculeDeAgence(cree.getId()));
        vehiculeService.affecterVehiculeAAgence(cree.getId(), agence.getId());
        assertEquals(agence.getId(), vehiculeService.findById(cree.getId()).getAgenceId());

        // agence inexistante
        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.affecterVehiculeAAgence(cree.getId(), 999999L));

        vehiculeService.deleteById(cree.getId());
        agenceService.deleteById(agence.getId());
        System.out.println("Véhicule et agence supprimés.");
    }

    @Test
    void testContratService() {
        System.out.println("===== TEST CONTRAT SERVICE =====");

        ContratDTO dto = ContratDTO.builder().dateSignature(LocalDate.now())
                .montantTotal(new BigDecimal("1500.00")).valide(true).build();

        ContratDTO cree = contratService.create(dto);
        assertNotNull(cree.getId());
        System.out.println("Contrat créé, id = " + cree.getId());

        ContratDTO modif = ContratDTO.builder().dateSignature(LocalDate.now())
                .montantTotal(new BigDecimal("1800.00")).valide(true).build();
        ContratDTO misAJour = contratService.update(cree.getId(), modif);
        assertEquals(0, new BigDecimal("1800.00").compareTo(misAJour.getMontantTotal()));

        // création avec un id déjà renseigné : refusée
        assertThrows(IllegalArgumentException.class, () -> contratService.create(misAJour));

        contratService.deleteById(cree.getId());
        assertThrows(ResourceNotFoundException.class, () -> contratService.findById(cree.getId()));
        System.out.println("Contrat supprimé.");
    }

    // Employe et Maintenance : services non migrés vers les DTO (entités)
    @Test
    void testEmployeService() {
        System.out.println("===== TEST EMPLOYE SERVICE =====");

        Employe e = new Employe();
        e.setNom("Jlassi");
        e.setPrenom("Mohamed");
        e.setRole(RoleEmploye.AGENT);

        Employe cree = employeService.create(e);
        assertNotNull(cree.getIdEmploye());

        Employe trouve = employeService.findById(cree.getIdEmploye());
        assertEquals(RoleEmploye.AGENT, trouve.getRole());

        employeService.deleteById(cree.getIdEmploye());
        assertThrows(ResourceNotFoundException.class,
                () -> employeService.findById(cree.getIdEmploye()));
        System.out.println("Employé supprimé.");
    }

    @Test
    void testMaintenanceService() {
        System.out.println("===== TEST MAINTENANCE SERVICE =====");

        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.now());
        m.setDateFin(LocalDate.now().plusDays(2));
        m.setDescription("Vidange et contrôle des freins");

        Maintenance cree = maintenanceService.create(m);
        assertNotNull(cree.getIdMaintenance());

        Maintenance trouvee = maintenanceService.findById(cree.getIdMaintenance());
        assertEquals("Vidange et contrôle des freins", trouvee.getDescription());

        Maintenance invalide = new Maintenance();
        invalide.setDateDebut(LocalDate.now());
        invalide.setDateFin(LocalDate.now().minusDays(1));
        assertThrows(IllegalArgumentException.class, () -> maintenanceService.create(invalide));

        maintenanceService.deleteById(cree.getIdMaintenance());
        assertThrows(ResourceNotFoundException.class,
                () -> maintenanceService.findById(cree.getIdMaintenance()));
        System.out.println("Maintenance supprimée.");
    }
}