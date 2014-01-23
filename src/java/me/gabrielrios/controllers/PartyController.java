package me.gabrielrios.controllers;

import me.gabrielrios.models.Party;
import me.gabrielrios.controllers.util.JsfUtil;
import me.gabrielrios.controllers.util.PaginationHelper;
import me.gabrielrios.facades.PartyFacade;

import java.io.Serializable;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@ManagedBean(name = "partyController")
@SessionScoped
public class PartyController implements Serializable {

  private Party current;
  private DataModel items = null;
  @EJB
  private me.gabrielrios.facades.PartyFacade ejbFacade;
  private PaginationHelper pagination;
  private int selectedItemIndex;
  private MapModel simpleModel;

  public PartyController() {
   
  }
  public MapModel getSimpleModel() {
    if (simpleModel == null) {
      simpleModel = new DefaultMapModel();
      //Shared coordinates  
      for (Iterator it = getItems().iterator(); it.hasNext();) {
        Party p = (Party) it.next();
        
        simpleModel.addOverlay(new Marker(p.getCoordinates(), p.getName()));
      }
   }
    return simpleModel;
  }
  
  public Party getSelected() {
    if (current == null) {
      current = new Party();
      selectedItemIndex = -1;
    }
    return current;
  }

  private PartyFacade getFacade() {
    return ejbFacade;
  }

  public PaginationHelper getPagination() {
    if (pagination == null) {
      pagination = new PaginationHelper(10) {
        @Override
        public int getItemsCount() {
          return getFacade().count();
        }

        @Override
        public DataModel createPageDataModel() {
          return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
        }
      };
    }
    return pagination;
  }

  public String prepareList() {
    recreateModel();
    return "List";
  }

  public String prepareInvite() {
    current = (Party) getItems().getRowData();
    selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    return "Invite";
  }
  
  public String prepareView() {
    current = (Party) getItems().getRowData();
    selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    return "View";
  }

  public String prepareCreate() {
    current = new Party();
    selectedItemIndex = -1;
    return "Create";
  }

  public String create() {
    try {
      getFacade().create(current);
      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartyCreated"));
      return prepareCreate();
    } catch (Exception e) {
      JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
      return null;
    }
  }

  public String prepareEdit() {
    current = (Party) getItems().getRowData();
    selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    return "Edit";
  }

  public String update() {
    try {
      getFacade().edit(current);
      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartyUpdated"));
      return "View";
    } catch (Exception e) {
      JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
      return null;
    }
  }

  public String destroy() {
    current = (Party) getItems().getRowData();
    selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
    performDestroy();
    recreatePagination();
    recreateModel();
    return "List";
  }

  public String destroyAndView() {
    performDestroy();
    recreateModel();
    updateCurrentItem();
    if (selectedItemIndex >= 0) {
      return "View";
    } else {
      // all items were removed - go back to list
      recreateModel();
      return "List";
    }
  }

  private void performDestroy() {
    try {
      getFacade().remove(current);
      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PartyDeleted"));
    } catch (Exception e) {
      JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
    }
  }

  private void updateCurrentItem() {
    int count = getFacade().count();
    if (selectedItemIndex >= count) {
      // selected index cannot be bigger than number of items:
      selectedItemIndex = count - 1;
      // go to previous page if last page disappeared:
      if (pagination.getPageFirstItem() >= count) {
        pagination.previousPage();
      }
    }
    if (selectedItemIndex >= 0) {
      current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
    }
  }

  public DataModel getItems() {
    if (items == null) {
      items = getPagination().createPageDataModel();
    }
    return items;
  }

  private void recreateModel() {
    items = null;
  }

  private void recreatePagination() {
    pagination = null;
  }

  public String next() {
    getPagination().nextPage();
    recreateModel();
    return "List";
  }

  public String previous() {
    getPagination().previousPage();
    recreateModel();
    return "List";
  }

  public SelectItem[] getItemsAvailableSelectMany() {
    return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
  }

  public SelectItem[] getItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
  }
  

  @FacesConverter(forClass = Party.class)
  public static class PartyControllerConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
      if (value == null || value.length() == 0) {
        return null;
      }
      PartyController controller = (PartyController) facesContext.getApplication().getELResolver().
              getValue(facesContext.getELContext(), null, "partyController");
      return controller.ejbFacade.find(getKey(value));
    }

    java.lang.Integer getKey(String value) {
      java.lang.Integer key;
      key = Integer.valueOf(value);
      return key;
    }

    String getStringKey(java.lang.Integer value) {
      StringBuffer sb = new StringBuffer();
      sb.append(value);
      return sb.toString();
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
      if (object == null) {
        return null;
      }
      if (object instanceof Party) {
        Party o = (Party) object;
        return getStringKey(o.getId());
      } else {
        throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Party.class.getName());
      }
    }

  }
}
