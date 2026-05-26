package repository;

import model.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

/**
 * ClienteRepository — acesso ao banco para a entidade Cliente.
 *
 * Padrão usado em cada método de escrita:
 *   1. Abre Session  (conexão leve com o banco)
 *   2. Inicia Transaction  (garante atomicidade)
 *   3. Executa operação
 *   4. commit() → confirma  |  rollback() → desfaz se houver erro
 *   5. Session fechada automaticamente pelo try-with-resources
 */
public class ClienteRepository {

    // ─── CRIAR ────────────────────────────────────────────────────────────────
    public Cliente criar(String nome, String cpf, String telefone, String endereco, String email) {
        Cliente cliente = new Cliente(nome, cpf, telefone, endereco, email);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(cliente);   // INSERT INTO clientes (...)
                tx.commit();
                return cliente;             // cliente.id já foi preenchido pelo banco
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // ─── SALVAR (UPDATE) ──────────────────────────────────────────────────────
    public Cliente salvar(Cliente cliente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                // merge(): se existe ID → UPDATE; se id=0 → INSERT
                Cliente salvo = session.merge(cliente);
                tx.commit();
                return salvo;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // ─── BUSCAR POR ID ────────────────────────────────────────────────────────
    public Optional<Cliente> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // session.get() = SELECT * FROM clientes WHERE id = ?
            // Retorna null se não encontrar (por isso o Optional)
            return Optional.ofNullable(session.get(Cliente.class, id));
        }
    }

    // ─── BUSCAR POR CPF ───────────────────────────────────────────────────────
    public Optional<Cliente> buscarPorCpf(String cpf) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: Hibernate Query Language — usa nomes de classe/campo Java, não SQL
            // :cpf é um parâmetro nomeado (previne SQL Injection)
            return session.createQuery(
                    "FROM Cliente c WHERE c.cpf = :cpf", Cliente.class)
                    .setParameter("cpf", cpf)
                    .uniqueResultOptional();
        }
    }

    // ─── LISTAR TODOS ─────────────────────────────────────────────────────────
    public List<Cliente> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // "FROM Cliente" → SELECT * FROM clientes
            return session.createQuery("FROM Cliente ORDER BY nome", Cliente.class)
                    .getResultList();
        }
    }

    // ─── REMOVER ──────────────────────────────────────────────────────────────
    public boolean remover(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Cliente cliente = session.get(Cliente.class, id);
                if (cliente == null) {
                    tx.rollback();
                    return false;
                }
                session.remove(cliente);    // DELETE FROM clientes WHERE id = ?
                tx.commit();
                return true;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
