package repository;

import model.Cliente;
import model.Orcamento;
import model.Veiculo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrcamentoRepository {

    public Orcamento criar(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
        Orcamento o = new Orcamento(cliente, veiculo, dataInicio, dataFim);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(o);
                tx.commit();
                return o;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Orcamento salvar(Orcamento orcamento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Orcamento salvo = session.merge(orcamento);
                tx.commit();
                return salvo;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Optional<Orcamento> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // JOIN FETCH garante que cliente e veiculo sejam carregados junto
            // (necessário porque usamos FetchType.LAZY nas entidades)
            return session.createQuery(
                    "FROM Orcamento o JOIN FETCH o.cliente JOIN FETCH o.veiculo WHERE o.id = :id",
                    Orcamento.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public List<Orcamento> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Orcamento o JOIN FETCH o.cliente JOIN FETCH o.veiculo ORDER BY o.dataInicio DESC",
                    Orcamento.class)
                    .getResultList();
        }
    }

    public List<Orcamento> listarPorCliente(int clienteId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Orcamento o JOIN FETCH o.cliente c JOIN FETCH o.veiculo WHERE c.id = :cid",
                    Orcamento.class)
                    .setParameter("cid", clienteId)
                    .getResultList();
        }
    }

    public boolean remover(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Orcamento o = session.get(Orcamento.class, id);
                if (o == null) { tx.rollback(); return false; }
                session.remove(o);
                tx.commit();
                return true;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
