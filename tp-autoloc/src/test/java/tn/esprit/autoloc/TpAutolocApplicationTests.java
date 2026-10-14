package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.domain.enums.*;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.*;
import tn.esprit.autoloc.service.*;

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
        // PARTIE 2 — Test guide de IAgenceRepository
        // ============================================================
        @Test
        void testAgenceRepository() {

            System.out.println("===== TEST AGENCE REPOSITORY =====");

            // 1. Creer une instance d'Agence
            Agence agence = new Agence();
            agence.setNom("Agence Tunis Centre");
            agence.setVille("Tunis");
            agence.setAdresse("12 Avenue Habib Bourguiba");
            agence.setTelephone("71123456");

            // 2. Enregistrer avec save()
            Agence agenceSauvegardee = agenceRepository.save(agence);
            assertNotNull(agenceSauvegardee);
            assertNotNull(agenceSauvegardee.getIdAgence());
            System.out.println("Agence creee : " + agenceSauvegardee.getNom()
                    + " (id = " + agenceSauvegardee.getIdAgence() + ")");

            // 3. Afficher toutes les agences avec findAll()
            List<Agence> toutesLesAgences = agenceRepository.findAll();
            System.out.println("Nombre total d'agences : " + toutesLesAgences.size());
            toutesLesAgences.forEach(a -> System.out.println(" - " + a.getNom()));

            // 4. Recuperer l'agence avec findById()
            Optional<Agence> agenceTrouvee = agenceRepository.findById(agenceSauvegardee.getIdAgence());
            assertTrue(agenceTrouvee.isPresent());
            System.out.println("Agence retrouvee par id : " + agenceTrouvee.get().getNom());

            // 5. Verifier l'existence avec existsById()
            boolean existe = agenceRepository.existsById(agenceSauvegardee.getIdAgence());
            assertTrue(existe);
            System.out.println("L'agence existe bien en base : " + existe);

            // 6. Afficher le nombre d'agences avec count()
            long nombreAgences = agenceRepository.count();
            System.out.println("Nombre d'agences (count) : " + nombreAgences);
            assertTrue(nombreAgences > 0);

            // 7. Supprimer avec deleteById() puis verifier
            agenceRepository.deleteById(agenceSauvegardee.getIdAgence());
            boolean existeEncore = agenceRepository.existsById(agenceSauvegardee.getIdAgence());
            assertFalse(existeEncore);
            System.out.println("Agence supprimee, existsById renvoie maintenant : " + existeEncore);
        }

        // ============================================================
        // PARTIE 3 — Test guide de IClientRepository
        // ============================================================
        @Test
        void testClientRepository() {

            System.out.println("===== TEST CLIENT REPOSITORY =====");

            // 1. Creer deux clients
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

            // 2. Enregistrer les deux clients
            Client client1Sauvegarde = clientRepository.save(client1);
            Client client2Sauvegarde = clientRepository.save(client2);

            assertNotNull(client1Sauvegarde.getIdClient());
            assertNotNull(client2Sauvegarde.getIdClient());
            System.out.println("Clients crees : " + client1Sauvegarde.getNom()
                    + " et " + client2Sauvegarde.getNom());

            // 3. Afficher la liste avec findAll()
            List<Client> tousLesClients = clientRepository.findAll();
            System.out.println("Nombre total de clients : " + tousLesClients.size());
            tousLesClients.forEach(c -> System.out.println(" - " + c.getNom() + " " + c.getPrenom()));

            // 4. Rechercher un client avec findById()
            Optional<Client> clientTrouve = clientRepository.findById(client1Sauvegarde.getIdClient());
            assertTrue(clientTrouve.isPresent());
            assertEquals("Ben Salah", clientTrouve.get().getNom());
            System.out.println("Client retrouve : " + clientTrouve.get().getNom());

            // 5. Tester existsById() avec id existant puis inexistant
            boolean existeVrai = clientRepository.existsById(client1Sauvegarde.getIdClient());
            boolean existeFaux = clientRepository.existsById(999999L);
            assertTrue(existeVrai);
            assertFalse(existeFaux);
            System.out.println("existsById(id existant) = " + existeVrai);
            System.out.println("existsById(id inexistant) = " + existeFaux);

            // 6. Afficher le nombre de clients avec count()
            long nombreClients = clientRepository.count();
            System.out.println("Nombre de clients (count) : " + nombreClients);
            assertTrue(nombreClients >= 2);
        }

        // ============================================================
        // PARTIE 4 — Test guide de IVehiculeRepository
        // ============================================================
        @Test
        void testVehiculeRepository() {

            System.out.println("===== TEST VEHICULE REPOSITORY =====");

            // 1. Creer un vehicule
            Vehicule vehicule = new Vehicule();
            vehicule.setImmatriculation("123 TUN 4567");
            vehicule.setMarque("Renault");
            vehicule.setModele("Clio");
            vehicule.setCategorie(CategorieVehicule.CITADINE);
            vehicule.setTarifJournalier(new BigDecimal("80.00"));
            vehicule.setStatut(StatutVehicule.DISPONIBLE);

            // 2. Enregistrer avec save()
            Vehicule vehiculeSauvegarde = vehiculeRepository.save(vehicule);
            assertNotNull(vehiculeSauvegarde.getIdVehicule());
            System.out.println("Vehicule cree : " + vehiculeSauvegarde.getMarque()
                    + " " + vehiculeSauvegarde.getModele()
                    + " (id = " + vehiculeSauvegarde.getIdVehicule() + ")");

            // 3. Afficher tous les vehicules avec findAll()
            List<Vehicule> tousLesVehicules = vehiculeRepository.findAll();
            System.out.println("Nombre total de vehicules : " + tousLesVehicules.size());
            tousLesVehicules.forEach(v -> System.out.println(" - " + v.getMarque() + " " + v.getModele()));

            // 4. Afficher le nombre de vehicules avec count()
            long nombreVehicules = vehiculeRepository.count();
            System.out.println("Nombre de vehicules (count) : " + nombreVehicules);
            assertTrue(nombreVehicules > 0);

            // 5. Modifier une propriete puis save()
            vehiculeSauvegarde.setStatut(StatutVehicule.LOUE);
            vehiculeSauvegarde.setTarifJournalier(new BigDecimal("95.00"));
            Vehicule vehiculeModifie = vehiculeRepository.save(vehiculeSauvegarde);
            assertEquals(StatutVehicule.LOUE, vehiculeModifie.getStatut());
            System.out.println("Vehicule modifie, nouveau statut : " + vehiculeModifie.getStatut()
                    + ", nouveau tarif : " + vehiculeModifie.getTarifJournalier());

            // 6. Supprimer avec deleteById()
            vehiculeRepository.deleteById(vehiculeModifie.getIdVehicule());
            boolean existeEncore = vehiculeRepository.existsById(vehiculeModifie.getIdVehicule());
            assertFalse(existeEncore);
            System.out.println("Vehicule supprime : existsById = " + existeEncore);
        }

        // ============================================================
        // PARTIE 5 — Exercice autonome (3 repositories au choix)
        // Choix : IEmployeRepository, IReservationRepository, IMaintenanceRepository
        // ============================================================
        @Test
        void testEmployeRepository() {

            System.out.println("===== TEST EMPLOYE REPOSITORY (exercice autonome) =====");

            Employe employe = new Employe();
            employe.setNom("Jlassi");
            employe.setPrenom("Mohamed");
            employe.setRole(RoleEmploye.AGENT);

            Employe employeSauvegarde = employeRepository.save(employe);
            assertNotNull(employeSauvegarde.getIdEmploye());
            System.out.println("Employe cree : " + employeSauvegarde.getNom());

            Optional<Employe> employeTrouve = employeRepository.findById(employeSauvegarde.getIdEmploye());
            assertTrue(employeTrouve.isPresent());
            System.out.println("Employe retrouve : " + employeTrouve.get().getNom());

            assertTrue(employeRepository.existsById(employeSauvegarde.getIdEmploye()));

            employeRepository.deleteById(employeSauvegarde.getIdEmploye());
            assertNull(employeRepository.findById(employeSauvegarde.getIdEmploye()).orElse(null));
            System.out.println("Employe supprime avec succes.");
        }

        @Test
        void testReservationRepository() {

            System.out.println("===== TEST RESERVATION REPOSITORY (exercice autonome) =====");

            Reservation reservation = new Reservation();
            reservation.setDateDebut(LocalDate.now());
            reservation.setDateFin(LocalDate.now().plusDays(3));
            reservation.setStatut(StatutReservation.EN_ATTENTE);

            Reservation reservationSauvegardee = reservationRepository.save(reservation);
            assertNotNull(reservationSauvegardee.getIdReservation());
            System.out.println("Reservation creee, id = " + reservationSauvegardee.getIdReservation());

            List<Reservation> toutes = reservationRepository.findAll();
            System.out.println("Nombre de reservations : " + toutes.size());

            assertTrue(reservationRepository.existsById(reservationSauvegardee.getIdReservation()));

            reservationRepository.deleteById(reservationSauvegardee.getIdReservation());
            assertFalse(reservationRepository.existsById(reservationSauvegardee.getIdReservation()));
            System.out.println("Reservation supprimee avec succes.");
        }

        @Test
        void testMaintenanceRepository() {

            System.out.println("===== TEST MAINTENANCE REPOSITORY (exercice autonome) =====");

            Maintenance maintenance = new Maintenance();
            maintenance.setDateDebut(LocalDate.now());
            maintenance.setDateFin(LocalDate.now().plusDays(2));
            maintenance.setDescription("Vidange et controle freins");

            Maintenance maintenanceSauvegardee = maintenanceRepository.save(maintenance);
            assertNotNull(maintenanceSauvegardee.getIdMaintenance());
            System.out.println("Maintenance creee, id = " + maintenanceSauvegardee.getIdMaintenance());

            Optional<Maintenance> trouvee = maintenanceRepository.findById(maintenanceSauvegardee.getIdMaintenance());
            assertTrue(trouvee.isPresent());
            assertEquals("Vidange et controle freins", trouvee.get().getDescription());

            assertTrue(maintenanceRepository.existsById(maintenanceSauvegardee.getIdMaintenance()));

            maintenanceRepository.deleteById(maintenanceSauvegardee.getIdMaintenance());
            assertFalse(maintenanceRepository.existsById(maintenanceSauvegardee.getIdMaintenance()));
            System.out.println("Maintenance supprimee avec succes.");
        }

    // ============================================================
    // PARTIE 2 : AgenceService
    // ============================================================
    @Test
    void testAgenceService() {
        System.out.println("===== TEST AGENCE SERVICE =====");

        Agence agence = new Agence();
        agence.setNom("Agence Sfax");
        agence.setVille("Sfax");
        agence.setAdresse("5 Rue de la Liberté");
        agence.setTelephone("74111222");

        // create
        Agence creee = agenceService.create(agence);
        assertNotNull(creee);
        assertNotNull(creee.getIdAgence());
        System.out.println("Agence créée : " + creee.getNom() + " (id = " + creee.getIdAgence() + ")");

        // findAll
        List<Agence> toutes = agenceService.findAll();
        System.out.println("Toutes les agences (" + toutes.size() + ") :");
        toutes.forEach(a -> System.out.println(" - " + a.getNom()));

        // findById
        Agence trouvee = agenceService.findById(creee.getIdAgence());
        assertEquals("Agence Sfax", trouvee.getNom());
        System.out.println("Agence retrouvée : " + trouvee.getNom());

        // update
        Agence modif = new Agence();
        modif.setNom("Agence Sfax Centre");
        modif.setVille("Sfax");
        modif.setAdresse("5 Rue de la Liberté");
        modif.setTelephone("74111222");
        Agence misAJour = agenceService.update(creee.getIdAgence(), modif);
        assertEquals("Agence Sfax Centre", misAJour.getNom());
        System.out.println("Agence modifiée : " + misAJour.getNom());

        // "count" via findAll
        long nombre = agenceService.findAll().size();
        assertTrue(nombre > 0);
        System.out.println("Nombre d'agences : " + nombre);

        // règle de gestion : nom obligatoire
        Agence sansNom = new Agence();
        assertThrows(IllegalArgumentException.class, () -> agenceService.create(sansNom));

        // deleteById puis vérification
        agenceService.deleteById(creee.getIdAgence());
        assertThrows(ResourceNotFoundException.class,
                () -> agenceService.findById(creee.getIdAgence()));
        System.out.println("Agence supprimée : elle n'existe plus.");
    }
    // ============================================================
    // PARTIE 3 : ClientService
    // ============================================================
    @Test
    void testClientService() {
        System.out.println("===== TEST CLIENT SERVICE =====");

        Client c1 = new Client();
        c1.setNom("Ben Salah");
        c1.setPrenom("Amine");
        c1.setEmail("amine.bensalah@mail.com");
        c1.setTelephone("20111222");
        c1.setNumPermis("TN123456");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sarra");
        c2.setEmail("sarra.trabelsi@mail.com");
        c2.setTelephone("20333444");
        c2.setNumPermis("TN654321");
        c2.setDateInscription(LocalDate.now());

        Client client1 = clientService.create(c1);
        Client client2 = clientService.create(c2);
        assertNotNull(client1.getIdClient());
        assertNotNull(client2.getIdClient());
        System.out.println("Clients créés : " + client1.getNom() + " et " + client2.getNom());

        List<Client> tous = clientService.findAll();
        System.out.println("Liste des clients (" + tous.size() + ") :");
        tous.forEach(c -> System.out.println(" - " + c.getNom() + " " + c.getPrenom()));

        Client trouve = clientService.findById(client1.getIdClient());
        assertEquals("Ben Salah", trouve.getNom());
        System.out.println("Client retrouvé : " + trouve.getNom());

        long nombre = clientService.findAll().size();
        assertTrue(nombre >= 2);
        System.out.println("Nombre de clients : " + nombre);

        // règle de gestion : nom et e-mail obligatoires
        Client invalide = new Client();
        assertThrows(IllegalArgumentException.class, () -> clientService.create(invalide));

        // suppression des deux clients de test
        clientService.deleteById(client1.getIdClient());
        clientService.deleteById(client2.getIdClient());
        assertThrows(ResourceNotFoundException.class,
                () -> clientService.findById(client1.getIdClient()));
        System.out.println("Clients supprimés : ils n'existent plus.");
    }

    // ============================================================
    // PARTIE 4 : VehiculeService
    // ============================================================
    @Test
    void testVehiculeService() {
        System.out.println("===== TEST VEHICULE SERVICE =====");

        Vehicule v = new Vehicule();
        v.setImmatriculation("123 TUN 4567");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("80.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule cree = vehiculeService.create(v);
        assertNotNull(cree.getIdVehicule());
        System.out.println("Véhicule créé : " + cree.getMarque() + " " + cree.getModele()
                + " (id = " + cree.getIdVehicule() + ")");

        List<Vehicule> tous = vehiculeService.findAll();
        System.out.println("Tous les véhicules (" + tous.size() + ") :");
        tous.forEach(x -> System.out.println(" - " + x.getMarque() + " " + x.getModele()));

        Vehicule trouve = vehiculeService.findById(cree.getIdVehicule());
        assertEquals("Clio", trouve.getModele());
        System.out.println("Véhicule retrouvé : " + trouve.getModele());

        long nombre = vehiculeService.findAll().size();
        assertTrue(nombre > 0);
        System.out.println("Nombre de véhicules : " + nombre);

        // règle de gestion : tarif non négatif
        Vehicule negatif = new Vehicule();
        negatif.setTarifJournalier(new BigDecimal("-10"));
        assertThrows(IllegalArgumentException.class, () -> vehiculeService.create(negatif));

        vehiculeService.deleteById(cree.getIdVehicule());
        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.findById(cree.getIdVehicule()));
        System.out.println("Véhicule supprimé : il n'existe plus.");
    }

    // ============================================================
    // PARTIE 5 : exercice autonome (Employe, Contrat, Maintenance)
    // ============================================================
    @Test
    void testEmployeService() {
        System.out.println("===== TEST EMPLOYE SERVICE =====");

        Employe e = new Employe();
        e.setNom("Jlassi");
        e.setPrenom("Mohamed");
        e.setRole(RoleEmploye.AGENT);

        Employe cree = employeService.create(e);
        assertNotNull(cree.getIdEmploye());
        System.out.println("Employé créé : " + cree.getNom());

        Employe trouve = employeService.findById(cree.getIdEmploye());
        assertEquals(RoleEmploye.AGENT, trouve.getRole());
        System.out.println("Employé retrouvé : " + trouve.getNom() + " (" + trouve.getRole() + ")");

        System.out.println("Nombre d'employés : " + employeService.findAll().size());

        employeService.deleteById(cree.getIdEmploye());
        assertThrows(ResourceNotFoundException.class,
                () -> employeService.findById(cree.getIdEmploye()));
        System.out.println("Employé supprimé.");
    }

    @Test
    void testContratService() {
        System.out.println("===== TEST CONTRAT SERVICE =====");

        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(new BigDecimal("1500.00"));
        c.setValide(true);

        Contrat cree = contratService.create(c);
        assertNotNull(cree.getIdContrat());
        System.out.println("Contrat créé, id = " + cree.getIdContrat());

        Contrat trouve = contratService.findById(cree.getIdContrat());
        assertEquals(0, new BigDecimal("1500.00").compareTo(trouve.getMontantTotal()));
        System.out.println("Contrat retrouvé, montant = " + trouve.getMontantTotal());

        // update
        Contrat modif = new Contrat();
        modif.setDateSignature(LocalDate.now());
        modif.setMontantTotal(new BigDecimal("1800.00"));
        modif.setValide(true);
        Contrat misAJour = contratService.update(cree.getIdContrat(), modif);
        assertEquals(0, new BigDecimal("1800.00").compareTo(misAJour.getMontantTotal()));
        System.out.println("Contrat modifié, nouveau montant = " + misAJour.getMontantTotal());

        // règle de gestion : montant non négatif
        Contrat negatif = new Contrat();
        negatif.setMontantTotal(new BigDecimal("-5"));
        assertThrows(IllegalArgumentException.class, () -> contratService.create(negatif));

        // création avec un id déjà renseigné : refusée
        assertThrows(IllegalArgumentException.class, () -> contratService.create(misAJour));

        contratService.deleteById(cree.getIdContrat());
        assertThrows(ResourceNotFoundException.class,
                () -> contratService.findById(cree.getIdContrat()));
        System.out.println("Contrat supprimé.");
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
        System.out.println("Maintenance créée, id = " + cree.getIdMaintenance());

        Maintenance trouvee = maintenanceService.findById(cree.getIdMaintenance());
        assertEquals("Vidange et contrôle des freins", trouvee.getDescription());
        System.out.println("Maintenance retrouvée : " + trouvee.getDescription());

        // règle de gestion : date de fin non antérieure à la date de début
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

}




