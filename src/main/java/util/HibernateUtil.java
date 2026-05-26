package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtil — Fábrica de Sessions (Padrão Singleton)
 *
 * Por que Singleton?
 *   Criar uma SessionFactory é caro: lê configurações, conecta ao banco,
 *   prepara o mapeamento das entidades. Fazemos isso UMA VEZ só ao iniciar
 *   o programa e reutilizamos a instância em todo o sistema.
 *
 *   Sessions individuais (abertas por openSession()) são leves e devem ser
 *   criadas/fechadas por operação — como um "pedido de conversa" com o banco.
 */
public class HibernateUtil {

    // Criada uma única vez quando a classe é carregada pela JVM
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml") // lê src/main/resources/hibernate.cfg.xml
                    .buildSessionFactory();
        } catch (Exception e) {
            System.err.println("╔═══════════════════════════════════════════╗");
            System.err.println("║  ERRO ao inicializar o Hibernate!         ║");
            System.err.println("║  Verifique o hibernate.cfg.xml            ║");
            System.err.println("╚═══════════════════════════════════════════╝");
            System.err.println("Detalhe: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /** Chame ao encerrar o programa para liberar conexões com o banco. */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
