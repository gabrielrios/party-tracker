package me.gabrielrios.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import me.gabrielrios.models.Invite;
import me.gabrielrios.models.Party;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-12T12:45:05")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> email;
    public static volatile CollectionAttribute<User, Party> partyCollection;
    public static volatile CollectionAttribute<User, Invite> inviteCollection1;
    public static volatile SingularAttribute<User, String> password;
    public static volatile CollectionAttribute<User, Invite> inviteCollection;

}