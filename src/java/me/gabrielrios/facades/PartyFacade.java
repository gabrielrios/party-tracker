/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gabrielrios.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import me.gabrielrios.models.Party;

/**
 *
 * @author gabriel
 */
@Stateless
public class PartyFacade extends AbstractFacade<Party> {
  @PersistenceContext(unitName = "party-trackerPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public PartyFacade() {
    super(Party.class);
  }
  
}
