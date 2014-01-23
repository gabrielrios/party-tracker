/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gabrielrios.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "invite")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "Invite.findAll", query = "SELECT i FROM Invite i"),
@NamedQuery(name = "Invite.findById", query = "SELECT i FROM Invite i WHERE i.id = :id"),
@NamedQuery(name="Invite.findAllByPartyId", query="SELECT i FROM Invite i WHERE i.partyId = :partyId"),
@NamedQuery(name = "Invite.findByPresence", query = "SELECT i FROM Invite i WHERE i.presence = :presence")})

public class Invite implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  
  @Column(name = "presence")
  private Integer presence;
  
  @JoinColumn(name = "party_id", referencedColumnName = "id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Party partyId;
  @JoinColumn(name = "host_id", referencedColumnName = "id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User hostId;
  @JoinColumn(name = "guest_id", referencedColumnName = "id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User guestId;

  public Invite() {
  }

  public Invite(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPresence() {
    return presence;
  }

  public void setPresence(Integer presence) {
    this.presence = presence;
  }

  public Party getPartyId() {
    return partyId;
  }

  public void setPartyId(Party partyId) {
    this.partyId = partyId;
  }

  public User getHostId() {
    return hostId;
  }

  public void setHostId(User hostId) {
    this.hostId = hostId;
  }

  public User getGuestId() {
    return guestId;
  }

  public void setGuestId(User guestId) {
    this.guestId = guestId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Invite)) {
      return false;
    }
    Invite other = (Invite) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  public void confirmPresence() {
    this.presence = 1;
  }
  
  @Override
  public String toString() {
    return "me.gabrielrios.models.Invite[ id=" + id + " ]";
  }

  boolean isConfirmed() {
    if (this.presence != null) {
      return this.presence == 1;
    } else {
      return false;
    }
  }
  
}
