package me.gabrielrios.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import me.gabrielrios.models.Invite;
import me.gabrielrios.models.User;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-05-12T12:45:05")
@StaticMetamodel(Party.class)
public class Party_ { 

    public static volatile SingularAttribute<Party, Integer> id;
    public static volatile SingularAttribute<Party, String> address;
    public static volatile SingularAttribute<Party, String> description;
    public static volatile SingularAttribute<Party, String> name;
    public static volatile SingularAttribute<Party, User> userId;
    public static volatile SingularAttribute<Party, Date> startAt;
    public static volatile SingularAttribute<Party, Double> longitude;
    public static volatile SingularAttribute<Party, Double> latitude;
    public static volatile CollectionAttribute<Party, Invite> inviteCollection;

}