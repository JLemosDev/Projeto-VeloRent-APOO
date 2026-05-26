package repository;

import model.Disponibilidade;
import model.Veiculo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DisponibilidadeRepository {

    public Disponibilidade criar(Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim, boolean disponivel) {
        Disponibilidade d = new Disponibilidade(veiculo, dataInicio, dataFim, disponivel);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(d);
                tx.commit();
                return d;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Disponibilidade salvar(Disponibilidade disponibilidade) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Disponibilidade salvo = session.merge(disponibilidade);
                tx.commit();
                return salvo;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Optional<Disponibilidade> buscarPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Disponibilidade.class, id));
        }
    }

    public List<Disponibilidade> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // JOIN FETCH: carrega o Veiculo junto (evita N+1 queries no toString())
            return session.createQuery(
                    "FROM Disponibilidade d JOIN FETCH d.veiculo ORDER BY d.dataInicio",
                    Disponibilidade.class)
                    .getResultList();
        }
    }

    public List<Disponibilidade> listarPorVeiculo(int veiculoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Disponibilidade d JOIN FETCH d.veiculo v WHERE v.id = :vid ORDER BY d.dataInicio",
                    Disponibilidade.class)
                    .setParameter("vid", veiculoId)
                    .getResultList();
        }
    }

    /**
     * Verifica se um veículo está disponível no período solicitado.
     * Retorna true se NÃO houver registro de indisponibilidade conflitante.
     *
     * Lógica de sobreposição de datas:
     *   Dois intervalos [A,B] e [C,D] se sobrepõem quando: A <= D AND B >= C
     */
    public boolean estaDisponivel(int veiculoId, LocalDate inicio, LocalDate fim) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long conflitos = session.createQuery(
                    "SELECT COUNT(d) FROM Disponibilidade d " +
                    "WHERE d.veiculo.id = :vid " +
                    "AND d.disponivel = false " +
                    "AND d.dataInicio <= :fim " +
                    "AND d.dataFim >= :inicio",
                    Long.class)
                    .setParameter("vid", veiculoId)
                    .setParameter("inicio", inicio)
                    .setParameter("fim", fim)
                    .uniqueResult();
            return conflitos == 0;
        }
    }

    public boolean remover(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Disponibilidade d = session.get(Disponibilidade.class, id);
                if (d == null) { tx.rollback(); return false; }
                session.remove(d);
                tx.commit();
                return true;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
