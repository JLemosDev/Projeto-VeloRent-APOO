package repository;

import model.Categoria;
import model.Veiculo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class VeiculoRepository {

    public Veiculo criar(String marca, String modelo, String placa, int ano, Categoria categoria, double valorDiaria) {
        Veiculo veiculo = new Veiculo(marca, modelo, placa, ano, categoria, valorDiaria);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(veiculo);
                tx.commit();
                return veiculo;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Veiculo salvar(Veiculo veiculo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Veiculo salvo = session.merge(veiculo);
                tx.commit();
                return salvo;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Optional<Veiculo> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Veiculo.class, id));
        }
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Veiculo v WHERE UPPER(v.placa) = UPPER(:placa)", Veiculo.class)
                    .setParameter("placa", placa)
                    .uniqueResultOptional();
        }
    }

    public List<Veiculo> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Veiculo ORDER BY marca, modelo", Veiculo.class)
                    .getResultList();
        }
    }

    public List<Veiculo> listarPorCategoria(Categoria categoria) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Enum pode ser passado direto como parâmetro HQL
            return session.createQuery(
                    "FROM Veiculo v WHERE v.categoria = :cat ORDER BY v.marca", Veiculo.class)
                    .setParameter("cat", categoria)
                    .getResultList();
        }
    }

    public boolean remover(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Veiculo veiculo = session.get(Veiculo.class, id);
                if (veiculo == null) { tx.rollback(); return false; }
                session.remove(veiculo);
                tx.commit();
                return true;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
