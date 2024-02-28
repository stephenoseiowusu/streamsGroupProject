package data;

import challenges.StudentOps;
import domain.Student;
import org.assertj.core.api.AssertDelegateTarget;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentOpsTest {


        private static List<Student> students;

        @BeforeAll
        static void setUp() throws IOException {
            // Fetch data before all tests
            students = FetchData.getStudentList();
            students.stream().forEach((student -> {
                student.setAge( new Random().nextInt(13,26));
            }));
        }



        @Test
        void shouldReturnEmptyListWhenNoStudentsMatchGender() {
            List<Student> filteredStudents = StudentOps.filterStudentsByGender(students, "Other");
            assertThat(filteredStudents).isEmpty();
        }

        @Test
        void shouldReturnListStudentsMatchingGender() {
            List<Student> filteredStudents = StudentOps.filterStudentsByGender(students, "Female");
            assertEquals("Female",filteredStudents.get(5).getGender());
        }

        @Test
        void shouldGetAverageAgeOfStudents(){


            int average_age_of_students  = StudentOps.getAverageAgeOfStudents(students);

            assertEquals(18,average_age_of_students);
        }

        @Test
        void shouldPrintAllStudentNames(){
            StudentOps.printAllStudentNames(students);
        }

        @Test
        void shouldGroupStudentsByGender(){
            StudentOps.groupStudentsByGender(students);
        }

        @Test
        void maxAgeShouldBe25(){
            int max_age = StudentOps.getMaxAge(students);
            Assertions.assertEquals(max_age,25);
;        }

        @Test
        void shouldTransformListIntoMap(){
            Object result  = StudentOps.transformListToMap(students);
            Assertions.assertTrue( result instanceof Map<?,?>);
        }

        @Test
        void getAllStudentEmails(){
            List<String> result = StudentOps.getStudentsEmails(students);
            assertThat(result).allMatch((s) ->{
                return s.matches("^(.+)@(.+)$");
            });
        }

        @Test
        void checkIfStudentIsAdult(){
            assertThat(students.get(new Random().nextInt(0,students.size()-1))).matches((s) ->{
                return StudentOps.checkIfStudentISAdult(s);
            });
        }

        @Test
        void checkIfFemaleYoungestStudnetMatchesYoungestFemaleStudent(){
            Optional<Student> student = students.stream().filter((s) ->{
                return s.getGender().equalsIgnoreCase("F") || s.getGender().equalsIgnoreCase("female");
            }).sorted((s1, s2) ->{
                return s1.getAge() - s2.getAge();
            }).findFirst();
            Optional<Student> student2 = StudentOps.getYoungestFemaieStudent(students);
            assertThat(student.get()).isEqualTo(student2.get());
        }

        @Test
        void checkIfAllStudentFirstNameMatchesString(){
            String firstNames =  students.stream().map((s) ->{
                return s.getFirst_name()  + " " + s.getLast_name();
            }).collect(Collectors.joining(", "));
            Assertions.assertEquals(firstNames, StudentOps.getAllStudentNames(students));
        }

        @Test
        void checkSumOfallStudentAges(){
            int sumOfStudentAges = students.stream().mapToInt(s -> {
                return s.getAge();
            }).sum();
            Assertions.assertEquals(sumOfStudentAges,StudentOps.getSumOfallStudentAges(students));
        }
        @Test
        void checkIfAllStudentsAreAdults(){
            AtomicBoolean areAllStudentsAdults = new AtomicBoolean(true);
            students.stream().forEach((s) ->{
              if(s.getAge() < 18){
                  areAllStudentsAdults.set(false);
              }
            });
            Assertions.assertEquals(areAllStudentsAdults.get(),StudentOps.checkIfAllStudentsAreAdults(students));
        }

        @Test
        void checkGetOldestStudent(){
            Student oldestStudent = students.stream().max((s1,s2) ->{
                return s1.getAge() - s2.getAge();
            }).get();
            Student oldestStudent2 = StudentOps.getOldestStudent(students);
            Assertions.assertEquals(oldestStudent,oldestStudent2);
        }

        @Test
        void allStudentFirstNamesAreUpperCase(){
            String upperCasedFirstNames = "";
            List<Student> upperCasedStudentFirstNames = StudentOps.convertAllStudentFirstNamesToUpperCase(students);
            boolean result  = true;
            for(int i = 0; i < students.size(); i++){
                 if(!upperCasedStudentFirstNames.get(i).getFirst_name().equals(students.get(i).getFirst_name().toUpperCase())){
                     result = false;
                 }
            }
            Assertions.assertEquals(result,true);
        }

        @Test
        void getStudentById(){
            Student student = students.get(0);
            Student result = StudentOps.getStudentById(students, students.get(0).getId());
            Assertions.assertEquals(student,result);
        }

        @Test
        void groupStudentsByAge(){

            Map<Integer,List<Student>> studentsMap = new HashMap<>();
            for(Student student:students){
                if(!studentsMap.containsKey(student.getAge())){
                    studentsMap.put(student.getAge(),new ArrayList<>());
                    studentsMap.get(student.getAge()).add(student);
                }else{
                    studentsMap.get(student.getAge()).add(student);
                }
            }
            Map<Integer,List<Student>> result = new HashMap<>();
            result = StudentOps.getStudentsByAge(students);
            Assertions.assertEquals(studentsMap,result);
        }



    }

