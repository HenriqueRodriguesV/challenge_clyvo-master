MERGE INTO responsavel (id, nome, email, cpf, data_nasc) KEY(id) VALUES
(1, 'Maria Fernanda Silva', 'maria.fernanda@email.com', '12345678901', '1995-08-10'),
(2, 'Joao Pedro Alves', 'joao.alves@email.com', '23456789012', '1992-04-22');

MERGE INTO med_vet (id, nome, email, cpf, crmv) KEY(id) VALUES
(1, 'Dra. Ana Paula Costa', 'ana.costa@clinicavida.com', '34567890123', 'SP-12345'),
(2, 'Dr. Carlos Henrique Souza', 'carlos.souza@clinicavet.com', '45678901234', 'SP-54321');

MERGE INTO pet (id_pet, nome, descricao, raca, data_nasc, id_resp) KEY(id_pet) VALUES
(1, 'Thor', 'Cachorro com alergia recorrente e perfil preventivo monitorado pela Clyvo', 'Golden Retriever', '2021-03-15', 1),
(2, 'Mel', 'Gata idosa acompanhada por triagem inteligente para risco renal', 'Siamês', '2018-11-02', 2);

MERGE INTO prontuario (id_prontuario, procedimento, data_procedimento, local_atendimento, id_pet, id_med_vet) KEY(id_prontuario) VALUES
(1, 'Triagem inicial por sintomas: coceira intensa e irritação na pele. Encaminhamento para dermatologia veterinária.', '2026-05-20', 'Clínica Parceira Clyvo - Unidade Santana', 1, 1),
(2, 'Consulta preventiva com avaliação de hidratação, apetite e comportamento. Recomendado acompanhamento renal.', '2026-05-20', 'Clínica Parceira Clyvo - Unidade Tucuruvi', 2, 2);
