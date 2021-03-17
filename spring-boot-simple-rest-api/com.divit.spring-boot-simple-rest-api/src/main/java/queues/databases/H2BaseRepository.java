package queues.databases;


import queues.executor.TaskExecutor;
import queues.models.H2Queue;
import queues.models.Queue;
import queues.utils.Converter;
import org.bson.types.ObjectId;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class H2BaseRepository {

    public H2BaseRepository() {
        createTable();
    }

    public static Connection connection() {
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:~/test";
        final String USER = "sa";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void createTable() {
        String sql = "create table if not exists QUEUE(" +
                "ID varchar(255) primary key," +
                "executionDate varchar(255)," +
                "city varchar(255)," +
                "type varchar(255)," +
                "createdAt varchar(255)," +
                "tasks varchar(255)" +
                ")";
        executeUpdate(sql);
    }


    @NotNull
    public static Queue add(@NotNull H2Queue queue) {
        if(queue.getId() == null) {
            queue.setId(new ObjectId().toString());
            queue.setCreatedAt(Converter.localDateToDate(LocalDateTime.now()));
        }
        String insertQuery = "insert into QUEUE values('" + queue.getId() + "', '" + queue.getExecutionDate() + "', '" + queue.getCity() + "','" +
                queue.getType().name() + "', '" + queue.getCreatedAt() + "', '" + queue.getTasks().name() + "')";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection().prepareStatement(insertQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Queue q = Converter.convertQueue(queue);
        TaskExecutor t = new TaskExecutor(q);
        t.execute();
        return q;
    }


    public static Statement executeUpdate(String sql) {
        Connection conn = connection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static Statement executeQuery(String sql) {
        Connection conn = connection();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static List<H2Queue> find(String selectQuery) {
        List<H2Queue> queues = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection().prepareStatement(selectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            H2Queue queue = null;
            try {
                queue = new H2Queue(rs.getString("id"), Converter.convertDate(rs.getString("executionDate")), rs.getString("city"), Converter.convertQueueType(rs.getString("type")), Converter.convertDate(rs.getString("createdAt")), Converter.convertTaskType(rs.getString("tasks")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            queues.add(queue);
        }
        return queues;
    }

    @Nullable
    public static H2Queue getById(String id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection().prepareStatement("SELECT * From QUEUE WHERE 'id' = '" + id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!rs.next()) return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                return new H2Queue(rs.getString("id"), Converter.convertDate(rs.getString("executionDate")), rs.getString("city"), Converter.convertQueueType(rs.getString("type")), Converter.convertDate(rs.getString("createdAt")), Converter.convertTaskType(rs.getString("tasks")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Queue update(String id, H2Queue queue) {
        String sql = "UPDATE QUEUE SET type = '" + queue.getType().name() + "',executionDate = '" + queue.getExecutionDate() + "', city = '" +
                queue.getCity() + "',tasks ='" + queue.getTasks() + "' WHERE id = '" + id + "'";
        if (executeUpdate(sql) != null) return Converter.convertQueue(queue);
        return null;
    }

    public static boolean delete(String id) {
        String sql = "DELETE FROM QUEUE WHERE ID = '" + id + "'";
        return executeUpdate(sql) != null;
    }

    public static void delete(List<Queue> h2Queues) {
        h2Queues.forEach(queue -> delete(queue.getId()));
    }



    /*public static void add(Queue queue) throws SQLException {
        String insertQuery = "insert into QUEUE(obj) values(?)";
        PreparedStatement preparedStatement = connection().prepareStatement(insertQuery);
        preparedStatement.setObject(1, queue);
        preparedStatement.execute();
        connection().commit();
    }*/
    /*    public static List<Queue> find(String selectQuery) throws SQLException {
        List<Queue> queues = new ArrayList<>();
        PreparedStatement preparedStatement = connection().prepareStatement(selectQuery);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Queue queue = (Queue) rs.getObject("obj");
            queues.add(queue);
        }
        return queues;
    }*/

 /*   public static void main(String[] args) {
        *//*executeUpdate("DROP TABLE QUEUE");*//*


        List<H2Queue> h2Queues = find("SELECT * FROM QUEUE");
        System.out.println(h2Queues.size());
        List<H2Queue> h2Queues1 = find("SELECT * FROM QUEUE WHERE type = 'NEW'");
        System.out.println("NEW " + h2Queues1.toString());
        System.out.println("NEW " + h2Queues1.size());
        List<H2Queue> h2Queues2 = find("SELECT * FROM QUEUE WHERE type = 'FINISHED'");
        System.out.println("Finished " + h2Queues2.toString());
        System.out.println("FINISHED " + h2Queues2.size());
        List<H2Queue> h2Queues3 = find("SELECT * FROM QUEUE WHERE type = 'FAILED'");
        System.out.println("FAILED " + h2Queues3.toString());
        System.out.println("FAILED " + h2Queues3.size());
        delete("601dc15266393119d3cc1476");
        h2Queues1 = find("SELECT * FROM QUEUE WHERE type = 'NEW'");
        System.out.println("NEW after delete" + h2Queues1.toString());
        System.out.println("NEW after delete" + h2Queues1.size());



    *//*    Queue q1 = Converter.convertQueue(h2Queues.get(0));
        q1.setType(QueueType.FINISHED);
        update(q1.getId(),Converter.convertQueue(q1));*//*

        *//*executeUpdate("DROP TABLE QUEUE");*//*

        *//*Queue queue = new Queue(Converter.localDateToDate(LocalDateTime.now().plusSeconds(30)), "PRISHTINA", QueueType.NEW,
                TaskType.WriteToFile);
        queue.setId(new ObjectId().toString());
        queue.setCreatedAt(Converter.localDateToDate(LocalDateTime.now()));
        Queue queue2 = new Queue(Converter.localDateToDate(LocalDateTime.now().plusSeconds(31)), "PRISHTINA", QueueType.NEW,
                TaskType.PrimeNumberWritter);
        queue2.setId(new ObjectId().toString());
        queue2.setCreatedAt(Converter.localDateToDate(LocalDateTime.now()));
        TaskExecutor t = new TaskExecutor(queue);
              TaskExecutor      t2 = new TaskExecutor(queue2);
              t.execute();
              t2.execute();*//*
    }*/

    /*public static void main(String[] args) {
        H2BaseRepository h2BaseRepository = new H2BaseRepository();
        Queue queue = new Queue(Converter.localDateToDate(LocalDateTime.now().plusSeconds(10)), "Prishtina", QueueType.NEW, TaskType.GetTodayQueues);
        queue.setCreatedAt(Converter.localDateToDate(LocalDateTime.now()));
        queue.setId(new ObjectId().toString());
        h2BaseRepository.add(Converter.convertQueue(queue));

        Queue queue2 = new Queue(Converter.localDateToDate(LocalDateTime.now().plusSeconds(10)), "Prishtina", QueueType.NEW, TaskType.WriteFinishedQueue);
        queue2.setCreatedAt(Converter.localDateToDate(LocalDateTime.now()));
        queue2.setId(new ObjectId().toString());
        h2BaseRepository.add(Converter.convertQueue(queue2));

        queue = new Queue(Converter.localDateToDate(LocalDateTime.now().plusSeconds(10)), "Prishtina", QueueType.NEW, TaskType.WriteFailedQueue);
        queue.setCreatedAt(Converter.localDateToDate(LocalDateTime.now()));
        queue.setId(new ObjectId().toString());
        h2BaseRepository.add(Converter.convertQueue(queue));
    }*/
    public static void main(String[] args) {
        System.out.println("aa");
    }
}
