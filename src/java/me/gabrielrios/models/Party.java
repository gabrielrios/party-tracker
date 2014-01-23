/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gabrielrios.models;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.primefaces.model.map.LatLng;

/**
 *
 * @author gabriel
 */
@Entity
@Table(name = "party")
@XmlRootElement
@NamedQueries({
@NamedQuery(name = "Party.findAll", query = "SELECT p FROM Party p"),
@NamedQuery(name = "Party.findById", query = "SELECT p FROM Party p WHERE p.id = :id"),
@NamedQuery(name = "Party.findByName", query = "SELECT p FROM Party p WHERE p.name = :name"),
@NamedQuery(name = "Party.findByStartAt", query = "SELECT p FROM Party p WHERE p.startAt = :startAt")})
public class Party implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @Column(name = "id")
  private Integer id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "name")
  private String name;
  @Lob
  @Size(max = 65535)
  @Column(name = "description")
  private String description;
  @Basic(optional = false)
  @NotNull
  @Column(name = "start_at")
  @Temporal(TemporalType.TIME)
  private Date startAt;
  @Basic(optional = false)
  @NotNull
  @Lob
  @Size(min = 1, max = 65535)
  @Column(name = "address")
  private String address;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "partyId", fetch = FetchType.LAZY)
  private Collection<Invite> inviteCollection;
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private User userId;

  @Basic(optional = false)
  @Column(name = "latitude")
  private Double latitude;
  
  @Basic(optional = false)
  @Column(name = "longitude")
  private Double longitude;
  
  
  public Party() {
  }

  public Party(Integer id) {
    this.id = id;
  }

  public Party(Integer id, String name, Date startAt, String address) {
    this.id = id;
    this.name = name;
    this.startAt = startAt;
    this.address = address;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getStartAt() {
    return startAt;
  }

  public void setStartAt(Date startAt) {
    this.startAt = startAt;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @XmlTransient
  public Collection<Invite> getInviteCollection() {
    return inviteCollection;
  }

  public void setInviteCollection(Collection<Invite> inviteCollection) {
    this.inviteCollection = inviteCollection;
  }

  public User getUserId() {
    return userId;
  }

  public void setUserId(User userId) {
    this.userId = userId;
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
    if (!(object instanceof Party)) {
      return false;
    }
    Party other = (Party) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return name;
  }
 
  public LatLng getCoordinates() {
//    if (latitude == null || longitude == null) {
      Geocoder geocoder = new Geocoder();
      GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("pt-BR").getGeocoderRequest();
      GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
      List<GeocoderResult> results = geocoderResponse.getResults();
      if (results != null ){
        GeocoderResult result = results.get(0);
        com.google.code.geocoder.model.LatLng coord = result.getGeometry().getLocation();
        latitude = coord.getLat().doubleValue();
        longitude = coord.getLng().doubleValue();
      }
//    }
    return new LatLng(latitude, longitude);
  }
  
  public String getCoordinatesAsString() {
    return String.format("%.4f,%.4f", latitude, longitude);
  }
}

