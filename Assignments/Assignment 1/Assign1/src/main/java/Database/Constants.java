package Database;

import java.util.*;

public class Constants
{

    public static class Schemas
    {
        public static final String TEST="test_office";
        public static final String PRODUCTION="office";

        public static final String[] SCHEMAS= new String[]{TEST,PRODUCTION};
    }

    public static class Tables
    {
        public static final String ACCOUNT="account";
        public static final String USER="user";
        public static final String ROLE="role";
        public static final String RIGHT="right";
        public static final String ROLE_RIGHT="role_right";
        public static final String USER_ROLE="user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION= new String[]{ACCOUNT,USER,ROLE,RIGHT,ROLE_RIGHT,USER_ROLE};
    }

    public static class Roles
    {
        public static final String ADMIN="admin";
        public static final String CUSTOMER="customer";

        public static final String[] ROLES= new String[]{ADMIN,CUSTOMER};
    }

    public static class Rights
    {
        public static final String CREATE_USER="create_user";
        public static final String DELETE_USER="delete_user";
        public static final String UPDATE_USER="update_user";
        public static final String VIEW_USER="view_user";
        public static final String TRANSFER_MONEY="transfer_money";
        public static final String PROCESS_BILLS="process_bills";

        public static final String GENERATE_REPORT="generate_report";
        public static final String CRUD="crud";

        public static final String[] RIGHTS = new String[]{CREATE_USER,DELETE_USER,UPDATE_USER,VIEW_USER,TRANSFER_MONEY,PROCESS_BILLS,GENERATE_REPORT,CRUD};
    }

    public static Map<String, List<String>> getRolesRights()
    {
        Map<String,List<String>> ROLES_RIGHTS = new HashMap<>();
        for(String role: Roles.ROLES)
        {
            ROLES_RIGHTS.put(role,new ArrayList<>());
        }
        ROLES_RIGHTS.get(Roles.ADMIN).addAll(Arrays.asList(Rights.RIGHTS));

        ROLES_RIGHTS.get(Roles.CUSTOMER).addAll(Arrays.asList(Rights.CREATE_USER, Rights.DELETE_USER, Rights.UPDATE_USER, Rights.VIEW_USER, Rights.TRANSFER_MONEY, Rights.PROCESS_BILLS));

        return ROLES_RIGHTS;
    }

}
