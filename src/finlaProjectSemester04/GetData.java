/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finlaProjectSemester04;

/**
 *
 * @author isuru
 */
class GetData {
    private int mobileNumber;
    private String indexNo,stuId,fullName,gender,streem,faculty,courseName,courseCode;
    private String courseCodeTable,courseNameTable;
    
    GetData(String cCode, String cName){  
       this.courseCodeTable = cCode;
       this.courseNameTable = cName;
    }
    
    GetData(String setIndex,String setStuId,String setFullName,int setMobile,String setGender,String setStreem,String setFaculty,String setCourseName,String setCourseCode){
    
        
        this.indexNo = setIndex;
        this.stuId = setStuId;
        this.fullName = setFullName;
        this.mobileNumber = setMobile;
        this.gender = setGender;
        this.streem = setStreem;
        this.faculty = setFaculty;
        this.courseName = setCourseName;
        this.courseCode = setCourseCode;  
    }
    
 public String getcCode(){
        return this.courseCodeTable;
    }
 public String getcName(){
        return this.courseNameTable;
    }
 
    public String getIndexNo(){
        return this.indexNo;
    }
    
    public String getStuId(){
        return this.stuId;
    }
    public String getFullName(){
        return this.fullName;
    }
    public int getMobile(){
        return this.mobileNumber;
    }
    public String getGender(){
        return this.gender;
    }
    public String getStreem(){
        return this.streem;
    }
    public String getFaculty(){
        return this.faculty;
    }
    public String getCourseName(){
        return this.courseName;
    }
    public String getCourseCode(){
        return this.courseCode;
    }
    
    
}
