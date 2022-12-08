package finlaProjectSemester04;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class DBConnection {
    private String url;
    private Statement statement = null;
    private Connection connection= null;
    private Connection connection1= null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private ResultSet result = null;
    DBConnection() throws SQLException{
        // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"
         this.url = "jdbc:mysql://localhost:3306/dbstudent_management_system";
         try{    
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url,"root","");
            this.connection1 = DriverManager.getConnection(url,"root","");
            
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }
                  
    }
    
   public void addDetailsForDatabase(String firstName,String lastName,String id,String email,String address,int mobile,String gender,String passwprd) throws SQLException{
      String insert = "INSERT INTO administrator"
            + "(administrator.firstName,"
            + "administrator.lastName,administrator.id,"
            + "administrator.email,administrator.address,"
            + "administrator.mobileNumber,administrator.gender,"
            + "administrator.password) VALUES(?,?,?,?,?,?,?,?)";
       try{
        this.ps = this.connection.prepareStatement(insert);
        
        this.ps.setString(1,firstName);
        this.ps.setString(2,lastName);
        this.ps.setString(3,id);
        this.ps.setString(4,email);
        this.ps.setString(5,address);
        this.ps.setInt(6,mobile);
        this.ps.setString(7,gender);
        this.ps.setString(8,passwprd);
        
        this.ps.executeUpdate();
        this.connection.close();
        JOptionPane.showMessageDialog(null,"Insert successfully");
       }
       catch(SQLException e){
          JOptionPane.showMessageDialog(null,e);
       }     
   } 
   
   public boolean matchEmailAndPassword(String email , String userPassword){
       
       boolean check = false ;
       String sql = "SELECT password FROM administrator WHERE email = '"+email+"'";
       
        try {
            this.ps = this.connection.prepareStatement(sql);
            this.result = this.ps.executeQuery();
            
            if(this.result.next()){
                String pw = this.result.getString("password");
                if(userPassword.equals(pw)){
                    System.out.println("Match");
                    check = true;
                    this.connection.close();
                }else{
                    JOptionPane.showMessageDialog(null,"Password Incorrect");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Empty");
            } 
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }   
     return check;   
   }
   private String getCourseCode(String courseName){
       String sql = "SELECT courseCode FROM coursecode WHERE courseName = '"+courseName+"'";
       String getCode="";
        try {          
            this.ps = this.connection.prepareStatement(sql);
            this.result = this.ps.executeQuery();
            if(this.result.next()){
                getCode = this.result.getString("courseCode");
                this.connection.close();
            }else{
                JOptionPane.showMessageDialog(null, "Can't find course code");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return getCode;
   }
   
   public void addStudentDataIntoDatabase(String index,String id,String fullName,int mobileNo,String gender,String streems,String faculty,String courseName){
 
        
       String sql = "INSERT INTO student" +
                    " (student.indexNo, student.stuId," +
                    "student.fullName, student.mobileNo, " +
                    "student.gender, student.streem, " +
                    "student.faculty, student.courseName," +
                    "student.courseCode) "+
                    "VALUES (?,?,?,?,?,?,?,?,?);";
       
       String courseCode = getCourseCode(courseName);
       System.out.println(courseCode);
        try {
            this.ps1 = this.connection1.prepareStatement(sql);
        
                this.ps1.setString(1,index);
                this.ps1.setString(2,id);
                this.ps1.setString(3,fullName);
                this.ps1.setInt(4,mobileNo);
                this.ps1.setString(5,gender);
                this.ps1.setString(6,streems);
                this.ps1.setString(7,faculty);
                this.ps1.setString(8,courseName);
                this.ps1.setString(9, courseCode);

                this.ps1.executeUpdate();
                this.connection1.close();
         JOptionPane.showMessageDialog(null, "Insert successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
      
   }
   
   private String getCurrentPosition(String dbName,String columnName1,String columnName2,String indexValue){
       
       String sql = "SELECT "+ columnName1 +" FROM "+dbName+" WHERE "+columnName2+" = "+"'"+indexValue+"'";
       System.out.println(sql);
       String getIndex ="";
        try {
          this.ps = this.connection.prepareStatement(sql);
          this.result = this.ps.executeQuery();
          
          if(this.result.next()){
              
              if(indexValue.equals(this.result.getString(columnName1))){
                  getIndex = this.result.getString(columnName1);
                  System.out.println(getIndex);
                  this.connection.close();
              }else{
                  JOptionPane.showMessageDialog(null, "Index number Incorrect");
              }
          }         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       return getIndex;
   }
   
   public void dataDeleteFromDatabase(String indexNumber){
       String columnName12 = "indexNo";
       String sqlQuery = "DELETE FROM `student` WHERE "+columnName12+" =?";
       String getIndex = getCurrentPosition("student", columnName12,columnName12,indexNumber);
       if(getIndex.length()==0){
           JOptionPane.showMessageDialog(null, "Cannot find this index please try again");  
       }else{
        try {
            this.ps1 = this.connection1.prepareStatement(sqlQuery);
            this.ps1.setString(1,getIndex);
            this.ps1.execute();
            this.connection1.close();
            JOptionPane.showMessageDialog(null, "Successfully deleted");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       }
   }
     
   public ArrayList<GetData> getDataToTheTable(String sqlQuery){ 
       ArrayList<GetData> dataList = new ArrayList<>();
       
       try {
           this.statement = this.connection.createStatement();
           this.result = this.statement.executeQuery(sqlQuery);
           GetData getData;
           while(this.result.next()){
               getData = new GetData(this.result.getString("indexNo"),
                                    this.result.getString("stuId"),this.result.getString("fullName"),
                                    this.result.getInt("mobileNo"),this.result.getString("gender"),
                                    this.result.getString("streem"),this.result.getString("faculty"),
                                    this.result.getString("courseName"),this.result.getString("courseCode")
                                    );
            dataList.add(getData);  
            //return dataList;
           }
           this.statement.close();
       } catch (SQLException e) {
           
       }
       return dataList;
   }
   
  
   public  ArrayList<GetData> getCourseDtailsToTable(){
       
       ArrayList<GetData> courseList = new ArrayList<>();
       String sqlQuery = "SELECT * FROM coursecode";
        try {
            this.statement = this.connection.createStatement();
            this.result = this.statement.executeQuery(sqlQuery);
            GetData cData;
            while(this.result.next()){
                cData  = new GetData(this.result.getString("courseCode"), this.result.getString("courseName"));
                courseList.add(cData);
            }  
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
     return courseList;  
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
}
