package repository;

import model.FormaPagamento;
import model.Orcamento;
import model.Pagamento;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PagamentoRepository {

    public Pagamento criar(Orcamento orcamento, double valorPago, LocalDate dataPagamento, FormaPagamento formaPagamento) {
        Pagamento p = new Pagamento(orcamento, valorPago, dataPagamento, formaPagamento);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(p);
                tx.commit();
                return p;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Pagamento salvar(Pagamento pagamento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Pagamento salvo = session.merge(pagamento);
                tx.commit();
                return salvo;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Optional<Pagamento> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Carrega orcamento → cliente e veiculo em uma única query
            return session.createQuery(
                    "FROM Pagamento p " +
                    "JOIN FETCH p.orcamento o " +
                    "JOIN FETCH o.cliente " +
                    "JOIN FETCH o.veiculo " +
                    "WHERE p.id = :id",
                    Pagamento.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }

    public List<Pagamento> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Pagamento p " +
                    "JOIN FETCH p.orcamento o " +
                    "JOIN FETCH o.cliente " +
                    "JOIN FETCH o.veiculo " +
                    "ORDER BY p.dataPagamento DESC",
                    Pagamento.class)
                    .getResultList();
        }
    }

    public boolean remover(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Pagamento p = session.get(Pagamento.class, id);
                if (p == null) { tx.rollback(); return false; }
                session.remove(p);
                tx.commit();
                return true;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
