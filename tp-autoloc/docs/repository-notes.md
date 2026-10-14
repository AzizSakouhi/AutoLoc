# Atelier 3 — Notes sur la couche Repository

## 1. Choix d'interface pour chaque repository

| Interface              | Étend                          | Justification |
|-------------------------|--------------------------------|----------------|
| IContratRepository      | JpaRepository<Contrat, Long>   | CRUD complet, findAll renvoie une List, saveAndFlush disponible |
| IAgenceRepository       | JpaRepository<Agence, Long>    | CRUD standard, pas de tri/pagination specifique necessaire pour l'instant |
| IEmployeRepository      | JpaRepository<Employe, Long>   | CRUD standard, acces simple par agence |
| IVehiculeRepository     | JpaRepository<Vehicule, Long>  | CRUD complet, utile pour pagination future (liste de vehicules disponibles) |
| IEquipementRepository   | JpaRepository<Equipement, Long>| CRUD standard, relation ManyToMany geree cote Vehicule |
| IClientRepository       | JpaRepository<Client, Long>    | CRUD complet, utile pour recherche et tri (nom, date d'inscription) |
| IReservationRepository  | JpaRepository<Reservation, Long>| CRUD complet, pagination utile (historique des reservations) |
| IPaiementRepository     | JpaRepository<Paiement, Long>  | Lecture seule en pratique : les paiements sont crees/retires via le Contrat parent (cascade) |
| IMaintenanceRepository  | JpaRepository<Maintenance, Long>| CRUD complet, pagination utile pour l'historique de maintenance |