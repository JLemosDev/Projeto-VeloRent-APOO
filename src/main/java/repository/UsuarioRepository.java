package repository;

import model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class UsuarioRepository {

    public Usuario criar(String login, String senhaHash, Usuario.Perfil perfil) {
        Usuario usuario = new Usuario(login, senhaHash, perfil);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(usuario);
                tx.commit();
                return usuario;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public Optional<Usuario> buscarPorLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .uniqueResultOptional();
        }
    }

    public Usuario salvar(Usuario usuario) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Usuario salvo = session.merge(usuario);
                tx.commit();
                return salvo;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    public List<Usuario> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Usuario ORDER BY login", Usuario.class)
                    .getResultList();
        }
    }

    public boolean remover(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Usuario u = session.get(Usuario.class, id);
                if (u == null) { tx.rollback(); return false; }
                session.remove(u);
                tx.commit();
                return true;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    /** Retorna true se ainda não existe nenhum usuário no banco. */
    public boolean estaVazio() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long total = session.createQuery("SELECT COUNT(u) FROM Usuario u", Long.class)
                    .uniqueResult();
            return total == 0;
        }
    }
}
