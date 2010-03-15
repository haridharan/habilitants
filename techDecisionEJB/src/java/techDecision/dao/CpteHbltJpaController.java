/*
Copyright Stéphane Georges Popoff, (février - juin 2009)

spopoff@rocketmail.com

Ce logiciel est un programme informatique servant à gérer des habilitations.

Ce logiciel est régi par la licence [CeCILL|CeCILL-B|CeCILL-C] soumise au droit français et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence [CeCILL|CeCILL-B|CeCILL-C] telle que diffusée par le CEA, le CNRS et l'INRIA
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilité au code source et des droits de copie,
de modification et de redistribution accordés par cette licence, il n'est
offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
seule une responsabilité restreinte pèse sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les concédants successifs.

A cet égard  l'attention de l'utilisateur est attirée sur les risques
associés au chargement,  à l'utilisation,  à la modification et/ou au
développement et à la reproduction du logiciel par l'utilisateur étant
donné sa spécificité de logiciel libre, qui peut le rendre complexe à
manipuler et qui le réserve donc à des développeurs et des professionnels
avertis possédant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
logiciel à leurs besoins dans des conditions permettant d'assurer la
sécurité de leurs systèmes et ou de leurs données et, plus généralement,
à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.

Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
pris connaissance de la licence [CeCILL|CeCILL-B|CeCILL-C], et que vous en avez accepté les
termes.
 */
package techDecision.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import techDecision.dao.exceptions.NonexistentEntityException;
import techDecision.dao.exceptions.RollbackFailureException;
import techDecision.entites.CpteHblt;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import techDecision.entites.Compte;
import techDecision.entites.Habilitant;
/**
 *
 * @author spopoff@rocketmail.com
 * @version 0.2
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CpteHbltJpaController {

    private EntityManager em = null;

    CpteHbltJpaController(EntityManager emm) {
        em = emm;
    }

    public void create(CpteHblt cpteHblt) throws RollbackFailureException, Exception {
        try {
            em.persist(cpteHblt);
        } catch (Exception ex) {
            throw ex;
        } finally {
        }
    }

    public void edit(CpteHblt cpteHblt) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            cpteHblt = em.merge(cpteHblt);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cpteHblt.getPkcpteHblt();
                if (findCpteHblt(id) == null) {
                    throw new NonexistentEntityException("The cpteHblt with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            CpteHblt cpteHblt;
            try {
                cpteHblt = em.getReference(CpteHblt.class, id);
                cpteHblt.getPkcpteHblt();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cpteHblt with id " + id + " no longer exists.", enfe);
            }
            em.remove(cpteHblt);
        } catch (Exception ex) {
            throw ex;
        } finally {
        }
    }

    public List<CpteHblt> findCpteHbltEntities() {
        return findCpteHbltEntities(true, -1, -1);
    }

    public List<CpteHblt> findCpteHbltEntities(int maxResults, int firstResult) {
        return findCpteHbltEntities(false, maxResults, firstResult);
    }

    private List<CpteHblt> findCpteHbltEntities(boolean all, int maxResults, int firstResult) {
        try {
            Query q = em.createQuery("select object(o) from CpteHblt as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public CpteHblt findCpteHblt(Integer id) {
        try {
            return em.find(CpteHblt.class, id);
        } finally {
        }
    }
    /**
     * Vérifie la présence d'une liaison entre un compte et un habilitant
     * @param ic
     * @param ih
     * @return vrai si existe, faux sinon
     */
    public Boolean existCpteHblt(Compte c, Habilitant h){
        Boolean ret = false;
         try {
            Query q = em.createNamedQuery("CpteHblt.findByFkCpteFkHblt");
            q.setParameter("fkcpte", c);
            q.setParameter("fkhblt", h);
            CpteHblt ch = (CpteHblt) q.getSingleResult();
            if(ch!=null) ret = true;
        } catch(Exception err) {
            System.err.println("Erreur dans existCpteHblt "+err.toString());
        }
       return ret;
    }
    public Long getCpteHbltCount() {
        try {
            return ((Long) em.createQuery("select count(o) from CpteHblt as o").getSingleResult()).longValue();
        } finally {
        }
    }
    public void truncate(){
        try {
            Query q = em.createNativeQuery("truncate CPTE_HBLT");
            CpteHblt o = (CpteHblt) q.getSingleResult();
        } catch (Exception e) {
            System.err.println("On s'en fout de l'erreur truncate CPTE_HBLT");
        }
    }

}
