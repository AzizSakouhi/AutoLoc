# Atelier 5 — Notes sur l'API REST

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| (tous sauf Paiement) | GET /api/{ressources} | findAll() | 200 | — |
| (tous) | GET /api/{ressources}/{id} | findById(id) | 200 | 404 |
| (tous sauf Paiement) | POST /api/{ressources} | create(x) | 201 | 400 |
| (tous sauf Paiement) | PUT /api/{ressources}/{id} | update(id, x) | 200 | 404, 400 |
| (tous sauf Paiement) | DELETE /api/{ressources}/{id} | deleteById(id) | 204 | 404 |

Ressources : contrats, paiements (GET seulement), agences, employes, vehicules, equipements, clients, reservations, maintenances.

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| Contrat.paiements / Paiement.contrat | @JsonManagedReference / @JsonBackReference ("contrat-paiements") | Composition : le paiement se crée dans le JSON de son contrat, le lien retour est reconstruit |
| Contrat.reservation, Agence.employes/vehicules, Client.reservations, Vehicule.reservations/maintenances, Equipement.vehicules | @JsonIgnore | Côté inverse : coupe la boucle de sérialisation |

Différence : @JsonBackReference n'est ignoré qu'à la sérialisation mais reconstruit le lien à la désérialisation (il doit être accompagné de son @JsonManagedReference). @JsonIgnore exclut totalement le champ, dans les deux sens.

## C. Postman et Swagger

- Postman : tester et rejouer des requêtes, vérifier les réponses (ex. PUT sur 9999 renvoie 404, POST /paiements renvoie 405).
- Swagger UI : documenter et explorer le contrat de l'API, synchronisé avec le code (ex. schémas et réponses 201/400/404 visibles sans écrire de requête).

## D. Anomalies SonarQube

| Anomalie | Règle / explication | Correction |
|---|---|---|
| (à compléter) | | |
| (à compléter) | | |

## E. Questions

1. Le DispatcherServlet reçoit toutes les requêtes ; le HandlerMapping retrouve la méthode à partir du verbe HTTP et de l'URL.
2. Le contrôleur ne dépend que de l'interface du service : couplage faible, et il ne contourne pas la logique métier (pas d'accès direct au repository).
3. POST : une ressource est créée, d'où 201. DELETE : rien à renvoyer, d'où 204. Une ressource absente est un cas métier prévu (ResourceNotFoundException traduite en 404), pas une panne serveur (500).
4. Un paiement est l'enfant d'une composition : il se gère via son Contrat. Un POST /api/paiements renvoie 405 Method Not Allowed.
5. Risques : couplage API/base, champs internes exposés, boucles de sérialisation, modification involontaire de colonnes. Contrat.paiements est une composition (l'enfant se crée dans le JSON du parent, donc paire Managed/Back) ; Agence.vehicules est une simple liste inverse (les véhicules se créent seuls avec une référence à l'agence, donc @JsonIgnore).