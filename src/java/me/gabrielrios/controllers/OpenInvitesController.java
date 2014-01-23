package me.gabrielrios.controllers;

import me.gabrielrios.models.Invite;
import me.gabrielrios.controllers.util.JsfUtil;
import me.gabrielrios.controllers.util.PaginationHelper;
import me.gabrielrios.facades.InviteFacade;

import java.io.Serializable;
import java.util.List;
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
import me.gabrielrios.models.User;
import me.gabrielrios.util.UserManager;

@ManagedBean(name = "openInvitesController")
@SessionScoped
public class OpenInvitesController implements Serializable {

  private Invite current;
  private DataModel items = null;
  @EJB
  private me.gabrielrios.facades.InviteFacade ejbFacade;
  private PaginationHelper pagination;
  private int selectedItemIndex;

  public OpenInvitesController() {
  }

  public Invite getSelected() {
    if (current == null) {
      current = new Invite();
      selectedItemIndex = -1;
    }
    return current;
  }

  private InviteFacade getFacade() {
    return ejbFacade;
  }

  public String prepareList() {
    recreateModel();
    return "List";
  }
  
  public String destroy() {
    current = (Invite) getItems().getRowData();
    
    performDestroy();
    recreatePagination();
    recreateModel();
    return "List";
  }

  public String accept() {
    current = (Invite) getItems().getRowData();
    current.setPresence(1);
    try {
      getFacade().edit(current);
      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InviteUpdated"));
    } catch (Exception e) {
      JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
    }
    return "List";
  }

  private void performDestroy() {
    try {
      getFacade().remove(current);
      JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("InviteDeleted"));
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
      FacesContext context = FacesContext.getCurrentInstance();
      User user = (User) context.getExternalContext().getSessionMap().get(UserManager.USER_SESSION_KEY);
      
      return new ListDataModel((List) user.getOpenInvites());
//      items = createPageDataModel();
    }
    return items;
  }

  private void recreateModel() {
    items = null;
  }

  private void recreatePagination() {
    pagination = null;
  }

  public SelectItem[] getItemsAvailableSelectMany() {
    return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
  }

  public SelectItem[] getItemsAvailableSelectOne() {
    return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
  }

  @FacesConverter(forClass = Invite.class)
  public static class InviteControllerConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
      if (value == null || value.length() == 0) {
        return null;
      }
      OpenInvitesController controller = (OpenInvitesController) facesContext.getApplication().getELResolver().
              getValue(facesContext.getELContext(), null, "openInvitesController");
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
      if (object instanceof Invite) {
        Invite o = (Invite) object;
        return getStringKey(o.getId());
      } else {
        throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Invite.class.getName());
      }
    }
  }
}
