package ro.mpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
 	//to do 
        return null;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
	//to do
        return null;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving task {}",elem);
        Connection conn=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=conn.prepareStatement(
                "insert into Masini (manufacturer, model, year) values (?, ?, ?)"

        )){
            preparedStatement.setString(1, elem.getManufacturer());
            preparedStatement.setString(2, elem.getModel());
            preparedStatement.setInt(3, elem.getYear());
            int result=preparedStatement.executeUpdate();
            logger.traceExit("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Eror DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
      //to do
    }

    @Override
    public Iterable<Car> findAll() {
         logger.traceEntry();
         Connection conn=dbUtils.getConnection();
         List<Car> cars=new ArrayList<>();
         try(PreparedStatement preparedStatement=conn.prepareStatement("Select * from cars")){
             try(ResultSet resultSet=preparedStatement.executeQuery()){
                 while(resultSet.next()){
                     int id=resultSet.getInt("id");
                     String manufacturer=resultSet.getString("manufacturer");
                     String model=resultSet.getString("model");
                     int year=resultSet.getInt("year");
                     Car car= new Car(manufacturer,model,year);
                     car.setId(car.getId());
                     cars.add(car);

                 }
             }
         }
         catch(SQLException e){
             logger.error(e);
             System.err.println("Eror DB " + e);
         }
         logger.traceExit(cars);
         return cars;

    }
}
