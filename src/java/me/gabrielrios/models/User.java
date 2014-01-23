/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gabrielrios.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
  @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
  @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
  @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
  @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "username")
  private String username;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "password")
  private String password;
  // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "email")
  private String email;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "hostId", fetch = FetchType.LAZY)
  private Collection<Invite> inviteHostCollection;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "guestId", fetch = FetchType.LAZY)
  private Collection<Invite> inviteGuestCollection;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
  private Collection<Party> partyCollection;

  public User() {
  }

  public User(Integer id) {
    this.id = id;
  }

  public User(Integer id, String username, String password, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @XmlTransient
  public Collection<Invite> getInviteHostCollection() {
    return inviteHostCollection;
  }

  public void setInviteHostCollection(Collection<Invite> inviteCollection) {
    this.inviteHostCollection = inviteCollection;
  }

  @XmlTransient
  public Collection<Invite> getInviteGuestCollection() {
    return inviteGuestCollection;
  }

  public void setInviteguestCollection(Collection<Invite> inviteCollection1) {
    this.inviteGuestCollection = inviteCollection1;
  }

  @XmlTransient
  public Collection<Party> getPartyCollection() {
    return partyCollection;
  }
  
  public Collection<Invite> getOpenInvites() {
    ArrayList<Invite> temp = new ArrayList<Invite>();
    
    for (Invite i : getInviteGuestCollection()) {
      if (i.isConfirmed()) {
      } else {
        temp.add(i);
      }
        
    }
    
    return temp;
  }
  
  public int getOpenInvitesSize() {
    return getOpenInvites().size();
  }

  public void setPartyCollection(Collection<Party> partyCollection) {
    this.partyCollection = partyCollection;
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
    if (!(object instanceof User)) {
      return false;
    }
    User other = (User) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return username;
  }
 
  
}
