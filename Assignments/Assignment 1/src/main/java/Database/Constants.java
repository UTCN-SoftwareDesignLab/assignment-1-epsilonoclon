package Database;

import java.util.*;

import static Database.Constants.Rights.TRANSFER_MONEY;

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
        public static final String ACTIVITY ="activity";
        public static final String CLIENT ="client";

        public static final String[] ORDERED_TABLES_FOR_CREATION= new String[]{ACCOUNT,USER,ROLE,RIGHT,ROLE_RIGHT,USER_ROLE,ACTIVITY,CLIENT};
    }

    public static class Roles
    {
        public static final String ADMIN="admin";
        public static final String EMPLOYEE="employee";

        public static final String[] ROLES= new String[]{ADMIN,EMPLOYEE};
    }

    public static class Rights
    {
        public static final String CREATE_USER="create_user";
        public static final String DELETE_USER="delete_user";
        public static final String UPDATE_USER="update_user";
        public static final String CREATE_CLIENT="create_client";
        public static final String DELETE_CLIENT="delete_client";
        public static final String UPDATE_CLIENT="update_client";
        public static final String CREATE_ACCOUNT="create_account";
        public static final String DELETE_ACCOUNT="delete_account";
        public static final String UPDATE_ACCOUNT="update_account";
        public static final String TRANSFER_MONEY="transfer_money";
        public static final String PROCESS_BILLS="process_bills";
        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER,CREATE_CLIENT,DELETE_CLIENT,UPDATE_CLIENT,CREATE_ACCOUNT,DELETE_ACCOUNT,UPDATE_ACCOUNT,TRANSFER_MONEY,PROCESS_BILLS};
    }

    public static class Operations
    {
        public static final String CRUD_CLIENT = "crud_client";
        public static final String CRUD_EMPLOYEE = "crud_employee";
        public static final String CRUD_ACCOUNTS= "crud_accounts";
        public static final String LOG_OUT="log_out";
        public static final String PAY_BILLS = "pay_bills";
        public static final String TRANSFER_MONEY= "transfer_money";
        public static final String GENERATE_REPORT="generate_report";
    }

    public static Map<String, List<String>> getRolesRights()
    {
        Map<String,List<String>> ROLES_RIGHTS = new HashMap<>();
        for(String role: Roles.ROLES)
        {
            ROLES_RIGHTS.put(role,new ArrayList<>());
        }
        ROLES_RIGHTS.get(Roles.ADMIN).addAll(Arrays.asList(Rights.RIGHTS));

        ROLES_RIGHTS.get(Roles.EMPLOYEE).addAll(Arrays.asList(Rights.CREATE_ACCOUNT, Rights.DELETE_ACCOUNT, Rights.UPDATE_ACCOUNT, Rights.CREATE_CLIENT, Rights.DELETE_CLIENT, Rights.UPDATE_CLIENT,TRANSFER_MONEY, Rights.PROCESS_BILLS));

        return ROLES_RIGHTS;
    }

}
