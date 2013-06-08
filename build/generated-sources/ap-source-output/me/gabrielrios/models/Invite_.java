package me.gabrielrios.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import me.gabrielrios.models.Party;
import me.gabrielrios.models.User;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-06-08T09:42:48")
@StaticMetamodel(Invite.class)
public class Invite_ { 

    public static volatile SingularAttribute<Invite, Integer> id;
    public static volatile SingularAttribute<Invite, Integer> presence;
    public static volatile SingularAttribute<Invite, Party> partyId;
    public static volatile SingularAttribute<Invite, User> hostId;
    public static volatile SingularAttribute<Invite, User> guestId;

}