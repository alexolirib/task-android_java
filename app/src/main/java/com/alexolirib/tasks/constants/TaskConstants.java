package com.alexolirib.tasks.constants;

public class TaskConstants {
    public static class HEADER {
        public static final String TOKEN_KEY = "token";
        public static final String PERSON_KEY = "personKey";
    }

    public static class ENDPOINT {
        public static final String ROOT = "http://www.devmasterteam.com/cursoAndroidAPI";

        public static final String AUTHENTICATION_CREATE = "Authentication/Create";
        public static final String AUTHENTICATION_LOGIN = "Authentication/Login";

        public static final String TASK_GET_LIST_NO_FILTER = "Task";
        public static final String TASK_GET_LIST_overdue = "Task/Overdue";
        public static final String TASK_GET_LIST_NEXT_7_DAYS = "Task/Next7Days";
        public static final String TASK_GET_SPECIFIC = "Task";
        public static final String TASK_DELETE = "Task";
        public static final String TASK_UPDATE = "Task";
        public static final String TASK_INSERT = "Task";
        public static final String TASK_COMPLETE = "Task/Complete";
        public static final String TASK_UNCOMPLETE = "Task/Uncomplete";


        public static final String PRIORITY_GET = "priority";
    }

    public static class API_PARAMETER{
        public static class TASK {

            public static String ID = "id";
            public static String PRIORITY_ID = "priorityid";
            public static String DESCRIPTION = "description";
            public static String DUE_DATE = "duedate";
            public static String COMPLETE = "complete";
        }

        public static class PERSON {
            public static String NAME = "name";
            public static String EMAIL = "email";
            public static String PASSWORD = "password";
            public static String RECEIVE_NEWS = "receivenews";
        }
    }

    public static class OPERATION_METHOD {
        public static final String GET = "GET";
        public static final String PUT = "PUT";
        public static final String POST = "POST";
        public static final String DELETE = "DELETE";
    }

    public static class USER {
        public static final String NAME = "name";
        public static final String EMAIL = "email";
    }

    public static class STATUS_CODE {
        public static final int SUCCESS =200;
        public static final int FORBINDDEN =403;
        public static final int NOT_FOUND =404;
        public static final int INTERNAL_SERVER_ERROR =500;
        public static final int INTERNET_NOT_AVAILABLE =901;

    }

    public static class TASK_FILTER {
        public static final String KEY = "taskFilterKey";
        public static final int NO_FILTER = 0;
        public static final int OVERDUE = 1;
        public static final int NEXT_7_DAYS = 2;
    }

}
