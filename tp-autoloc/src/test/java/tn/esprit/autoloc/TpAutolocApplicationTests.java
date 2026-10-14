package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.domain.enums.*;
import tn.esprit.autoloc.repository.*;

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
    }




