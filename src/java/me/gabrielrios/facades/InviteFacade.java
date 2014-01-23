/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gabrielrios.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import me.gabrielrios.models.Invite;
import java.util.List;

/**
 *
 * @author gabriel
 */
@Stateless
public class InviteFacade extends AbstractFacade<Invite> {
  @PersistenceContext(unitName = "party-trackerPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public InviteFacade() {
    super(Invite.class);
  }
  


  
  
}
