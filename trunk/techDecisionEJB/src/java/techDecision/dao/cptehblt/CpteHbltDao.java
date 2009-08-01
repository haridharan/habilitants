/*
Copyright Stéphane Georges Popoff, (avril 2009)

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
package techDecision.dao.cptehblt;
import java.util.List;
import techDecision.entites.CpteHblt;
import techDecision.dao.exceptions.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 *
 * @author spopoff@rocketmail.com
 * @version 0.2
 */
@Stateless(mappedName="ejbCpteHblt")
public class CpteHbltDao implements ICpteHbltDaoLocal, ICpteHbltDaoRemote {
    @PersistenceContext(unitName="techDecisionEJBPU")
    private EntityManager em;
    private CpteHbltJpaSimple objC = null;

    public void create(CpteHblt cpteHblt) throws PreexistingEntityException, RollbackFailureException, Exception {
        objC.create(cpteHblt);
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        objC.destroy(id);
    }

    public void edit(CpteHblt cpteHblt) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        objC.edit(cpteHblt);
    }

    public CpteHblt findCpteHblt(Integer id) {
        return objC.findCpteHblt(id);
    }

    public List<CpteHblt> findCpteHbltEntities() {
        return objC.findCpteHbltEntities();
    }

    public List<CpteHblt> findCpteHbltEntities(int maxResults, int firstResult) {
        return objC.findCpteHbltEntities(maxResults, firstResult);
    }

    public Long getCpteHbltCount() {
        return objC.getCpteHbltCount();
    }

    public void init() {
        objC = new CpteHbltJpaSimple(em);
    }

    public List<CpteHblt> relatedCpte(int idHblt) {
        return objC.relatedCpte(idHblt);
    }

    public List<CpteHblt> relatedHblt(int idCpte) {
        return objC.relatedHblt(idCpte);
    }

}
