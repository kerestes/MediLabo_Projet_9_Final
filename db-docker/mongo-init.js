
db = db.getSiblingDB('medilabo')

db.createUser(
    {
        user: "medilabo",
        pwd: "rootroot",
        roles: [
            {
                role: "readWrite",
                db: "medilabo"
            }
        ]
    }
)

db = db.getSiblingDB('medilabo')

const doc = [
    {_id: "65fd4ee72e1ac5efb7782db1", patId: Long("1"),  patient: 'TestNone', note: "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db2", patId: Long("2"),  patient: 'TestBorderline', note: "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db3", patId: Long("2"),  patient: 'TestBorderline', note: "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db4", patId: Long("3"),  patient: 'TestInDanger', note: "Le patient déclare qu'il fume depuis peu", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db5", patId: Long("3"),  patient: 'TestInDanger', note: "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db6", patId: Long("4"),  patient: 'TestEarlyOnset', note: "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db7", patId: Long("4"),  patient: 'TestEarlyOnset', note: "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db8", patId: Long("4"),  patient: 'TestEarlyOnset', note: "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé", _class: 'fr.medilabo.microservice.note.medecin.models.Note'},
    {_id: "65fd4ee72e1ac5efb7782db9", patId: Long("4"),  patient: 'TestEarlyOnset', note: "Taille, Poids, Cholestérol, Vertige et Réaction", _class: 'fr.medilabo.microservice.note.medecin.models.Note'}
]

db.createCollection("notes")
db.notes.insertMany(doc)